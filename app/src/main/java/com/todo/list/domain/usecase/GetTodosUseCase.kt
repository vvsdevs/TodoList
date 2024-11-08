package com.todo.list.domain.usecase

import com.todo.list.data.repository.TodoRepository
import com.todo.list.domain.models.TodoEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTodosUseCase @Inject constructor(
    private val repository: TodoRepository
) {
    operator fun invoke(): Flow<List<TodoEntity>> = repository.getTodos()
}