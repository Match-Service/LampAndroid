package com.devndev.lamp.presentation.ui.mypage

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.canhub.cropper.CropImage.CancelledResult.uriContent
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.main.navigation.navigateMain
import com.devndev.lamp.presentation.ui.common.CustomRadioButton
import com.devndev.lamp.presentation.ui.common.LampBigTextField
import com.devndev.lamp.presentation.ui.common.MainScreenPage
import com.devndev.lamp.presentation.ui.common.ProfileImage
import com.devndev.lamp.presentation.ui.theme.LampBlack
import com.devndev.lamp.presentation.ui.theme.LightGray
import com.devndev.lamp.presentation.ui.theme.Typography

@Composable
fun ProfileEditScreen(
    modifier: Modifier,
    profileEditViewModel: ProfileEditViewModel = hiltViewModel(),
    navController: NavController
) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageIndex by remember { mutableIntStateOf(-1) }
    val bitmaps: List<Bitmap?> = mutableListOf()
    var deleteIndex by remember { mutableIntStateOf(-1) }

    var profileQuery by remember { mutableStateOf("원래 있던 소개") }

    var selectedDrink by remember { mutableStateOf(context.getString(R.string.drink_often)) }
    var isDrinkExpanded by remember { mutableStateOf(false) }

    var selectedSmoke by remember { mutableStateOf(context.getString(R.string.smoke_often)) }
    var isSmokeExpanded by remember { mutableStateOf(false) }

    var selectedExercise by remember { mutableStateOf(context.getString(R.string.exercise_sometimes)) }
    var isExerciseExpanded by remember { mutableStateOf(false) }

    val imageCropLauncher = rememberLauncherForActivityResult(CropImageContract()) { result ->
        if (result.isSuccessful) {
            val uri = result.uriContent
            imageUri = uri
            val bitmap = if (Build.VERSION.SDK_INT < 28) {
                MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, uri!!)
                ImageDecoder.decodeBitmap(source)
            }

            if (imageIndex in bitmaps.indices) {
                val updatedBitmaps = bitmaps.toMutableList().apply {
                    if (imageIndex == 0) {
                        this[0] = bitmap
                    } else {
                        val emptyIndex = this.subList(1, this.size).indexOfFirst { it == null }
                        if (emptyIndex != -1 && imageIndex != emptyIndex + 1) {
                            this[imageIndex] = null
                            this[emptyIndex + 1] = bitmap
                        } else {
                            this[imageIndex] = bitmap
                        }
                    }
                }
            }
        }
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(LampBlack)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.spacedBy(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painterResource(id = R.drawable.back_arrow),
                    contentDescription = "뒤로가기",
                    tint = Color.White,
                    modifier = Modifier.clickable {
                        navController.navigateMain(MainScreenPage.MY_PAGE)
                    }
                )
                Text(
                    text = stringResource(id = R.string.edit_profile),
                    style = Typography.semiBold25,
                    fontSize = 25.sp,
                    color = Color.White,
                    textAlign = TextAlign.Center
                )
            }
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
                                val currentBitmap =
                                    if (index < bitmaps.size) bitmaps[index] else null

                                ProfileImage(
                                    bitmap = currentBitmap,
                                    onClick = {
                                        imageIndex = index
                                        imageCropLauncher.launch(
                                            CropImageContractOptions(
                                                uriContent,
                                                CropImageOptions()
                                            )
                                        )
                                    },
                                    onDelete = {
                                        deleteIndex = index
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
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                ) {
                    Text(text = "한국대학교", color = Color.White, style = Typography.medium18)
                }
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .border(
                            width = 1.dp,
                            color = LightGray,
                            shape = RoundedCornerShape(15.dp)
                        )
                        .padding(horizontal = 20.dp, vertical = 10.dp)
                ) {
                    Text(text = "instagram_ID", color = Color.White, style = Typography.medium18)
                }
            }
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
            enter = slideInVertically(animationSpec = tween(300)) +
                expandVertically(expandFrom = Alignment.Top) +
                fadeIn(initialAlpha = 0.3f),
            exit = slideOutVertically(animationSpec = tween(500)) +
                shrinkVertically() +
                fadeOut()
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

object InfoType {
    const val DRINK = 0
    const val SMOKE = 1
    const val EXERCISE = 2
}
