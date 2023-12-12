package com.mateusz.quoteapp2


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.mateusz.quoteapp2.viewmodel.MainViewModel
import com.mateusz.quoteapp2.screens.*
import com.mateusz.quoteapp2.ui.DrawerBody
import com.mateusz.quoteapp2.ui.DrawerHeader
import com.mateusz.quoteapp2.ui.NavShape
import com.mateusz.quoteapp2.ui.theme.QuoteApp2Theme
import com.mateusz.quoteapp2.util.MenuItem
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private val mainVm by viewModels<MainViewModel>()
    private lateinit var navController: NavHostController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            QuoteApp2Theme {
                navController = rememberNavController()

                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                Scaffold(
                    drawerShape = NavShape(0.dp,0.58f),
                    scaffoldState = scaffoldState,
                    topBar = {
                        com.mateusz.quoteapp2.ui.AppBar(
                            onNavigationIconClick = {
                                scope.launch {
                                    scaffoldState.drawerState.open()
                                }
                            }
                        )
                    },
                    drawerContent = {
                        DrawerHeader()
                        DrawerBody(
                            items = listOf(
                                MenuItem(
                                    id = "home",
                                    title = "Home",
                                    contentDescription = "Go to home screen",
                                    icon = Icons.Default.Home
                                ),
                                MenuItem(
                                    id = "author",
                                    title = "Author",
                                    contentDescription = "Go to author screen",
                                    icon = Icons.Default.Face
                                ),
                                MenuItem(
                                    id = "favourites",
                                    title = "Favourites",
                                    contentDescription = "Go to favourites screen",
                                    icon = Icons.Default.Favorite
                                ),
                                MenuItem(
                                    id = "settings",
                                    title = "Settings",
                                    contentDescription = "Go to settings screen",
                                    icon = Icons.Default.Settings
                                ),
                                MenuItem(
                                    id = "help",
                                    title = "About",
                                    contentDescription = "Get help",
                                    icon = Icons.Default.Info
                                ),
                            ),
                            onItemClick = {
                                when(it.id){
                                    "home" -> {
                                        navController.navigate("home_screen")
                                        scope.launch {
                                            scaffoldState.drawerState.close()
                                        }
                                    }
                                    "author" -> {
                                        navController.navigate("author_screen")
                                        scope.launch {
                                            scaffoldState.drawerState.close()
                                        }
                                    }
                                    "favourites" -> {
                                        navController.navigate("favourite_screen")
                                        scope.launch {
                                            scaffoldState.drawerState.close()
                                        }
                                    }

                                    "settings" -> {
                                        navController.navigate("settings_screen")
                                        scope.launch {
                                            scaffoldState.drawerState.close()
                                        }
                                    }
                                    "help" -> {
                                        navController.navigate("help_screen")
                                        scope.launch {
                                            scaffoldState.drawerState.close()
                                        }
                                    }

                                }
                            }
                        )
                    }
                ) {
                    SetupNav()
                }
            }
        }
    }
    @Composable
    fun SetupNav(){
        NavHost(navController = navController, startDestination = "home_screen"){
            composable("home_screen"){
                HomeScreen(mainVm, this@MainActivity )
            }
            composable("favourite_screen"){
                FavouriteScreen(mainVm)
            }
            composable("help_screen"){
                HelpScreen()
            }
            composable("settings_screen"){
                SettingsScreen()
            }
            composable("author_screen"){
                AuthorScreen(mainVm,this@MainActivity)
            }
        }
    }

}
