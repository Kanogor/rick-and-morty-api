package ru.kanogor.rickandmortypedia.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import ru.kanogor.rickandmortypedia.presentation.main.MainScreen
import ru.kanogor.rickandmortypedia.presentation.singleCharacter.SingleCharacterUi
import ru.kanogor.rickandmortypedia.presentation.theme.GreyBackground

@Composable
fun NavigationUi(
    component: NavigationComponent
) {

    val childStack by component.childStack.collectAsState()
    Children(
        modifier = Modifier
            .background(GreyBackground),
        stack = childStack
    ) { child ->
        when (val instance = child.instance) {
            is NavigationComponent.NavChild.Main -> MainScreen(instance.component)
            is NavigationComponent.NavChild.SingleCharacter -> SingleCharacterUi(component = instance.component)
        }
    }
}