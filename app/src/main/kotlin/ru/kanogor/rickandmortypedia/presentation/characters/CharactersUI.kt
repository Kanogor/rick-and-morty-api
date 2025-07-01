@file:OptIn(ExperimentalMaterial3Api::class)

package ru.kanogor.rickandmortypedia.presentation.characters

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.AppBarDefaults
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import ru.kanogor.rickandmortypedia.R
import ru.kanogor.rickandmortypedia.data.dto.EpisodeDto
import ru.kanogor.rickandmortypedia.domain.entity.CharacterData
import ru.kanogor.rickandmortypedia.presentation.charDetail.EpisodeCard
import ru.kanogor.rickandmortypedia.presentation.ErrorItem
import ru.kanogor.rickandmortypedia.presentation.LoadingItem
import ru.kanogor.rickandmortypedia.presentation.MainViewModel
import ru.kanogor.rickandmortypedia.presentation.charDetail.getEpisodeData
import ru.kanogor.rickandmortypedia.presentation.theme.GreyBackground
import ru.kanogor.rickandmortypedia.presentation.theme.GreyText

private const val ALIVE = "Alive"

@Composable
fun CharactersList(
    viewModel: MainViewModel,
    onClick: () -> Unit,
    charItem: MutableState<CharacterData?>
) {
    val pagedCharacters = viewModel.pagedCharacters().collectAsLazyPagingItems()
    pagedCharacters.refresh()
    LazyColumn {
        items(
            count = pagedCharacters.itemCount,
            key = pagedCharacters.itemKey { it.id },
            contentType = pagedCharacters.itemContentType { "char" }
        ) { index ->
            val item = pagedCharacters[index]
            CharacterListItem(item!!, onClick, charItem)
        }

        pagedCharacters.apply {
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
                            pagedCharacters.refresh()
                        }
                    }
                }

                loadState.append is LoadState.Error -> {
                    item {
                        ErrorItem {
                            pagedCharacters.refresh()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CharacterListItem(
    character: CharacterData,
    onClick: () -> Unit,
    charItem: MutableState<CharacterData?>
) {
    val isAlive = character.status == ALIVE
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .clickable {
                charItem.value = character
                onClick.invoke()
            },
        colors = CardDefaults.cardColors(
            containerColor = GreyBackground
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            AsyncImage(
                modifier = Modifier
                    .size(180.dp)
                    .padding(8.dp),
                model = character.image,
                contentDescription = "image_1",
                placeholder = painterResource(
                    id = R.drawable.non_picture
                )
            )
            Column {
                Text(
                    text = character.name,
                    modifier = Modifier.padding(8.dp),
                    color = Color.White,
                    fontSize = 20.sp
                )
                Row {
                    Canvas(
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .size(size = 6.dp),
                        onDraw = {
                            drawCircle(
                                color = if (isAlive) Color.Green
                                else Color.Red
                            )
                        },
                    )
                    Text(
                        text = character.status,
                        modifier = Modifier.padding(8.dp),
                        color = Color.White
                    )
                }
                Text(
                    text = stringResource(R.string.last_known_location),
                    modifier = Modifier.padding(8.dp),
                    color = GreyText
                )
                Text(
                    text = character.locationCharData.name,
                    modifier = Modifier.padding(8.dp),
                    color = Color.White
                )
            }

        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CharacterSingleItem(character: CharacterData, onClick: () -> Unit, context: Context) {
    val episodeList = remember {
        mutableStateOf<MutableList<EpisodeDto>>(mutableListOf())
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.person_details),
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = GreyBackground),
                modifier = Modifier
                    .shadow(
                        elevation = AppBarDefaults.TopAppBarElevation,
                    )
                    .zIndex(8f),
                navigationIcon = {
                    IconButton(onClick = { onClick.invoke() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            tint = Color.White,
                            contentDescription = "arrow_back"
                        )
                    }
                }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = GreyBackground)
                .padding(top = 60.dp)
        ) {
            item {
                AsyncImage(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .scale(1.4f),
                    model = character.image,
                    contentDescription = "${character.name}_image",
                    placeholder = painterResource(
                        id = R.drawable.non_picture
                    )
                )
                Text(
                    modifier = Modifier.padding(top = 80.dp, start = 26.dp),
                    text = character.name,
                    color = Color.White,
                    fontSize = 30.sp,
                    fontWeight = FontWeight(weight = 700)
                )
                val colorStops = arrayOf(
                    0.0f to Color.White,
                    0.7f to GreyBackground
                )
                Box(
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .fillMaxWidth()
                        .height(2.dp)
                        .background(Brush.horizontalGradient(colorStops = colorStops))
                )
                Text(
                    text = stringResource(id = R.string.live_status),
                    modifier = Modifier.padding(top = 6.dp, start = 26.dp),
                    color = GreyText
                )
                val isAlive = character.status == ALIVE
                Row {
                    Canvas(
                        modifier = Modifier
                            .padding(top = 8.dp, start = 26.dp)
                            .size(size = 6.dp),
                        onDraw = {
                            drawCircle(
                                color = if (isAlive) Color.Green
                                else Color.Red
                            )
                        },
                    )
                    Text(
                        text = character.status,
                        modifier = Modifier.padding(4.dp),
                        color = Color.White
                    )
                }
                Text(
                    text = stringResource(id = R.string.species_and_gender),
                    modifier = Modifier.padding(top = 12.dp, start = 26.dp),
                    color = GreyText
                )
                Text(
                    text = character.species,
                    modifier = Modifier.padding(top = 4.dp, start = 26.dp),
                    color = Color.White
                )
                Text(
                    text = stringResource(R.string.last_known_location),
                    modifier = Modifier.padding(top = 12.dp, start = 26.dp),
                    color = GreyText
                )
                Text(
                    text = character.locationCharData.name,
                    modifier = Modifier.padding(top = 4.dp, start = 26.dp),
                    color = Color.White
                )
                Text(
                    modifier = Modifier.padding(top = 30.dp, start = 26.dp),
                    text = stringResource(id = R.string.episodes),
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight(weight = 700)
                )
            }
            val urlList = character.episode
            val newList =
                urlList.take(10) // уменьшено число, так как очень долго грузится страница Рика и Морти(все эпизоды за все сезоны)
            newList.onEach { item ->
                getEpisodeData(
                    url = item,
                    context = context,
                    list = episodeList
                )
            }
            itemsIndexed(items = episodeList.value) { _, item ->
                if (episodeList.value.isNotEmpty()) EpisodeCard(item = item)
            }
        }
    }
}
