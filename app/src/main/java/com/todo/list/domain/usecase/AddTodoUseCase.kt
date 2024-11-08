package com.todo.list.domain.usecase

import com.todo.list.data.repository.TodoRepository
import com.todo.list.domain.models.TodoEntity
import javax.inject.Inject

class AddTodoUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(text: String) {
        if (text == "Error") throw IllegalArgumentException("Failed to add TODO")
        repository.addTodo(TodoEntity(text = text))
    }
}