package ru.kanogor.rickandmortypedia.presentation.characters

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import ru.kanogor.rickandmortypedia.R
import ru.kanogor.rickandmortypedia.domain.entity.CharacterData
import ru.kanogor.rickandmortypedia.domain.entity.Status
import ru.kanogor.rickandmortypedia.domain.entity.Status.Companion.toText
import ru.kanogor.rickandmortypedia.domain.entity.previewCharacterData
import ru.kanogor.rickandmortypedia.presentation.components.BoxPullToRefresh
import ru.kanogor.rickandmortypedia.presentation.components.ErrorItem
import ru.kanogor.rickandmortypedia.presentation.theme.GreyCard
import ru.kanogor.rickandmortypedia.presentation.theme.GreyText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersUi(
    component: CharactersComponent?,
    viewModel: CharactersViewModel = koinViewModel()
) {
    if (component != null) {
        val isError by viewModel.isError.collectAsState()
        val pagedCharacters = viewModel.characters.collectAsLazyPagingItems()
        var mutableCharactersCount by rememberSaveable { mutableIntStateOf(0) }
        val charactersCount by remember { derivedStateOf { mutableCharactersCount } }
        val isLoadingCharacters by remember(
            pagedCharacters.loadState.refresh
        ) {
            mutableStateOf(
                pagedCharacters.loadState.refresh == LoadState.Loading
            )
        }
        val isVisibleCharacters by remember(
            pagedCharacters.loadState.refresh,
        ) {
            mutableStateOf(
                !isLoadingCharacters && charactersCount > 0
            )
        }

        LaunchedEffect(Unit) {
            if (!isVisibleCharacters) {
                viewModel.getPagedCharacters()
            }
        }

        LaunchedEffect(pagedCharacters) {
            launch {
                snapshotFlow { pagedCharacters.loadState.refresh }
                    .collect { loadState ->
                        viewModel.showErrorMessage(loadState is LoadState.Error)
                    }
            }

            launch {
                snapshotFlow {
                    pagedCharacters.itemSnapshotList.mapNotNull { it }.count()
                }.collect { data ->
                    mutableCharactersCount = data
                }
            }
        }

        BoxPullToRefresh(
            modifier = Modifier,
            isRefreshing = isLoadingCharacters,
            onRefresh = {
                pagedCharacters.refresh()
            },
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                if (!isLoadingCharacters) {
                    CharactersContent(
                        data = pagedCharacters,
                        isVisibleCharacters = isVisibleCharacters,
                        characterCount = charactersCount,
                        component = component
                    )
                }

                if (isVisibleCharacters) {
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
                        pagedCharacters.refresh()
                    }
                }
            }
        }
    }
}

@Composable
internal fun CharactersContent(
    component: CharactersComponent,
    data: LazyPagingItems<CharacterData>,
    isVisibleCharacters: Boolean,
    characterCount: Int
) {
    val scrollState = rememberLazyListState()

    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        state = scrollState
    ) {

        if (characterCount > 0) {
            if (isVisibleCharacters) {
                items(
                    count = data.itemCount,
                    key = data.itemKey { it.id },
                    contentType = data.itemContentType { "char" }
                ) { index ->
                    val item = data[index]
                    if (item != null) {
                        CharacterListItem(
                            character = item,
                            onClick = {
                                component.onCharClick(item.id)
                            }
                        )
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
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .clickable {
                onClick.invoke()
            },
        colors = CardDefaults.cardColors(
            containerColor = GreyCard
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
                contentDescription = null,
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
                                color = if (character.status == Status.ALIVE) Color.Green
                                else Color.Red
                            )
                        },
                    )
                    Text(
                        text = character.status.toText(),
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

@Preview(
    showBackground = true,
    backgroundColor = 0xFFFFFFFF
)
@Composable
fun CharacterListItemPreview() {
    Column {
        CharacterListItem(
            character = previewCharacterData,
            onClick = {}
        )
    }
}