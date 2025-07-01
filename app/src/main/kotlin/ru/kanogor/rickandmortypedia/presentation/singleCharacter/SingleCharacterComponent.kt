package ru.kanogor.rickandmortypedia.presentation.singleCharacter

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.doOnDestroy
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.kanogor.rickandmortypedia.domain.entity.CharacterData

interface SingleCharacterComponent {

    val characterId: Int

    val singleCharacter: StateFlow<CharacterData?>
    val isLoading: StateFlow<Boolean>

    fun getSingleCharacter(id: Int)

    fun onBackClick()

}

class SingleCharacterComponentImpl(
    componentContext: ComponentContext,
    private val getSingleCharacterData: suspend (Int) -> CharacterData?,
    private val onBackClick: () -> Unit,
    override val characterId: Int
) : ComponentContext by componentContext, SingleCharacterComponent {

    override val singleCharacter = MutableStateFlow<CharacterData?>(null)
    override val isLoading = MutableStateFlow(false)

    private val coroutineScope = componentCoroutineScope()

    override fun getSingleCharacter(id: Int) {
        isLoading.value = true
        coroutineScope.launch {
            val character = getSingleCharacterData(id)
            singleCharacter.update { character }
        }
        isLoading.value = false
    }

    override fun onBackClick() {
        onBackClick.invoke()
    }
}

fun ComponentContext.componentCoroutineScope(): CoroutineScope {
    val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main.immediate)

    if (lifecycle.state != Lifecycle.State.DESTROYED) {
        lifecycle.doOnDestroy {
            scope.cancel()
        }
    } else {
        scope.cancel()
    }

    return scope
}