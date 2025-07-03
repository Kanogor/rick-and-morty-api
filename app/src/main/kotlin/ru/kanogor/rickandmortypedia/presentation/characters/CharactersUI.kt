package ru.kanogor.rickandmortypedia.presentation.characters

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import ru.kanogor.rickandmortypedia.R
import ru.kanogor.rickandmortypedia.domain.entity.CharacterData
import ru.kanogor.rickandmortypedia.presentation.components.ErrorItem
import ru.kanogor.rickandmortypedia.presentation.components.LoadingItem
import ru.kanogor.rickandmortypedia.presentation.theme.GreyBackground
import ru.kanogor.rickandmortypedia.presentation.theme.GreyText

const val ALIVE = "Alive" // TODO заменить на энум

@Composable
fun CharactersUi(
    component: CharactersComponent?
) {
    if (component != null) {
        val pagedCharacters = component.characters.collectAsLazyPagingItems()
        pagedCharacters.refresh()
        LazyColumn {
            items(
                count = pagedCharacters.itemCount,
                key = pagedCharacters.itemKey { it.id },
                contentType = pagedCharacters.itemContentType { "char" }
            ) { index ->
                val item = pagedCharacters[index]
                if (item != null) {
                    CharacterListItem(
                        character = item,
                        onClick = {
                            component.onCharClick(item.id)
                        }
                    )
                }
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
}

@Composable
fun CharacterListItem(
    character: CharacterData,
    onClick: () -> Unit,
) {
    val isAlive = character.status == ALIVE
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .clickable {
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

// TODO добавить превью для CharacterListItem