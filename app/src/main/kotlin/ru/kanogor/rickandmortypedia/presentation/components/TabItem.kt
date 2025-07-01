package ru.kanogor.rickandmortypedia.presentation.components

import androidx.compose.runtime.Composable

data class TabItem(
    val text: String,
    val screen: @Composable () -> Unit
)
