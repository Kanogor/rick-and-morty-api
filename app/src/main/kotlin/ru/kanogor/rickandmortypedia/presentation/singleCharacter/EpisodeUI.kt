package ru.kanogor.rickandmortypedia.presentation.singleCharacter

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import ru.kanogor.rickandmortypedia.data.dto.EpisodeDto
import ru.kanogor.rickandmortypedia.presentation.theme.GreyCard
import ru.kanogor.rickandmortypedia.presentation.theme.GreyText

// TODO это пиздец, только ломать)

fun getEpisodeData(
    url: String,
    context: Context,
    list: MutableState<MutableList<EpisodeDto>>
) {
    val queue = Volley.newRequestQueue(context)
    val stringRequest = StringRequest(
        Request.Method.GET, url,
        { response ->
            val episode = getEpisodeResponse(response)
            if (episode != null) list.value.add(episode)
        },
        { error ->
            Log.d("EpisodeData", "VolleyResponse -> $error")
        }
    )
    queue.add(stringRequest)
}

private fun getEpisodeResponse(response: String): EpisodeDto? {
    if (response.isEmpty()) return null
    val mainObject = JSONObject(response)
    val name = mainObject.getString("name")
    val airDate: String = mainObject.getString("air_date")
    val episodeNum: String = mainObject.getString("episode")
    return EpisodeDto(name, airDate, episodeNum)
}

@Composable
fun EpisodeCard(item: EpisodeDto) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = GreyCard
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