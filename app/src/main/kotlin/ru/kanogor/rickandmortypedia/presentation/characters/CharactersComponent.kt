package ru.kanogor.rickandmortypedia.presentation.characters

import com.arkivanov.decompose.ComponentContext

interface CharactersComponent {

    fun onCharClick(id: Int)

}

class CharactersComponentImpl(
    private val navigateToSingleCharacter: (id: Int) -> Unit,
    componentContext: ComponentContext
) : ComponentContext by componentContext, CharactersComponent {
    override fun onCharClick(id: Int) {
        navigateToSingleCharacter(id)
    }
}