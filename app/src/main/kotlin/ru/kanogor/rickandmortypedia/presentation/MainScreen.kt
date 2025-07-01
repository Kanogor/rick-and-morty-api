package ru.kanogor.rickandmortypedia.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import ru.kanogor.rickandmortypedia.R
import ru.kanogor.rickandmortypedia.domain.entity.CharacterData
import ru.kanogor.rickandmortypedia.presentation.components.TabItem
import ru.kanogor.rickandmortypedia.presentation.theme.GreyBackground
import ru.kanogor.rickandmortypedia.presentation.theme.GreyCard

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    onClick: () -> Unit,
    charItem: MutableState<CharacterData?>
) {
    val tabList = listOf(
        TabItem(
            text = stringResource(R.string.characters),
            screen = {
                CharactersList(
                    viewModel = viewModel,
                    onClick = { onClick.invoke() },
                    charItem = charItem
                )
            }
        ), TabItem(
            text = stringResource(R.string.locations),
            screen = {
                LocationsList(viewModel = viewModel)
            }
        )
    )
    val pagerState = rememberPagerState(initialPage = 0) {
        tabList.size
    }
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .padding(top = 6.dp)
    ) {
        PrimaryTabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = GreyBackground,
            contentColor = Color.White
        ) {
            tabList.forEachIndexed { index, tab ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(page = index)
                        }
                    },
                    text = {
                        Text(text = tab.text)
                    }
                )
            }
        }

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxSize()
                .background(color = GreyCard)
        ) { index ->
            tabList[index].screen.invoke()
        }
    }
}