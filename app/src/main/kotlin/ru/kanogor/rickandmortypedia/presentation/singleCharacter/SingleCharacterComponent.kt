package ru.kanogor.rickandmortypedia.presentation.singleCharacter

import com.arkivanov.decompose.ComponentContext

interface SingleCharacterComponent {

    val characterId: Int

    fun onBackClick()

}

class SingleCharacterComponentImpl(
    componentContext: ComponentContext,
    private val onBackClick: () -> Unit,
    override val characterId: Int
) : ComponentContext by componentContext, SingleCharacterComponent {

    override fun onBackClick() {
        onBackClick.invoke()
    }
}