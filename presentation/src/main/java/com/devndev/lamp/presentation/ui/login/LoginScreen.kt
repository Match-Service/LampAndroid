package com.devndev.lamp.presentation.ui.login

import android.content.Intent
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.theme.Gray
import com.devndev.lamp.presentation.theme.LampBlack
import com.devndev.lamp.presentation.theme.Typography
import com.devndev.lamp.presentation.ui.common.AccountStatus
import com.devndev.lamp.presentation.ui.registration.navigation.navigateRegistration
import com.devndev.lamp.presentation.utils.Const

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel(),
    onClickSignInButton: (Intent) -> Unit
) {
    val accountStatus by AuthManager.accountStatus
    val logTag = "LoginScreen"
    val context = LocalContext.current
    val signInLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        result.data?.let { onClickSignInButton(it) }
        Log.d(logTag, "accountStatus == $accountStatus")
    }

    LaunchedEffect(accountStatus) {
        if (accountStatus == AccountStatus.NEW_ACCOUNT) {
            // 신규 계정이므로 회원가입 화면으로 네비게이션
            Log.d(logTag, "new account")
            navController.navigateRegistration()
        } else if (accountStatus == AccountStatus.SIGNED_IN_ACCOUNT) {
            Log.d(logTag, "signed in account")
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = LampBlack)
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(LocalConfiguration.current.screenHeightDp.dp * 0.2f))
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = stringResource(id = R.string.login_header),
                color = Color.White,
                style = Typography.medium18
            )
            Icon(
                painter = painterResource(id = R.drawable.main_lamp_logo),
                contentDescription = "loginLampLogo",
                tint = Color.Unspecified
            )
        }
        val buttonColor = ButtonDefaults.buttonColors(
            containerColor = Gray,
            contentColor = Color.White
        )
        Spacer(modifier = Modifier.height(LocalConfiguration.current.screenHeightDp.dp * 0.25f))
        Column(
            modifier = Modifier.width(270.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Image(
                painter = painterResource(R.drawable.google_sign_in_btn),
                contentDescription = "Google Sign In",
                modifier = Modifier
                    .height(48.dp)
                    .width(270.dp)
                    .clickable {
                        val signInIntent = viewModel.getSignInIntent()
                        signInLauncher.launch(signInIntent)
                    }
            )
            Spacer(modifier = Modifier.height(35.dp))
            Text(
                text = stringResource(R.string.policy_agree_msg),
                color = Color.White,
                style = Typography.medium10
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(18.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.clickable {
                        val intent = Intent(Intent.ACTION_VIEW, (Const.TERMS_OF_SERVICE_URL).toUri())
                        context.startActivity(intent)
                    },
                    text = stringResource(R.string.terms_and_conditions),
                    style = Typography.normal12.copy(
                        textDecoration = TextDecoration.Underline
                    ),
                    color = Color.White
                )
                Text(
                    modifier = Modifier.clickable {
                        val intent = Intent(Intent.ACTION_VIEW, (Const.PRIVACY_POLICY_URL).toUri())
                        context.startActivity(intent)
                    },
                    text = stringResource(R.string.privacy_policy),
                    style = Typography.normal12.copy(
                        textDecoration = TextDecoration.Underline
                    ),
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(48.dp))
        }
    }
}
