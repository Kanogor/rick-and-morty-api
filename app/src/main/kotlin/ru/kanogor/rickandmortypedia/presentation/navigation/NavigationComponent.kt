package ru.kanogor.rickandmortypedia.presentation.navigation

import androidx.paging.PagingData
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable
import ru.kanogor.rickandmortypedia.domain.entity.CharacterData
import ru.kanogor.rickandmortypedia.domain.entity.LocationData
import ru.kanogor.rickandmortypedia.presentation.components.toStateFlow
import ru.kanogor.rickandmortypedia.presentation.main.MainComponent
import ru.kanogor.rickandmortypedia.presentation.main.MainComponentImpl
import ru.kanogor.rickandmortypedia.presentation.navigation.NavigationComponent.NavChild
import ru.kanogor.rickandmortypedia.presentation.singleCharacter.SingleCharacterComponent
import ru.kanogor.rickandmortypedia.presentation.singleCharacter.SingleCharacterComponentImpl

interface NavigationComponent {

    val childStack: StateFlow<ChildStack<*, NavChild>>

    sealed interface NavChild {
        data class Main(val component: MainComponent) : NavChild
        data class SingleCharacter(val component: SingleCharacterComponent) : NavChild
    }

}

class NavigationComponentImpl(
    componentContext: ComponentContext,
    private val locationsData: Flow<PagingData<LocationData>>,
    private val charactersData: Flow<PagingData<CharacterData>>,
    private val getSingleCharacterData: suspend  (Int) -> CharacterData?,
) : ComponentContext by componentContext, NavigationComponent {

    private val navigation = StackNavigation<NavChildConfig>()

    override val childStack: StateFlow<ChildStack<*, NavChild>> = childStack(
        serializer = NavChildConfig.serializer(),
        source = navigation,
        initialConfiguration = NavChildConfig.Main,
        handleBackButton = false,
        childFactory = ::createChild
    ).toStateFlow(lifecycle)

    private fun createChild(
        config: NavChildConfig,
        componentContext: ComponentContext
    ): NavChild = when (config) {
        is NavChildConfig.Main -> NavChild.Main(
            component = MainComponentImpl(
                componentContext = componentContext,
                locationsData = locationsData,
                charactersData = charactersData,
                navigateToSingleCharacter = {
                    navigation.bringToFront(NavChildConfig.SingleCharacter(it))
                }
            )
        )

        is NavChildConfig.SingleCharacter -> NavChild.SingleCharacter(
            component = SingleCharacterComponentImpl(
                componentContext = componentContext,
                getSingleCharacterData = getSingleCharacterData,
                characterId = config.characterId,
                onBackClick = {
                    navigation.pop()
                }
            )
        )
    }

}

@Serializable
sealed interface NavChildConfig {

    @Serializable
    data object Main : NavChildConfig

    @Serializable
    data class SingleCharacter(val characterId: Int) : NavChildConfig
}