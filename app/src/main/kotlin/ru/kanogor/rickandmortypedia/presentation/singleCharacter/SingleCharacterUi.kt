package ru.kanogor.rickandmortypedia.presentation.singleCharacter

import android.annotation.SuppressLint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import ru.kanogor.rickandmortypedia.R
import ru.kanogor.rickandmortypedia.domain.entity.CharacterData
import ru.kanogor.rickandmortypedia.domain.entity.Gender.Companion.toText
import ru.kanogor.rickandmortypedia.domain.entity.Status
import ru.kanogor.rickandmortypedia.domain.entity.Status.Companion.toText
import ru.kanogor.rickandmortypedia.domain.entity.previewCharacterData
import ru.kanogor.rickandmortypedia.presentation.components.ErrorItem
import ru.kanogor.rickandmortypedia.presentation.components.LoadingItem
import ru.kanogor.rickandmortypedia.presentation.theme.GreyBackground
import ru.kanogor.rickandmortypedia.presentation.theme.GreyCard
import ru.kanogor.rickandmortypedia.presentation.theme.GreyText


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SingleCharacterUi(
    component: SingleCharacterComponent,
    singleCharacterViewModel: SingleCharacterViewModel = koinViewModel()
) {

    LaunchedEffect(Unit) {
        singleCharacterViewModel.getSingleCharacter(component.characterId)
    }

    val character by singleCharacterViewModel.singleCharacter.collectAsState()
    val isCharacterLoading by singleCharacterViewModel.isCharacterLoading.collectAsState()
    val episodeList by singleCharacterViewModel.episodes.collectAsState()
    val isEpisodesLoading by singleCharacterViewModel.isEpisodesLoading.collectAsState()
    val errorMessage by singleCharacterViewModel.errorMessage.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.person_details),
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = GreyCard),
                modifier = Modifier
                    .zIndex(8f),
                navigationIcon = {
                    IconButton(onClick = { component.onBackClick() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            tint = Color.White,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { paddingValue ->

        if (isCharacterLoading) {
            LoadingItem(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = GreyCard)
            )
        } else {
            if (errorMessage == null) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = GreyCard)
                        .padding(top = paddingValue.calculateTopPadding())
                ) {
                    item {
                        SingleCharacterData(character)
                    }
                    episodeList(
                        isEpisodesLoading = isEpisodesLoading,
                        episodeList = episodeList
                    )
                }
            } else {
                val scope = rememberCoroutineScope()
                ErrorItem(
                    errorMessage = errorMessage.orEmpty()
                ) {
                    scope.launch {
                        singleCharacterViewModel.getSingleCharacter(component.characterId)
                    }
                }
            }
        }
    }
}

@Composable
fun SingleCharacterData(character: CharacterData?) {
    val colorStops = arrayOf(
        0.0f to Color.White,
        0.7f to GreyBackground
    )
    AsyncImage(
        modifier = Modifier
            .fillMaxWidth(),
        contentScale = ContentScale.FillWidth,
        model = character?.image,
        contentDescription = null,
        placeholder = painterResource(
            id = R.drawable.non_picture
        )
    )
    Text(
        modifier = Modifier.padding(top = 12.dp, start = 26.dp),
        text = character?.name.orEmpty(),
        color = Color.White,
        fontSize = 30.sp,
        fontWeight = FontWeight(weight = 700)
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
    Row {
        Canvas(
            modifier = Modifier
                .padding(top = 8.dp, start = 26.dp)
                .size(size = 6.dp),
            onDraw = {
                drawCircle(
                    color = if (character?.status == Status.ALIVE) Color.Green
                    else Color.Red
                )
            },
        )
        Text(
            text = character?.status?.toText() ?: Status.UNKNOWN.toText(),
            modifier = Modifier.padding(4.dp),
            color = Color.White
        )
    }
    Text(
        text = stringResource(id = R.string.species_and_gender),
        modifier = Modifier.padding(top = 12.dp, start = 26.dp),
        color = GreyText
    )
    val speciesAndGender = listOf(character?.species, character?.gender?.toText())
    Text(
        text = speciesAndGender.filterNotNull().joinToString(", "),
        modifier = Modifier.padding(top = 4.dp, start = 26.dp),
        color = Color.White
    )
    Text(
        text = stringResource(R.string.last_known_location),
        modifier = Modifier.padding(top = 12.dp, start = 26.dp),
        color = GreyText
    )
    Text(
        text = character?.locationCharData?.name.orEmpty(),
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

@Preview(
    showBackground = true,
    backgroundColor = 0xFF000000
)
@Composable
fun SingleCharacterDataPreview() {
    Column {
        SingleCharacterData(
            character = previewCharacterData
        )
    }
}