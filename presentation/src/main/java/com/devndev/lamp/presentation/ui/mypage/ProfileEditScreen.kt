package com.devndev.lamp.presentation.ui.mypage

import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.canhub.cropper.CropImage.CancelledResult.uriContent
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.canhub.cropper.CropImageView
import com.devndev.lamp.domain.model.signup.BioQuestion
import com.devndev.lamp.domain.model.user.EditImageModel
import com.devndev.lamp.domain.model.user.ModifyUserParam
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.theme.LampBlack
import com.devndev.lamp.presentation.theme.LightGray
import com.devndev.lamp.presentation.theme.Typography
import com.devndev.lamp.presentation.ui.common.CustomRadioButton
import com.devndev.lamp.presentation.ui.common.EditPopup
import com.devndev.lamp.presentation.ui.common.InstagramAuth
import com.devndev.lamp.presentation.ui.common.LampBigTextField
import com.devndev.lamp.presentation.ui.common.OneButtonPopup
import com.devndev.lamp.presentation.ui.common.ProfileImage
import com.devndev.lamp.presentation.ui.common.TopNavigationBar
import com.devndev.lamp.presentation.ui.common.TwoButtonPopup
import com.devndev.lamp.presentation.ui.mypage.navigation.navigateMyPage

@Composable
fun ProfileEditScreen(
    modifier: Modifier,
    profileEditViewModel: ProfileEditViewModel = hiltViewModel(),
    navController: NavController
) {
    val logTag = "ProfileEditScreen"

    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageIndex by remember { mutableIntStateOf(-1) }
    var editImageModel by remember { mutableStateOf<List<EditImageModel?>>(List(6) { null }) }
    var originImageModel by remember { mutableStateOf<MutableList<EditImageModel?>>(mutableListOf()) }
    var deleteIndex by remember { mutableIntStateOf(-1) }
    var urls by remember { mutableStateOf(emptyList<String>()) }
    val myInfo by profileEditViewModel.myInfo.collectAsState()

    var profileQuery by remember { mutableStateOf("원래 있던 소개") }
    var isShowDeletePopup by remember { mutableStateOf(false) }

    Log.d(logTag, myInfo?.bioQuestions?.get(0)?.answer.toString())

    var selectedDrink by remember { mutableStateOf("") }
    var isDrinkExpanded by remember { mutableStateOf(false) }

    var selectedSmoke by remember { mutableStateOf("") }
    var isSmokeExpanded by remember { mutableStateOf(false) }

    var selectedExercise by remember { mutableStateOf("") }
    var isExerciseExpanded by remember { mutableStateOf(false) }

    var instagram by remember { mutableStateOf("") }
    val instagramAuthStep by profileEditViewModel.instagramStep.collectAsState()

    var university by remember { mutableStateOf("") }

    var isShowEditUniversityPopup by remember { mutableStateOf(false) }
    var isShowEditInstagramPopup by remember { mutableStateOf(false) }
    var isProfilePopupShow by remember { mutableStateOf(false) }
    var isBioPopupShow by remember { mutableStateOf(false) }

    LaunchedEffect(myInfo) {
        selectedDrink = myInfo?.bioQuestions?.get(0)?.answer ?: ""
        selectedSmoke = myInfo?.bioQuestions?.get(1)?.answer ?: ""
        selectedExercise = myInfo?.bioQuestions?.get(2)?.answer ?: ""
        instagram = myInfo?.instagramId ?: ""
        profileQuery = myInfo?.bio ?: ""
        university = myInfo?.jobName ?: ""
        if (myInfo?.profileImages != null) {
            val newUrls = myInfo!!.profileImages.map { profileImage -> profileImage.downloadUrl }
            urls = newUrls
        }

        val newList = editImageModel.toMutableList()
        myInfo?.profileImages?.forEachIndexed { index, profileImage ->
            if (index < newList.size) {
                newList[index] = EditImageModel(
                    bitmap = null,
                    imageUrl = profileImage.originUrl
                )
            }
        }
        editImageModel = newList.toList()

        originImageModel = myInfo?.profileImages?.mapIndexed { index, profileImage ->
            if (index < editImageModel.size) {
                EditImageModel(
                    bitmap = null,
                    imageUrl = profileImage.originUrl
                )
            } else {
                null
            }
        }?.toMutableList() ?: mutableListOf()
    }

    val isRepresentativeImageEmpty = editImageModel.getOrNull(0)?.let {
        it.bitmap == null && it.imageUrl.isNullOrBlank()
    } ?: true

    // 뒤로가기 버튼
    BackHandler {
        if (isRepresentativeImageEmpty) {
            isProfilePopupShow = true
        } else if (profileQuery.isBlank()) {
            isBioPopupShow = true
        } else {
            if (!compProfileList(originImageModel, editImageModel)) {
                profileEditViewModel.editImages(
                    ModifyUserParam(
                        name = myInfo?.name ?: "", // 변경불가
                        job = myInfo?.job ?: "",
                        jobName = university,
                        gender = myInfo?.gender ?: "", // 변경불가
                        birth = myInfo?.birth ?: "", // 변경불가
                        instagramId = instagram,
                        bio = profileQuery,
                        profileImages = listOf(myInfo?.profileImages?.get(0)?.downloadUrl ?: ""),
                        bioQuestions = listOf(
                            BioQuestion(
                                myInfo?.bioQuestions?.get(0)?.question ?: "음주",
                                selectedDrink ?: myInfo?.bioQuestions?.get(0)!!.answer
                            ),
                            BioQuestion(
                                myInfo?.bioQuestions?.get(1)?.question ?: "흡연",
                                selectedSmoke ?: myInfo?.bioQuestions?.get(1)!!.answer
                            ),
                            BioQuestion(
                                myInfo?.bioQuestions?.get(2)?.question ?: "운동",
                                selectedExercise ?: myInfo?.bioQuestions?.get(2)!!.answer
                            )
                        ),
                        pushToken = ""
                    ),
                    editImageModel = editImageModel
                ) {
                    navController.navigateMyPage()
                }
            }
        }
    }

    if (isProfilePopupShow) {
        OneButtonPopup(
            text = stringResource(id = R.string.register_profile_image),
            onDismissRequest = { isProfilePopupShow = false }
        )
    }

    if (isBioPopupShow) {
        OneButtonPopup(
            text = stringResource(id = R.string.type_bio),
            onDismissRequest = { isBioPopupShow = false }
        )
    }

    if (isShowEditUniversityPopup) {
        EditPopup(
            text = stringResource(id = R.string.edit_university),
            queryString = university,
            onXButtonClick = {
                isShowEditUniversityPopup = false
            },
            onEditButtonClick = {
                university = it
                isShowEditUniversityPopup = false
            }
        )
    }

    if (isShowEditInstagramPopup) {
        EditPopup(
            text = stringResource(id = R.string.edit_instagram),
            queryString = instagram,
            onXButtonClick = {
                isShowEditInstagramPopup = false
                profileEditViewModel.updateInstagramStep(InstagramAuth.BEFORE_AUTH)
            },
            onEditButtonClick = {
                if (instagramAuthStep == InstagramAuth.BEFORE_AUTH || instagramAuthStep == InstagramAuth.AUTH_FAIL) {
                    profileEditViewModel.checkIsValidInstagramId(instagramId = instagram)
                } else if (instagramAuthStep == InstagramAuth.AUTH_SUCCESS) {
                    instagram = it
                    isShowEditInstagramPopup = false
                    profileEditViewModel.updateInstagramStep(InstagramAuth.BEFORE_AUTH)
                }
            },
            instagramAuthStep = instagramAuthStep,
            onInstagramQueryChange = { profileEditViewModel.updateInstagramStep(it) },
            isNeedClearFocus = instagramAuthStep == InstagramAuth.AUTH_SUCCESS
        )
    }

    val imageCropLauncher = rememberLauncherForActivityResult(CropImageContract()) { result ->
        Log.d("imageCropResult", result.isSuccessful.toString())
        if (result.isSuccessful) {
            val uri = result.uriContent
            imageUri = uri
            val bitmap = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, uri!!)
                ImageDecoder.decodeBitmap(source)
            }

            editImageModel = editImageModel.toMutableList().apply {
                if (imageIndex == 0) {
                    this[0] = EditImageModel(
                        bitmap = bitmap,
                        imageUrl = null
                    )
                    Log.d("BitmapInfo1", "$this")
                } else {
                    val emptyIndex =
                        this.indexOfFirst { it?.bitmap == null && it?.imageUrl == null }
                    if (emptyIndex != -1) {
                        this[emptyIndex] = EditImageModel(
                            bitmap = bitmap,
                            imageUrl = null
                        )
                        Log.d("BitmapInfo2", "$this")
                    } else {
                        this[imageIndex] = EditImageModel(
                            bitmap = bitmap,
                            imageUrl = null
                        )
                        Log.d("BitmapInfo3", "$this")
                    }
                }
            }
            editImageModel = editImageModel.toMutableList()
        }
    }

    // 프로필 이미지 삭제 팝업
    if (isShowDeletePopup) {
        TwoButtonPopup(
            mainText = stringResource(id = R.string.delete_profile_image),
            startButtonText = stringResource(id = R.string.no),
            stringResource(id = R.string.yes),
            onStartButtonClick = {
                isShowDeletePopup = false
            },
            onEndButtonClick = {
                if (deleteIndex == -1) {
                    isShowDeletePopup = false
                    return@TwoButtonPopup
                }

                val updatedImage = editImageModel.toMutableList().apply {
                    if (deleteIndex == 0) {
                        // 대표 이미지는 삭제만 처리하고 당기지 않음
                        this[0] = null
                    } else {
                        // 1번째 인덱스 이상인 경우 당겨서 빈칸 없애기
                        this[deleteIndex] = null
                        for (i in deleteIndex + 1 until size) {
                            this[i - 1] = this[i]
                        }
                        this[size - 1] = null
                    }
                }

                urls = urls.toMutableList().apply {
                    if (deleteIndex == 0 && isNotEmpty()) {
                        this[0] = ""
                    } else if (deleteIndex in 1 until size) {
                        // 삭제 후 당기기 처리
                        this[deleteIndex] = ""
                        for (i in deleteIndex + 1 until size) {
                            this[i - 1] = this[i]
                        }
                        this[size - 1] = ""
                    }
                }
                editImageModel = updatedImage.toList()
                Log.d("test", editImageModel.toString())
                isShowDeletePopup = false
                deleteIndex = -1
            }
        )
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(LampBlack)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            TopNavigationBar(
                text = stringResource(id = R.string.edit_profile),
                isNeedXButton = false,
                onBackButtonClick = {
                    if (isRepresentativeImageEmpty) {
                        isProfilePopupShow = true
                    } else if (profileQuery.isBlank()) {
                        isBioPopupShow = true
                    } else {
                        if (!compProfileList(originImageModel, editImageModel)) {
                            profileEditViewModel.editImages(
                                ModifyUserParam(
                                    name = myInfo?.name ?: "", // 변경불가
                                    job = myInfo?.job ?: "",
                                    jobName = university,
                                    gender = myInfo?.gender ?: "", // 변경불가
                                    birth = myInfo?.birth ?: "", // 변경불가
                                    instagramId = instagram,
                                    bio = profileQuery,
                                    profileImages = listOf(
                                        myInfo?.profileImages?.get(0)?.downloadUrl ?: ""
                                    ),
                                    bioQuestions = listOf(
                                        BioQuestion(
                                            myInfo?.bioQuestions?.get(0)?.question ?: "음주",
                                            selectedDrink ?: myInfo?.bioQuestions?.get(0)!!.answer
                                        ),
                                        BioQuestion(
                                            myInfo?.bioQuestions?.get(1)?.question ?: "흡연",
                                            selectedSmoke ?: myInfo?.bioQuestions?.get(1)!!.answer
                                        ),
                                        BioQuestion(
                                            myInfo?.bioQuestions?.get(2)?.question ?: "운동",
                                            selectedExercise
                                                ?: myInfo?.bioQuestions?.get(2)!!.answer
                                        )
                                    ),
                                    pushToken = ""
                                ),
                                editImageModel = editImageModel
                            ) {
                                navController.navigateMyPage()
                            }
                        }
                    }
                }
            )

            Spacer(modifier = Modifier.height(32.dp))
            Column(
                modifier = Modifier.width(300.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    repeat(2) { row ->
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            repeat(3) { column ->
                                val index = row * 3 + column

                                val currentUrl = urls.getOrNull(index)?.takeIf { it.isNotEmpty() }
                                val currentBitmap = editImageModel.getOrNull(index)?.bitmap

                                ProfileImage(
                                    bitmap = currentBitmap,
                                    url = currentUrl,
                                    onClick = {
                                        imageIndex = index
                                        imageCropLauncher.launch(
                                            CropImageContractOptions(
                                                uriContent,
                                                CropImageOptions().apply {
                                                    aspectRatioX = 1
                                                    aspectRatioY = 1
                                                    fixAspectRatio = true
                                                    guidelines = CropImageView.Guidelines.ON
                                                }
                                            )
                                        )
                                    },
                                    onDelete = {
                                        deleteIndex = index
                                        isShowDeletePopup = true
                                    },
                                    isFirstImage = (index == 0)
                                )
                            }
                        }
                        Spacer(modifier = Modifier.height(5.dp))
                    }
                }
                LampBigTextField(
                    width = 300,
                    height = 120,
                    query = profileQuery,
                    onQueryChange = { profileQuery = it },
                    hintText = stringResource(id = R.string.introduce_profile_hint),
                    maxLength = 100
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .border(
                            width = 1.dp,
                            color = LightGray,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .padding(start = 20.dp, end = 20.dp, top = 10.dp)
                ) {
                    InfoSection(
                        type = InfoType.DRINK,
                        selectedOption = selectedDrink,
                        onSelectOption = { selectedDrink = it },
                        isExpanded = isDrinkExpanded,
                        onToggleExpand = { isDrinkExpanded = !isDrinkExpanded }
                    )
                    HorizontalDivider(thickness = 1.dp, color = LightGray)
                    Spacer(modifier = Modifier.height(10.dp))
                    InfoSection(
                        type = InfoType.SMOKE,
                        selectedOption = selectedSmoke,
                        onSelectOption = { selectedSmoke = it },
                        isExpanded = isSmokeExpanded,
                        onToggleExpand = { isSmokeExpanded = !isSmokeExpanded }
                    )
                    HorizontalDivider(thickness = 1.dp, color = LightGray)
                    Spacer(modifier = Modifier.height(10.dp))
                    InfoSection(
                        type = InfoType.EXERCISE,
                        selectedOption = selectedExercise,
                        onSelectOption = { selectedExercise = it },
                        isExpanded = isExerciseExpanded,
                        onToggleExpand = { isExerciseExpanded = !isExerciseExpanded }
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .border(
                            width = 1.dp,
                            color = LightGray,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .clickable {
                            isShowEditUniversityPopup = true
                        }
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = if (university.isEmpty()) {
                            stringResource(id = R.string.input_university)
                        } else {
                            university
                        },
                        color = if (university.isEmpty()) {
                            LightGray
                        } else {
                            Color.White
                        },
                        style = Typography.medium18
                    )
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .border(
                            width = 1.dp,
                            color = LightGray,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .clickable {
                            isShowEditInstagramPopup = true
                        }
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = if (instagram.isEmpty()) {
                            stringResource(id = R.string.guide_insta)
                        } else {
                            instagram
                        },
                        color = if (instagram.isEmpty()) {
                            LightGray
                        } else {
                            Color.White
                        },
                        style = Typography.medium18
                    )
                }
            }
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

@Composable
fun InfoSection(
    type: Int,
    selectedOption: String,
    onSelectOption: (String) -> Unit,
    isExpanded: Boolean,
    onToggleExpand: () -> Unit
) {
    var text = ""
    var optionText by remember { mutableStateOf(selectedOption) }
    Log.d("InfoSection", optionText)
    LaunchedEffect(selectedOption) {
        optionText = selectedOption
    }
    var options: List<String> = emptyList()
    when (type) {
        InfoType.DRINK -> {
            text = stringResource(id = R.string.drink)
            options = listOf(
                stringResource(id = R.string.drink_often),
                stringResource(id = R.string.drink_sometimes),
                stringResource(id = R.string.drink_never),
                stringResource(id = R.string.drink_stop)
            )
        }

        InfoType.SMOKE -> {
            text = stringResource(id = R.string.smoke)
            options = listOf(
                stringResource(id = R.string.smoke_often),
                stringResource(id = R.string.smoke_sometimes),
                stringResource(id = R.string.smoke_never),
                stringResource(id = R.string.smoke_stop)
            )
        }

        InfoType.EXERCISE -> {
            text = stringResource(id = R.string.exercise)
            options = listOf(
                stringResource(id = R.string.exercise_often),
                stringResource(id = R.string.exercise_sometimes),
                stringResource(id = R.string.exercise_never),
                stringResource(id = R.string.exercise_stop)
            )
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    onToggleExpand()
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = text, color = Color.White, style = Typography.medium18)
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = optionText, color = Color.White, style = Typography.normal14)
                Icon(
                    modifier = Modifier.size(10.dp),
                    painter = if (isExpanded) {
                        painterResource(id = R.drawable.reduce_icon)
                    } else {
                        painterResource(id = R.drawable.expand_icon)
                    },
                    contentDescription = null,
                    tint = Color.White
                )
            }
        }
        AnimatedVisibility(
            visible = isExpanded,
            enter = slideInVertically(animationSpec = tween(300)) + expandVertically(expandFrom = Alignment.Top) + fadeIn(
                initialAlpha = 0.3f
            ),
            exit = slideOutVertically(animationSpec = tween(500)) + shrinkVertically() + fadeOut()
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                options.forEach { option ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        RadioButtonWithLabel(
                            selected = selectedOption == option,
                            onClick = {
                                onSelectOption(option)
                                optionText = option
                                onToggleExpand()
                            },
                            label = option
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RadioButtonWithLabel(
    modifier: Modifier = Modifier,
    selected: Boolean,
    onClick: () -> Unit,
    label: String
) {
    Row(
        modifier = modifier
            .clickable(onClick = onClick),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, color = Color.White, style = Typography.normal14)
        CustomRadioButton(
            radioSize = 14,
            selected = selected,
            onClick = onClick
        )
    }
}

fun compProfileList(
    originImageModel: MutableList<EditImageModel?>,
    editImageModel: List<EditImageModel?>
): Boolean {
    // 두 리스트의 크기가 다르면 바로 false 반환
    if (originImageModel.size != editImageModel.size) return false

    // 각 요소를 비교하여 다르면 false 반환
    for (i in originImageModel.indices) {
        val origin = originImageModel[i]
        val edit = editImageModel[i]

        // origin과 edit의 bitmap과 imageUrl이 다르면 false 반환
        if (origin?.bitmap != edit?.bitmap || origin?.imageUrl != edit?.imageUrl) {
            return false
        }
    }

    // 모든 항목이 동일하면 true 반환
    return true
}

object InfoType {
    const val DRINK = 0
    const val SMOKE = 1
    const val EXERCISE = 2
}
