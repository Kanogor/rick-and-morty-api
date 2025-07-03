package ru.kanogor.rickandmortypedia.presentation.main

import androidx.paging.PagingData
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable
import ru.kanogor.rickandmortypedia.domain.entity.CharacterData
import ru.kanogor.rickandmortypedia.domain.entity.LocationData
import ru.kanogor.rickandmortypedia.presentation.characters.CharactersComponent
import ru.kanogor.rickandmortypedia.presentation.characters.CharactersComponentImpl
import ru.kanogor.rickandmortypedia.presentation.components.toStateFlow
import ru.kanogor.rickandmortypedia.presentation.locations.LocationsComponent
import ru.kanogor.rickandmortypedia.presentation.locations.LocationsComponentImpl
import kotlin.enums.EnumEntries

interface MainComponent {

    val mainChildStack: StateFlow<ChildStack<*, MainChild>>

    fun onTabSelected(config: ChildConfig)

    sealed interface MainChild {
        class Characters(val component: CharactersComponent) : MainChild
        class Locations(val component: LocationsComponent) : MainChild
    }

    val mainTabs: EnumEntries<MainTabs>

}

class MainComponentImpl(
    componentContext: ComponentContext,
    private val locationsData: Flow<PagingData<LocationData>>,
    private val charactersData: Flow<PagingData<CharacterData>>,
    private val navigateToSingleCharacter: (Int) -> Unit
) : ComponentContext by componentContext, MainComponent {

    private val navigation = StackNavigation<ChildConfig>()

    override fun onTabSelected(config: ChildConfig) {
        navigation.bringToFront(config)
    }

    override val mainTabs: EnumEntries<MainTabs> = MainTabs.entries

    override val mainChildStack = childStack(
        serializer = ChildConfig.serializer(),
        source = navigation,
        initialConfiguration = ChildConfig.Characters,
        handleBackButton = false,
        childFactory = ::createChild
    ).toStateFlow(lifecycle)

    private fun createChild(
        config: ChildConfig,
        componentContext: ComponentContext
    ): MainComponent.MainChild = when (config) {
        ChildConfig.Characters -> MainComponent.MainChild.Characters(
            component = CharactersComponentImpl(
                componentContext = componentContext,
                charactersData = charactersData,
                navigateToSingleCharacter = navigateToSingleCharacter
            )
        )

        ChildConfig.Locations -> MainComponent.MainChild.Locations(
            component = LocationsComponentImpl(
                componentContext = componentContext,
                locationsData = locationsData,
            )
        )
    }
}

enum class MainTabs {
    CHARACTERS,
    LOCATIONS;
}

@Serializable
sealed interface ChildConfig {

    @Serializable
    data object Characters : ChildConfig

    @Serializable
    data object Locations : ChildConfig

}