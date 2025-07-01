package ru.kanogor.rickandmortypedia.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.arkivanov.decompose.extensions.compose.stack.Children
import ru.kanogor.rickandmortypedia.presentation.main.MainScreen
import ru.kanogor.rickandmortypedia.presentation.singleCharacter.SingleCharacterUi

@Composable
fun NavigationUi(
    component: NavigationComponent
) {

    val childStack by component.childStack.collectAsState()

    Children(childStack) { child ->
        when (val instance = child.instance) {
            is NavigationComponent.NavChild.Main -> MainScreen(instance.component)
            is NavigationComponent.NavChild.SingleCharacter -> SingleCharacterUi(component = instance.component)
        }

    }

}