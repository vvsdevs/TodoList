package com.todo.list.data.repository

import com.todo.list.data.local.dao.TodoDao
import com.todo.list.domain.models.TodoEntity
import kotlinx.coroutines.flow.Flow

class TodoRepositoryImpl(private val todoDao: TodoDao) : TodoRepository {
    override fun getTodos(): Flow<List<TodoEntity>> = todoDao.getAllTodos()

    override suspend fun addTodo(todo: TodoEntity) {
        todoDao.insertTodo(todo)
    }
}