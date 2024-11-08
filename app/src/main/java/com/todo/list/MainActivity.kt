package com.todo.list

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.todo.list.presentation.ui.DetailsScreen
import com.todo.list.presentation.ui.MainScreen
import com.todo.list.presentation.viewmodel.TodoViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val todoViewModel: TodoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            NavHost(navController, startDestination = "main") {
                composable("main") {
                    MainScreen(
                        viewModel = todoViewModel,
                        onAddTodo = { navController.navigate("details") }
                    )
                }
                composable("details") {
                    DetailsScreen(
                        viewModel = todoViewModel,
                        onBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}

