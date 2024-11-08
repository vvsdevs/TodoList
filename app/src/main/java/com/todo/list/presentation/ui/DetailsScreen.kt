package com.todo.list.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.todo.list.presentation.viewmodel.TodoViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun DetailsScreen(viewModel: TodoViewModel, onBack: () -> Unit) {
    var text by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(modifier = Modifier.padding(16.dp)) {
        TextField(
            value = text,
            onValueChange = { text = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 30.dp, bottom = 16.dp),
            placeholder = { Text("Enter TODO") }
        )

        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(bottom = 16.dp))
        }

        Button(
            onClick = {
                scope.launch {
                    isLoading = true
                    try {
                        viewModel.addTodoWithErrorHandling(text)
                        delay(3000)
                        onBack()
                    } catch (e: Exception) {
                        viewModel.setError("Failed to add TODO")
                        onBack()
                    } finally {
                        isLoading = false
                    }
                }
            },
            enabled = !isLoading
        ) {
            Text("Add TODO")
        }
    }
}