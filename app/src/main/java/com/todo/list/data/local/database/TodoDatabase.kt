package com.todo.list.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.todo.list.data.local.dao.TodoDao
import com.todo.list.domain.models.TodoEntity

@Database(entities = [TodoEntity::class], version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}