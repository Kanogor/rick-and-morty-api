package ru.kanogor.rickandmortypedia.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import kotlinx.coroutines.launch
import ru.kanogor.rickandmortypedia.entity.CharacterData
import ru.kanogor.rickandmortypedia.presentation.theme.GreyBackground

private const val CHARS = "Characters"
private const val LOCATIONS = "Locations"


@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    onClick: () -> Unit,
    charItem: MutableState<CharacterData?>
) {
    val tabList = listOf(CHARS, LOCATIONS)
    val pagerState = rememberPagerState()
    val tabIndex = pagerState.currentPage
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .padding(top = 6.dp)
    ) {
        TabRow(
            selectedTabIndex = tabIndex,
            indicator = { pos ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, pos)
                )

            },
            backgroundColor = GreyBackground,
            contentColor = Color.White
        ) {
            tabList.forEachIndexed { index, text ->
                Tab(selected = false,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(page = index)
                        }
                    },
                    text = {
                        Text(text = text)
                    }
                )

            }
        }
        HorizontalPager(
            count = tabList.size,
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { index ->
            val screen = when (index) {
                0 -> Screen.CharacterList
                1 -> Screen.LocationsList
                else -> Screen.CharacterList
            }
            MainList(
                screen = screen,
                viewModel = viewModel,
                onClick = { onClick.invoke() },
                charItem = charItem
            )

        }
    }
}

@Composable
fun MainList(
    screen: Screen,
    viewModel: MainViewModel,
    onClick: () -> Unit,
    charItem: MutableState<CharacterData?>
) {
    if (screen == Screen.CharacterList) CharactersList(
        viewModel = viewModel,
        onClick = { onClick.invoke() },
        charItem = charItem
    )
    else LocationsList(viewModel = viewModel)
}