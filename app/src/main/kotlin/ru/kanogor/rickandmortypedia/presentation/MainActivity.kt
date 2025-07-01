package ru.kanogor.rickandmortypedia.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kanogor.rickandmortypedia.presentation.main.MainViewModel
import ru.kanogor.rickandmortypedia.presentation.navigation.NavigationComponentImpl
import ru.kanogor.rickandmortypedia.presentation.navigation.NavigationUi

class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navigationComponent = NavigationComponentImpl(
                componentContext = defaultComponentContext(),
                getSingleCharacterData = { id ->
                    viewModel.getSingleCharacterFlow(id)
                },
                locationsData = viewModel.pagedLocations(),
                charactersData = viewModel.pagedCharacters()
            )

            NavigationUi(
                component = navigationComponent
            )
        }
    }
}

