package ru.kanogor.rickandmortypedia.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import ru.kanogor.rickandmortypedia.entity.LocationData
import ru.kanogor.rickandmortypedia.presentation.theme.GreyBackground
import ru.kanogor.rickandmortypedia.presentation.theme.GreyText

@Composable
fun LocationsList(viewModel: MainViewModel) {
    val pagedLocations = viewModel.pagedLocations.collectAsLazyPagingItems()
    pagedLocations.refresh()
    LazyColumn {
        items(
            count = pagedLocations.itemCount,
            key = pagedLocations.itemKey { it.id },
            contentType = pagedLocations.itemContentType { "location" }
        ) { index ->
            val item = pagedLocations[index]
            LocationListItem(item = item!!)
        }
        pagedLocations.apply {
            when {
                loadState.refresh is LoadState.Loading -> {
                    item { LoadingItem() }
                }

                loadState.append is LoadState.Loading -> {
                    item { LoadingItem() }
                }

                loadState.refresh is LoadState.Error -> {
                    item {
                        ErrorItem {
                            pagedLocations.refresh()
                        }
                    }
                }

                loadState.append is LoadState.Error -> {
                    item {
                        ErrorItem {
                            pagedLocations.refresh()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LocationListItem(item: LocationData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = GreyBackground
        )
    ) {
        Column(
            modifier = Modifier.padding(top = 6.dp, start = 18.dp, bottom = 6.dp, end = 6.dp),
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