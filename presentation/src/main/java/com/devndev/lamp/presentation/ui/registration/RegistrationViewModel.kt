package com.devndev.lamp.presentation.ui.registration

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.devndev.lamp.domain.model.login.GoogleTokenParam
import com.devndev.lamp.domain.model.signup.SignUpAuthRequest
import com.devndev.lamp.domain.model.signup.SignUpParam
import com.devndev.lamp.domain.model.signup.User
import com.devndev.lamp.domain.model.signup.ValidateInstagramParam
import com.devndev.lamp.domain.model.signup.ValidateNameParam
import com.devndev.lamp.domain.usecase.login.GoogleAuthUseCase
import com.devndev.lamp.domain.usecase.login.SaveIsNeedSignOutUseCase
import com.devndev.lamp.domain.usecase.login.SetTokenUseCase
import com.devndev.lamp.domain.usecase.signup.ImageUploadUseCase
import com.devndev.lamp.domain.usecase.signup.SignUpUseCase
import com.devndev.lamp.domain.usecase.signup.ValidateInstagramUseCase
import com.devndev.lamp.domain.usecase.signup.ValidateNameUseCase
import com.devndev.lamp.presentation.ui.common.InstagramStep
import com.devndev.lamp.presentation.ui.login.AuthManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val googleAuthUseCase: GoogleAuthUseCase,
    private val validateNameUseCase: ValidateNameUseCase,
    private val validateInstagramUseCase: ValidateInstagramUseCase,
    private val saveIsNeedSignOutUseCase: SaveIsNeedSignOutUseCase,
    private val imageUploadUseCase: ImageUploadUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val setTokenUseCase: SetTokenUseCase
) : ViewModel() {
    private val logTag = "RegistrationViewModel"

    private val _uiState = MutableStateFlow(RegistrationUiState())
    val uiState: StateFlow<RegistrationUiState> = _uiState.asStateFlow()

    private val _currentStep = MutableStateFlow(1)
    val currentStep: StateFlow<Int> = _currentStep

    private val _isDuplicateName = MutableStateFlow(false)
    val isDuplicateName: StateFlow<Boolean> = _isDuplicateName

    private val _isNameValidCompleted = MutableStateFlow(false)
    val isNameValidCompleted: StateFlow<Boolean> = _isNameValidCompleted

    private val _instagramStep = MutableStateFlow(InstagramStep.NONE)
    val instagramStep: StateFlow<Int> = _instagramStep

    private lateinit var fcmToken: String

    init {
        getFcmToken()
    }

    fun updateCurrentStep(step: Int) {
        _currentStep.value = step
        Log.d(logTag, "updateCurrentStep: ${currentStep.value}")
    }

    fun updateIsDuplicateName(isDuplicate: Boolean) {
        _isDuplicateName.value = isDuplicate
        Log.d(logTag, "updateIsDuplicateName: ${isDuplicateName.value}")
    }

    fun updateInstagramStep(step: Int) {
        _instagramStep.value = step
        Log.d(logTag, "updateInstagramStep: ${instagramStep.value}")
    }

    fun updateIsNameValidCompleted(isNameValidComplete: Boolean) {
        _isNameValidCompleted.value = isNameValidComplete
        Log.d(logTag, "updateIsNameValidCompleted: ${isNameValidCompleted.value}")
    }

    fun checkIsDuplicateName(name: String) {
        viewModelScope.launch {
            try {
                if (validateNameUseCase(ValidateNameParam(name))) {
                    Log.d(logTag, "checkIsDuplicateName: false")
                    _isDuplicateName.value = false
                    _currentStep.value = currentStep.value + 1
                    _isNameValidCompleted.value = true
                } else {
                    Log.d(logTag, "checkIsDuplicateName: true")
                    _isDuplicateName.value = true
                }
            } catch (e: ApiException) {
                Log.e(logTag, "checkIsDuplicateName", e)
            }
        }
    }

    fun checkIsValidInstagramId(instagramId: String) {
        viewModelScope.launch {
            try {
                if (validateInstagramUseCase(ValidateInstagramParam(instagramId))) {
                    Log.d(logTag, "checkIsValidInstagramId: true")
                    _instagramStep.value = InstagramStep.VALID
                } else {
                    Log.d(logTag, "checkIsValidInstagramId: false")
                    _instagramStep.value = InstagramStep.INVALID
                }
            } catch (e: ApiException) {
                Log.e(logTag, "checkIsValidInstagramId", e)
            }
        }
    }

    fun saveIsNeedSignOut(isNeedSignOut: Boolean) {
        saveIsNeedSignOutUseCase(isNeedSignOut)
    }

    private fun bitmapsToMultipartBodies(
        bitmaps: List<Bitmap?>,
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

    fun uploadImages(user: User, bitmaps: List<Bitmap?>) {
        viewModelScope.launch {
            try {
                val multipartBodies = bitmapsToMultipartBodies(bitmaps, "profileImages")
                val responses = imageUploadUseCase(multipartBodies)
                val imageUrls = responses.map { it.imageUrl }

                user.profileImages = imageUrls

                for (imageUrl in imageUrls) {
                    Log.d(logTag, "imageUrl: $imageUrl")
                }
                signUp(user = user)
            } catch (e: Exception) {
                Log.e(logTag, "Error uploading image", e)
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

    private fun signUp(user: User) {
        user.pushToken = fcmToken
        val signUpParam = SignUpParam(SignUpAuthRequest(AuthManager.signUpToken), user)

        Log.d(logTag, "SignUpParam $signUpParam")
        viewModelScope.launch {
            try {
                val response = signUpUseCase(signUpParam)
                if (response.isSuccessful) {
                    Log.d(logTag, "signUp Success")

                    val account = GoogleSignIn.getLastSignedInAccount(context)
                    val idToken = account?.idToken
                    if (idToken != null) {
                        val googleTokenParam = GoogleTokenParam(idToken = idToken)
                        val tokenResult = googleAuthUseCase(googleTokenParam)
                        if (tokenResult.token != null && tokenResult.signupToken == null) {
                            setTokenUseCase(tokenResult.token.toString())
                            _uiState.update { state ->
                                state.copy(isSignedUp = true)
                            }
                        }
                    }
                } else {
                    Log.e(logTag, "signUp Failed: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e(logTag, "Error signUp", e)
            }
        }
    }
}
