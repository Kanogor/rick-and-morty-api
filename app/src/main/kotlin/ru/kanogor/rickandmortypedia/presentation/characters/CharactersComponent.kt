package ru.kanogor.rickandmortypedia.presentation.characters

import androidx.paging.PagingData
import com.arkivanov.decompose.ComponentContext
import kotlinx.coroutines.flow.Flow
import ru.kanogor.rickandmortypedia.domain.entity.CharacterData

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