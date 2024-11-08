package com.todo.list.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.todo.list.presentation.viewmodel.TodoViewModel

@Composable
fun MainScreen(viewModel: TodoViewModel, onAddTodo: () -> Unit) {
    val todos by viewModel.filteredTodos.collectAsState()
    val error by viewModel.error.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    var searchText by remember { mutableStateOf("") }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddTodo) {
                Icon(Icons.Default.Add, contentDescription = "Add TODO")
            }
        }
    ) { paddingValues ->
        Column(modifier = Modifier.padding(paddingValues)) {
            TextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                    viewModel.onSearchTextChanged(it)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp, 5.dp, 20.dp, 20.dp),
                placeholder = { Text("Search TODOs") }
            )
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                )
            }

            if (error != null) {
                ErrorDialog(
                    errorMessage = "Failed to add TODO",
                    onDismiss = { viewModel.clearError() }
                )
            }
            if (todos.isEmpty()) {
                Text(
                    "Press the + button to add a TODO item",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp)
                )
            } else {
                LazyColumn {
                    items(todos) { todo ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(10.dp)
                        ) {
                            Text(todo.text, modifier = Modifier.padding(10.dp))
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun ErrorDialog(errorMessage: String, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .background(
                    Color.White,
                    shape = RectangleShape
                )
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = errorMessage,
                color = Color.Red,
                modifier = Modifier.padding(bottom = 16.dp)
            )
            Button(onClick = onDismiss) {
                Text("OK")
            }
        }
    }
}