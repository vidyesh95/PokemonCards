package com.astralai.pokemoncards

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.astralai.pokemoncards.ui.theme.PokemonCardsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    PokemonCardsTheme {
        // A surface container using the 'background' color from the theme
        Surface(color = MaterialTheme.colors.background) {
            val navController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = "pokemon_list_screen"
            ) {
                composable("pokemon_list_screen") {

                }
                composable(
                    "pokemon_detail_screen/{dominantColor}/{pokemonName}",
                    arguments = listOf(
                        navArgument("dominantColor") {
                            type = NavType.IntType
                        },
                        navArgument("pokemonName") {
                            type = NavType.StringType
                        }
                    )
                ) {
                    val dominantColor = remember {
                        val color = it.arguments?.getInt("dominantColor")
                        color?.let { Color(it) } ?: Color.White
                    }
                    val pokemonName = remember {
                        it.arguments?.getString("pokemonName")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "light mode")
@Preview(showBackground = true, name = "Night mode", uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
    MyApp()
}