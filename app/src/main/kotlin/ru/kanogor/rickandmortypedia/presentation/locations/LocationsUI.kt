package ru.kanogor.rickandmortypedia.presentation.locations

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import ru.kanogor.rickandmortypedia.domain.entity.LocationData
import ru.kanogor.rickandmortypedia.presentation.components.BoxPullToRefresh
import ru.kanogor.rickandmortypedia.presentation.components.ErrorItem
import ru.kanogor.rickandmortypedia.presentation.theme.GreyCard
import ru.kanogor.rickandmortypedia.presentation.theme.GreyText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationsUi(
    locationsComponent: LocationsComponent?,
    locationsViewModel: LocationsViewModel = koinViewModel()
) {
    if (locationsComponent != null) {
        val isError by locationsViewModel.isError.collectAsState()
        val pagedLocations = locationsViewModel.locations.collectAsLazyPagingItems()
        var mutableLocationsCount by rememberSaveable { mutableIntStateOf(0) }
        val locationsCount by remember { derivedStateOf { mutableLocationsCount } }
        val isLoadingLocations by remember(
            pagedLocations.loadState.refresh
        ) {
            mutableStateOf(
                pagedLocations.loadState.refresh == LoadState.Loading
            )
        }
        val isVisibleLocations by remember(
            pagedLocations.loadState.refresh,
        ) {
            mutableStateOf(
                !isLoadingLocations && locationsCount > 0
            )
        }

        LaunchedEffect(Unit) {
            if (!isVisibleLocations) {
                locationsViewModel.getPagedLocations()
            }
        }

        LaunchedEffect(pagedLocations) {
            launch {
                snapshotFlow { pagedLocations.loadState.refresh }
                    .collect { loadState ->
                        locationsViewModel.showErrorMessage(loadState is LoadState.Error)
                    }
            }

            launch {
                snapshotFlow {
                    pagedLocations.itemSnapshotList.mapNotNull { it }.count()
                }.collect { data ->
                    mutableLocationsCount = data
                }
            }
        }

        BoxPullToRefresh(
            modifier = Modifier,
            isRefreshing = isLoadingLocations,
            onRefresh = {
                pagedLocations.refresh()
            },
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                if (!isLoadingLocations) {
                    LocationsContent(
                        data = pagedLocations,
                        isVisibleLocations = isVisibleLocations,
                        locationsCount = locationsCount,
                        component = locationsComponent
                    )
                }

                if (isVisibleLocations) {
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(Color.White)
                    ) {
                        Spacer(
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                if (isError) {
                    ErrorItem {
                        pagedLocations.refresh()
                    }
                }
            }
        }
    }
}

@Composable
private fun LocationsContent(
    component: LocationsComponent,
    data: LazyPagingItems<LocationData>,
    isVisibleLocations: Boolean,
    locationsCount: Int
) {
    val scrollState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        state = scrollState
    ) {

        if (locationsCount > 0) {
            if (isVisibleLocations) {
                items(
                    count = data.itemCount,
                    key = data.itemKey { it.id },
                    contentType = data.itemContentType { "location" }
                ) { index ->
                    val item = data[index]
                    if (item != null) {
                        LocationListItem(
                            item = item,
                            onClick = {
                                component.onLocationClick(item.id)
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun LocationListItem(item: LocationData, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = GreyCard
        )
    ) {
        Column(
            modifier = Modifier
                .padding(top = 6.dp, start = 18.dp, bottom = 6.dp, end = 6.dp)
                .clickable {
                    onClick.invoke()
                },
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = item.name,
                    color = Color.White,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(weight = 700)
                )
                Text(
                    text = item.type,
                    color = GreyText,
                    fontSize = 12.sp
                )
            }
            Text(
                text = item.dimension,
                color = GreyText,
                fontSize = 12.sp
            )
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun LocationListItemPreview() {
    Column {
        LocationListItem(
            item = LocationData(
                dimension = "dimension",
                id = 0,
                name = "Earth",
                type = "Type"
            ),
            onClick = {}
        )
    }
}