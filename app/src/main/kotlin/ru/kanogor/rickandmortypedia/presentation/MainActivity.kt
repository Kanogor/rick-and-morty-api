package ru.kanogor.rickandmortypedia.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.arkivanov.decompose.defaultComponentContext
import ru.kanogor.rickandmortypedia.presentation.navigation.NavigationComponentImpl
import ru.kanogor.rickandmortypedia.presentation.navigation.NavigationUi

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navigationComponent = NavigationComponentImpl(
                componentContext = defaultComponentContext(),
            )

            NavigationUi(
                component = navigationComponent
            )
        }
    }
}

