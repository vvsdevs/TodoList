package com.todo.list.data.repository

import com.todo.list.domain.models.TodoEntity
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getTodos(): Flow<List<TodoEntity>>
    suspend fun addTodo(todo: TodoEntity)
}