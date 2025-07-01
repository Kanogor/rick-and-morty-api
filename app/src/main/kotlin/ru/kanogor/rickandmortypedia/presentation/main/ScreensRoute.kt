package ru.kanogor.rickandmortypedia.presentation.main

sealed class Screen(val route: String) {
    data object CharacterItem : Screen("character_item")
    data object MainScreen : Screen("main_screen")
}