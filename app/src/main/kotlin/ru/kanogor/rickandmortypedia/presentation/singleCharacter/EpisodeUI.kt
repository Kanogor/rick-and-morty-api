package ru.kanogor.rickandmortypedia.presentation.singleCharacter

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.kanogor.rickandmortypedia.domain.entity.Episode
import ru.kanogor.rickandmortypedia.presentation.components.LoadingItem
import ru.kanogor.rickandmortypedia.presentation.theme.GreyBackground
import ru.kanogor.rickandmortypedia.presentation.theme.GreyText

fun LazyListScope.episodeList(isEpisodesLoading: Boolean, episodeList: List<Episode>) {
    if (isEpisodesLoading) {
        item {
            LoadingItem()
        }
    } else {
        items(items = episodeList) { item ->
            if (episodeList.isNotEmpty()) EpisodeCard(item = item)
        }
    }
}

@Composable
fun EpisodeCard(item: Episode) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
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
                    text = item.episodeNum,
                    color = GreyText,
                    fontSize = 12.sp
                )
            }
            Text(
                text = item.airDate,
                color = GreyText,
                fontSize = 12.sp
            )
        }

    }
}