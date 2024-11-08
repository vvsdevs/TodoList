package com.todo.list.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.todo.list.presentation.viewmodel.TodoViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AddTodoScreen(viewModel: TodoViewModel, onBack: () -> Unit) {
    var text by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Column {
        TextField(value = text,
            modifier = Modifier.fillMaxWidth(),
            onValueChange = { text = it }
        )
        Button(onClick = {
            scope.launch {
                viewModel.addTodo(text)
                delay(3000)
                onBack()
            }
        }) {
            Text("Add TODO")
        }
    }
}


