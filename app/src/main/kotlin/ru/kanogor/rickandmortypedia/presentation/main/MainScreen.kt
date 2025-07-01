package ru.kanogor.rickandmortypedia.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.kanogor.rickandmortypedia.R
import ru.kanogor.rickandmortypedia.presentation.characters.CharactersUi
import ru.kanogor.rickandmortypedia.presentation.locations.LocationsUi
import ru.kanogor.rickandmortypedia.presentation.theme.GreyBackground
import ru.kanogor.rickandmortypedia.presentation.theme.GreyCard

// TODO рефакторинг, добавить перелистывание страниц по свайпу
@Composable
fun MainScreen(
    component: MainComponent
) {
    val childStack by component.mainChildStack.collectAsState()
    val activeComponent = childStack.active.instance

    Column(
        modifier = Modifier
            .padding(top = 6.dp)
    ) {
        PrimaryTabRow(
            selectedTabIndex = when (activeComponent) {
                is MainComponent.MainChild.Characters -> 0
                is MainComponent.MainChild.Locations -> 1
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

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = GreyCard),
        ) {
            when (activeComponent) {
                is MainComponent.MainChild.Characters -> CharactersUi(activeComponent.component)
                is MainComponent.MainChild.Locations -> LocationsUi(activeComponent.component)
            }
        }
    }
}