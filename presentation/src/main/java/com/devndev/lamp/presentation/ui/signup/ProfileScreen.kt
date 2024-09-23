package com.devndev.lamp.presentation.ui.signup

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.canhub.cropper.CropImage.CancelledResult.uriContent
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageContractOptions
import com.canhub.cropper.CropImageOptions
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.common.SelectionScreen
import com.devndev.lamp.presentation.ui.theme.Gray
import com.devndev.lamp.presentation.ui.theme.LampBlack
import com.devndev.lamp.presentation.ui.theme.LightGray
import com.devndev.lamp.presentation.ui.theme.ManColor
import com.devndev.lamp.presentation.ui.theme.Typography
import com.devndev.lamp.presentation.ui.theme.WomanColor

@Composable
fun ProfileScreen(
    profileIntro: String,
    bitmaps: List<Bitmap?>,
    onProfileIntroChange: (String) -> Unit,
    onBitmapsChange: (List<Bitmap?>) -> Unit
) {
    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    var imageIndex by remember { mutableStateOf(-1) }

    var profileQuery by remember { mutableStateOf(profileIntro) }
    val maxProfileChar = 100

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
                onBitmapsChange(updatedBitmaps)
            }
        }
    }

    SelectionScreen(text = stringResource(id = R.string.input_profile)) {
        Spacer(modifier = Modifier.height(15.dp))
        Text(
            text = stringResource(id = R.string.guide_profile),
            color = Color.White,
            style = Typography.normal12
        )
        Spacer(modifier = Modifier.height(30.dp))
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
                        val currentBitmap = if (index < bitmaps.size) bitmaps[index] else null

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
                                val updatedBitmaps = bitmaps.toMutableList().apply {
                                    this[index] = null
                                }
                                onBitmapsChange(updatedBitmaps)
                            },
//                            onEdit = {
//                                imageIndex = index
//                                imageCropLauncher.launch(
//                                    CropImageContractOptions(
//                                        imageUri,
//                                        CropImageOptions()
//                                    )
//                                )
//                            },
                            isFirstImage = (index == 0)
                        )
                    }
                }
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
        Spacer(modifier = Modifier.height(30.dp))
        Box(
            modifier = Modifier
                .width(295.dp)
                .height(120.dp)
                .background(LampBlack, shape = RoundedCornerShape(20.dp))
                .border(
                    width = 1.dp,
                    color = LightGray,
                    shape = RoundedCornerShape(27.dp)
                )
                .padding(horizontal = 20.dp, vertical = 14.dp)
        ) {
            BasicTextField(
                value = profileQuery,
                onValueChange = { newText ->
                    if (newText.length <= maxProfileChar) {
                        profileQuery = newText
                        onProfileIntroChange(newText)
                    }
                },
                textStyle = Typography.normal14.copy(color = Color.White),
                cursorBrush = SolidColor(Color.White),
                modifier = Modifier

            ) { innerTextField ->
                if (profileQuery.isEmpty()) {
                    Text(
                        text = "자신에 대해 소개해주세요",
                        color = LightGray,
                        style = Typography.normal14
                    )
                }
                innerTextField()
            }
        }
    }
}

@Composable
fun ProfileImage(
    bitmap: Bitmap?,
    onClick: () -> Unit,
    onDelete: () -> Unit,
//    onEdit: () -> Unit,
    isFirstImage: Boolean = false
) {
    val gradientBrush = Brush.horizontalGradient(
        colors = listOf(WomanColor, ManColor)
    )

    val modifier = Modifier
        .size(95.dp)
        .clip(RoundedCornerShape(4.dp))
        .background(Gray)
        .then(
            if (isFirstImage) {
                Modifier.border(
                    width = 1.dp,
                    brush = gradientBrush,
                    shape = RoundedCornerShape(4.dp)
                )
            } else {
                Modifier.border(width = 1.dp, color = Gray)
            }
        )
        .clickable(onClick = onClick)

    Box(modifier = modifier) {
        Icon(
            modifier = Modifier.align(Alignment.Center),
            painter = painterResource(id = R.drawable.image_icon),
            contentDescription = null,
            tint = Color.Unspecified
        )
        if (bitmap != null) {
            Image(
                bitmap = bitmap.asImageBitmap(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = modifier
            )

            if (isFirstImage) {
                MainProfileImage()
            }

            Box(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .size(24.dp)
                    .background(Color.Gray.copy(alpha = 0.5f), RectangleShape)
                    .clickable(onClick = onDelete)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.x_button),
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }

//            Box(
//                modifier = Modifier
//                    .align(Alignment.BottomStart)
//                    .size(24.dp)
//                    .background(Color.Gray.copy(alpha = 0.5f), RectangleShape)
//                    .clickable(onClick = onEdit)
//            ) {
//                Icon(
//                    painter = painterResource(id = R.drawable.baseline_edit_24),
//                    contentDescription = null,
//                    tint = Color.White,
//                    modifier = Modifier.size(20.dp)
//                )
//            }
        } else {
            if (isFirstImage) {
                MainProfileImage()
            }
        }
    }
}

@Composable
fun MainProfileImage() {
    Box(
        modifier = Modifier
            .size(width = 41.dp, height = 26.dp)
            .clip(RoundedCornerShape(topStart = 4.dp, bottomEnd = 4.dp))
            .background(
                color = WomanColor
            )
    ) {
        Text(
            text = stringResource(id = R.string.representative),
            color = Color.White,
            modifier = Modifier.align(Alignment.Center),
            style = Typography.normal12
        )
    }
}
