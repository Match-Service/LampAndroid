package com.devndev.lamp.presentation.ui.mypage

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devndev.lamp.domain.model.signup.ValidateInstagramParam
import com.devndev.lamp.domain.model.user.EditImageModel
import com.devndev.lamp.domain.model.user.EditImageParam
import com.devndev.lamp.domain.model.user.ModifyUserParam
import com.devndev.lamp.domain.model.user.MyInfoDomainModel
import com.devndev.lamp.domain.usecase.signup.ImageUploadUseCase
import com.devndev.lamp.domain.usecase.signup.ValidateInstagramUseCase
import com.devndev.lamp.domain.usecase.user.EditImageUseCase
import com.devndev.lamp.domain.usecase.user.GetMyInfoUseCase
import com.devndev.lamp.domain.usecase.user.ModifyUserUseCase
import com.devndev.lamp.presentation.ui.common.InstagramAuth
import com.devndev.lamp.presentation.ui.home.main.HomeViewModel
import com.google.android.gms.common.api.ApiException
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@HiltViewModel
class ProfileEditViewModel @Inject constructor(
    private val getMyInfoUseCase: GetMyInfoUseCase,
    private val validateInstagramUseCase: ValidateInstagramUseCase,
    private val modifyUserUseCase: ModifyUserUseCase,
    private val imageUploadUseCase: ImageUploadUseCase,
    private val editImageUseCase: EditImageUseCase
) : ViewModel() {
    private val logTag = "ProfileEditViewModel"

    private val _myInfo = MutableStateFlow<MyInfoDomainModel?>(null)
    val myInfo: StateFlow<MyInfoDomainModel?> = _myInfo

    private val _instagramStep = MutableStateFlow(InstagramAuth.BEFORE_AUTH)
    val instagramStep: StateFlow<Int> = _instagramStep

    // 테스트용 fcm 코드 추후 삭제
    private lateinit var fcmToken: String

    fun updateInstagramStep(step: Int) {
        _instagramStep.value = step
        Log.d(logTag, "updateInstagramStep: ${instagramStep.value}")
    }

    init {
        getMyInfo()
        getFcmToken()
    }

    private fun getMyInfo() {
        viewModelScope.launch {
            getMyInfoUseCase()
                .onSuccess { userInfo ->
                    Log.d(HomeViewModel.TAG, "getMyInfo")
                    _myInfo.value = userInfo
                    Log.d(HomeViewModel.TAG, "My Info ${myInfo.value}")
                }
                .onFailure { throwable ->
                    Log.e(HomeViewModel.TAG, "Failed to fetch user info", throwable)
                }
        }
    }

    private fun getFcmToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@addOnCompleteListener
            }
            viewModelScope.launch {
                val token = task.result
                fcmToken = token
                Log.d(logTag, "getFcmToken() $fcmToken")
            }
        }
    }

    private fun bitmapsToMultipartBodies(
        bitmaps: List<Bitmap>,
        fieldName: String
    ): List<MultipartBody.Part> {
        return bitmaps.map { bitmap ->
            val stream = ByteArrayOutputStream()
            bitmap?.let { Bitmap.createScaledBitmap(it, 1080, 1080, true) }
                ?.compress(Bitmap.CompressFormat.JPEG, 75, stream) // 품질 조정
            val byteArray = stream.toByteArray()

            val requestBody = RequestBody.create("image/jpeg".toMediaTypeOrNull(), byteArray)
            MultipartBody.Part.createFormData(fieldName, "image.jpg", requestBody)
        }
    }

    fun editImages(modifyUserParam: ModifyUserParam, editImageModel: List<EditImageModel?>) {
        viewModelScope.launch {
            try {
                val profileImageList = mutableListOf<String?>()
                for (model in editImageModel) {
                    profileImageList.add(model?.imageUrl)
                }
                Log.d("profileImageList", profileImageList.toString())

                val bitmapList = mutableListOf<Bitmap>()
                editImageModel.forEachIndexed { index, item ->
                    if (item?.bitmap is Bitmap) {
                        bitmapList.add(item.bitmap!!)
                    }
                }
                Log.d("bitmapList", bitmapList.toString())

                if (bitmapList.isNotEmpty()) {
                    val multipartBodies = bitmapsToMultipartBodies(bitmapList, "profileImages")
                    val responses = imageUploadUseCase(multipartBodies)
                    val imageUrls = responses.map { it.imageUrl }

                    for (imageUrl in imageUrls) {
                        Log.d(logTag, "imageUrl: $imageUrl")
                    }

                    var imageUrlIndex = 0

                    for (i in profileImageList.indices) {
                        if (profileImageList[i] == null && imageUrlIndex < imageUrls.size) {
                            profileImageList[i] = imageUrls[imageUrlIndex]
                            imageUrlIndex++
                        }
                    }
                }

                val filteredList = profileImageList.filterNotNull()
                Log.d("profileImageList2", filteredList.toString())

                editImageUseCase(
                    EditImageParam(
                        profileImages = filteredList
                    )
                )

                modifyUser(modifyUser = modifyUserParam)
            } catch (e: Exception) {
                Log.e(logTag, "Error uploading image", e)
            }
        }
    }

    fun modifyUser(modifyUser: ModifyUserParam) {
        viewModelScope.launch {
            Log.d(logTag, "modifyUserRequest $modifyUser pushToken $fcmToken")
            try {
                val response = modifyUserUseCase(
                    ModifyUserParam(
                        name = modifyUser.name,
                        job = modifyUser.job,
                        jobName = modifyUser.jobName,
                        gender = modifyUser.gender,
                        birth = modifyUser.birth,
                        instagramId = modifyUser.instagramId,
                        bio = modifyUser.bio,
                        profileImages = modifyUser.profileImages,
                        bioQuestions = modifyUser.bioQuestions,
                        pushToken = fcmToken
                    )
                )

                if (response) {
                    Log.d(logTag, "modifyUser: true")
                } else {
                    Log.d(logTag, "modifyUser: fail")
                }
            } catch (e: ApiException) {
                Log.e(logTag, "modifyUser", e)
            }
        }
    }

    fun checkIsValidInstagramId(instagramId: String) {
        viewModelScope.launch {
            try {
                if (validateInstagramUseCase(ValidateInstagramParam(instagramId))) {
                    Log.d(logTag, "checkIsValidInstagramId: true")
                    _instagramStep.value = InstagramAuth.AUTH_SUCCESS
                } else {
                    Log.d(logTag, "checkIsValidInstagramId: false")
                    _instagramStep.value = InstagramAuth.AUTH_FAIL
                }
            } catch (e: ApiException) {
                Log.e(logTag, "checkIsValidInstagramId", e)
            }
        }
    }
}
