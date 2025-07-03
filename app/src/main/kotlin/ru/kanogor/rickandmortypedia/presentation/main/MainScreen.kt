package ru.kanogor.rickandmortypedia.presentation.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.kanogor.rickandmortypedia.R
import ru.kanogor.rickandmortypedia.presentation.characters.CharactersUi
import ru.kanogor.rickandmortypedia.presentation.locations.LocationsUi
import ru.kanogor.rickandmortypedia.presentation.theme.GreyBackground
import ru.kanogor.rickandmortypedia.presentation.theme.GreyCard

// TODO рефакторинг, добавить перелистывание страниц про свайпу
@Composable
fun MainScreen(
    component: MainComponent
) {
    val childStack by component.mainChildStack.collectAsState()
    val activeComponent = childStack.active.instance

    val pagerState = rememberPagerState(
        initialPage = when (activeComponent) {
            is MainComponent.MainChild.Characters -> component.mainTabs.indexOf(MainTabs.CHARACTERS)
            is MainComponent.MainChild.Locations -> component.mainTabs.indexOf(MainTabs.LOCATIONS)
        },
        pageCount = { component.mainTabs.size }
    )

    var currentPage by remember {
        mutableIntStateOf(pagerState.currentPage)
    }

    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
        if (pagerState.isScrollInProgress && pagerState.currentPage != currentPage) {
            when (pagerState.currentPage) {
                component.mainTabs.indexOf(MainTabs.CHARACTERS) -> component.onTabSelected(
                    ChildConfig.Characters
                )

                component.mainTabs.indexOf(MainTabs.LOCATIONS) -> component.onTabSelected(
                    ChildConfig.Locations
                )
            }
            currentPage = pagerState.currentPage
        }
    }

    LaunchedEffect(activeComponent) {
        when (activeComponent) {
            is MainComponent.MainChild.Characters -> {
                pagerState.animateScrollToPage(component.mainTabs.indexOf(MainTabs.CHARACTERS))
            }

            is MainComponent.MainChild.Locations -> {
                pagerState.animateScrollToPage(component.mainTabs.indexOf(MainTabs.LOCATIONS))
            }
        }
    }

    Column(
        modifier = Modifier
            .padding(top = 6.dp)
    ) {
        PrimaryTabRow(
            selectedTabIndex = when (activeComponent) {
                is MainComponent.MainChild.Characters -> {
                    component.mainTabs.indexOf(MainTabs.CHARACTERS)
                }

                is MainComponent.MainChild.Locations -> {
                    component.mainTabs.indexOf(MainTabs.LOCATIONS)
                }
            },
            containerColor = GreyBackground,
            contentColor = Color.White
        ) {
            Tab(
                selected = activeComponent is MainComponent.MainChild.Characters,
                onClick = {
                    component.onTabSelected(ChildConfig.Characters)
                },
                text = {
                    Text(text = stringResource(R.string.characters))
                }
            )

            Tab(
                selected = activeComponent is MainComponent.MainChild.Locations,
                onClick = {
                    component.onTabSelected(ChildConfig.Locations)
                },
                text = {
                    Text(text = stringResource(R.string.locations))
                }
            )
        }

        HorizontalPager(
            state = pagerState
        ) { page ->
            when (page) {
                component.mainTabs.indexOf(MainTabs.CHARACTERS) -> {
                    CharactersUi(
                        (childStack.active.instance
                                as? MainComponent.MainChild.Characters)?.component
                    )
                }

                component.mainTabs.indexOf(MainTabs.LOCATIONS) -> {
                    LocationsUi(
                        (childStack.active.instance
                                as? MainComponent.MainChild.Locations)?.component
                    )
                }
            }
        }
    }
}