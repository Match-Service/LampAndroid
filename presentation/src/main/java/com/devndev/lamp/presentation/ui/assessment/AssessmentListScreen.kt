package com.devndev.lamp.presentation.ui.assessment

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.devndev.lamp.domain.model.assessment.AssessmentListDomainModel
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.theme.Gray
import com.devndev.lamp.presentation.theme.Gray3
import com.devndev.lamp.presentation.theme.LampBlack
import com.devndev.lamp.presentation.theme.Typography
import com.devndev.lamp.presentation.ui.common.TopNavigationBar
import com.devndev.lamp.presentation.ui.review.navigation.navigateReview
import com.devndev.lamp.presentation.utils.DateFormatUtil

@Composable
fun AssessmentListScreen(
    modifier: Modifier,
    navController: NavController,
    viewModel: AssessmentListViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(LampBlack)
            .padding(horizontal = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(30.5.dp)
    ) {
        TopNavigationBar(
            text = stringResource(id = R.string.assessment_title),
            isNeedXButton = false,
            onBackButtonClick = { navController.popBackStack() }
        )
        Text(
            modifier = Modifier.padding(top = 20.5.dp),
            textAlign = TextAlign.Center,
            text = stringResource(id = R.string.assessment_msg),
            style = Typography.normal14,
            color = Color.White
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(state.assessmentList) { assessment ->
                AssessmentItem(
                    assessment = assessment,
                    onAssessmentClick = {
                        navController.navigateReview(lampMatchId = it)
                    }
                )
            }
        }
    }
}

@Composable
fun AssessmentItem(
    assessment: AssessmentListDomainModel,
    onAssessmentClick: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Gray, shape = RoundedCornerShape(80.dp))
            .clickable {
                onAssessmentClick(assessment.lampMatchId)
            }
            .padding(vertical = 12.dp, horizontal = 30.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp)
        ) {
            Text(
                text = stringResource(R.string.assessment_item_title, assessment.otherLampName),
                style = Typography.medium18,
                color = Color.White
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(3.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.heart),
                    contentDescription = null,
                    tint = Gray3,
                    modifier = Modifier
                        .size(8.dp)
                )
                Text(
                    text = DateFormatUtil.formatToYearMonthDay(assessment.meetingTime),
                    color = Gray3,
                    style = Typography.normal12
                )
                Spacer(modifier = Modifier.width(7.dp))
                Icon(
                    painter = painterResource(id = R.drawable.people_icon),
                    contentDescription = null,
                    tint = Gray3,
                    modifier = Modifier
                        .size(8.dp)
                )
                Text(
                    text = "${assessment.meetingUserCount} : ${assessment.meetingUserCount}",
                    color = Gray3,
                    style = Typography.normal12
                )
            }
        }
        Icon(
            painter = painterResource(id = R.drawable.arrow),
            contentDescription = null,
            modifier = Modifier.size(16.dp),
            tint = Color.White
        )
    }
}
