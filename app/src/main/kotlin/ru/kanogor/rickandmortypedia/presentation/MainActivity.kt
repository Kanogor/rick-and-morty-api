package ru.kanogor.rickandmortypedia.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.kanogor.rickandmortypedia.domain.entity.CharacterData

class MainActivity : ComponentActivity() {

    private val viewModel by viewModel<MainViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            val charItem = remember {
                mutableStateOf<CharacterData?>(null)
            }
            NavHost(
                navController = navController,
                startDestination = Screen.MainScreen.route
            ) {
                composable(Screen.MainScreen.route) {
                    MainScreen(viewModel, charItem = charItem, onClick = {
                        navController.navigate(Screen.CharacterItem.route)
                    })
                }
                composable(Screen.CharacterItem.route) {
                    CharacterSingleItem(character = charItem.value!!, onClick = {
                        navController.navigate(Screen.MainScreen.route)
                    }, context = this@MainActivity)
                }
            }

        }
    }
}

