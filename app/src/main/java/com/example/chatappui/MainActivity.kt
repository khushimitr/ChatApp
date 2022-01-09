package com.example.chatappui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.chatappui.presentation.chat.ChatScreen
import com.example.chatappui.presentation.username.UsernameScreen
import com.example.chatappui.ui.theme.ChatAppUITheme
import com.example.chatappui.ui.theme.background
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatAppUITheme {
                Surface(
                    color = background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "username_screen") {
                        composable("username_screen") {
                            UsernameScreen(onNavigate = navController::navigate)
                        }
                        composable(
                            route = "chat_screen/{username}",
                            arguments = listOf(
                                navArgument(name = "username") {
                                    type = NavType.StringType
                                    nullable = true
                                }
                            )
                        ) {
                            val username = it.arguments?.getString("username")
                            ChatScreen(username = username)
                        }
                    }
                }
            }
        }
    }
}

