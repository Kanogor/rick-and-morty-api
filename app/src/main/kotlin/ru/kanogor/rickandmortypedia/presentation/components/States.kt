package ru.kanogor.rickandmortypedia.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.kanogor.rickandmortypedia.R
import ru.kanogor.rickandmortypedia.presentation.theme.SecondaryColor

@Composable
fun LoadingItem(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.Transparent
) {
    Surface(
        modifier = modifier.fillMaxSize(),
        color = backgroundColor,
        contentColor = backgroundColor
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = SecondaryColor
            )
        }
    }
}


@Composable
fun ErrorItem(
    errorMessage: String = stringResource(R.string.oops),
    refresh: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = errorMessage,
            modifier = Modifier.padding(top = 26.dp, bottom = 16.dp)
        )
        Button(onClick = { refresh.invoke() }) {
            Text(text = stringResource(R.string.refresh))
        }
    }
}