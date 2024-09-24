package com.devndev.lamp.presentation.ui.registration

import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.devndev.lamp.presentation.R
import com.devndev.lamp.presentation.ui.common.SelectionScreen
import com.devndev.lamp.presentation.ui.theme.ManColor
import com.devndev.lamp.presentation.ui.theme.Typography
import com.devndev.lamp.presentation.ui.theme.WomanColor
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map

@Composable
fun BirthScreen(
    isMan: Boolean,
    selectedYear: String,
    selectedMonth: String,
    selectedDay: String,
    onYearChange: (String) -> Unit,
    onMonthChange: (String) -> Unit,
    onDayChange: (String) -> Unit
) {
    SelectionScreen(text = stringResource(id = R.string.input_birthday)) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = stringResource(id = R.string.birth_guide),
            color = Color.White,
            style = Typography.normal12
        )
        val years = remember { (1900..2024).map { it.toString() + "년" } }
        val months = remember { (1..12).map { it.toString() + "월" } }
        val days = remember { (1..31).map { it.toString() + "일" } }
        Spacer(modifier = Modifier.height(30.dp))
        DatePicker(
            isMan = isMan,
            years = years,
            months = months,
            days = days,
            selectedYear = selectedYear,
            selectedMonth = selectedMonth,
            selectedDay = selectedDay,
            onYearChange = onYearChange,
            onMonthChange = onMonthChange,
            onDayChange = onDayChange
        )
    }
}

@Composable
fun DatePicker(
    isMan: Boolean,
    years: List<String>,
    months: List<String>,
    days: List<String>,
    selectedYear: String,
    selectedMonth: String,
    selectedDay: String,
    onYearChange: (String) -> Unit,
    onMonthChange: (String) -> Unit,
    onDayChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val yearPickerState = rememberPickerState()
    val monthPickerState = rememberPickerState()
    val dayPickerState = rememberPickerState()

    LaunchedEffect(yearPickerState.selectedItem) {
        onYearChange(yearPickerState.selectedItem)
    }
    LaunchedEffect(monthPickerState.selectedItem) {
        onMonthChange(monthPickerState.selectedItem)
    }
    LaunchedEffect(dayPickerState.selectedItem) {
        onDayChange(dayPickerState.selectedItem)
    }

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        // Year
        Picker(
            isMan = isMan,
            items = years,
            state = yearPickerState,
            visibleItemsCount = 5,
            startIndex = years.indexOf(selectedYear),
            textAlign = TextAlign.End
        )

        // Month
        Picker(
            isMan = isMan,
            items = months,
            state = monthPickerState,
            visibleItemsCount = 5,
            startIndex = months.indexOf(selectedMonth),
            textAlign = TextAlign.Center
        )

        // Day
        Picker(
            isMan = isMan,
            items = days,
            state = dayPickerState,
            visibleItemsCount = 5,
            startIndex = days.indexOf(selectedDay),
            textAlign = TextAlign.Start
        )
    }
}

@Composable
fun Picker(
    isMan: Boolean,
    items: List<String>,
    state: PickerState = rememberPickerState(),
    modifier: Modifier = Modifier,
    startIndex: Int = 0,
    visibleItemsCount: Int = 5,
    textAlign: TextAlign
) {
    val visibleItemsMiddle = visibleItemsCount / 2
    val listScrollCount = Integer.MAX_VALUE
    val listScrollMiddle = listScrollCount / 2
    val listStartIndex =
        listScrollMiddle - listScrollMiddle % items.size - visibleItemsMiddle + startIndex

    fun getItem(index: Int) = items[index % items.size]

    val listState = rememberLazyListState(initialFirstVisibleItemIndex = listStartIndex)
    val flingBehavior = rememberSnapFlingBehavior(lazyListState = listState)

    val fadingEdgeGradient = remember {
        Brush.verticalGradient(
            0f to Color.Transparent,
            0.5f to Color.Black,
            1f to Color.Transparent
        )
    }

    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .map { index -> getItem(index + visibleItemsMiddle) }
            .distinctUntilChanged()
            .collect { item -> state.selectedItem = item }
    }

    Box(modifier = modifier) {
        LazyColumn(
            state = listState,
            flingBehavior = flingBehavior,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .height(30.dp * visibleItemsCount)
                .fadingEdge(fadingEdgeGradient)
        ) {
            items(listScrollCount) { index ->
                val item = getItem(index)
                val currentIndex = listState.firstVisibleItemIndex
                val itemIndex = index - currentIndex

                // Animate font size and color
                val targetFontSize = when (itemIndex) {
                    visibleItemsMiddle -> 18f
                    in (visibleItemsMiddle - 1)..(visibleItemsMiddle + 1) -> 15f
                    in (visibleItemsMiddle - 2)..(visibleItemsMiddle + 2) -> 12f
                    else -> 12f
                }

                val animatedTextStyle = when (targetFontSize) {
                    18f -> Typography.medium18
                    15f -> Typography.medium15
                    12f -> Typography.normal12
                    else -> Typography.normal12
                }.copy(
                    color = if (itemIndex == visibleItemsMiddle) {
                        if (isMan) ManColor else WomanColor
                    } else {
                        Color.LightGray
                    }
                )

                Text(
                    text = item,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = animatedTextStyle,
                    modifier = Modifier
                        .width(100.dp)
                        .height(30.dp)
                        .padding(vertical = 2.dp),
                    textAlign = textAlign
                )
            }
        }
    }
}

private fun Modifier.fadingEdge(brush: Brush) = this
    .graphicsLayer(compositingStrategy = CompositingStrategy.Offscreen)
    .drawWithContent {
        drawContent()
        drawRect(brush = brush, blendMode = BlendMode.DstIn)
    }

@Composable
fun rememberPickerState() = remember { PickerState() }

class PickerState {
    var selectedItem by mutableStateOf("")
}
