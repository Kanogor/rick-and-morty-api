package ru.kanogor.rickandmortypedia.presentation

sealed class Screen(val route: String) {
    object CharacterList : Screen("character_list")
    object CharacterItem : Screen("character_item")
    object LocationsList : Screen("location_list")
    object MainScreen : Screen("main_screen")
}