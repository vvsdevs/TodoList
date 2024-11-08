package com.todo.list.di

import android.content.Context
import androidx.room.Room
import com.todo.list.data.local.dao.TodoDao
import com.todo.list.data.local.database.TodoDatabase
import com.todo.list.data.repository.TodoRepository
import com.todo.list.data.repository.TodoRepositoryImpl
import com.todo.list.domain.usecase.AddTodoUseCase
import com.todo.list.domain.usecase.GetTodosUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTodoDatabase(@ApplicationContext appContext: Context): TodoDatabase {
        return Room.databaseBuilder(
            appContext,
            TodoDatabase::class.java,
            "todo_database"
        ).build()
    }
    @Provides
    @Singleton
    fun provideDebounceDuration(): Long = 2000L

    @Provides
    fun provideTodoDao(db: TodoDatabase): TodoDao = db.todoDao()

    @Provides
    fun provideTodoRepository(todoDao: TodoDao): TodoRepository = TodoRepositoryImpl(todoDao)

    @Provides
    fun provideGetTodosUseCase(repository: TodoRepository) = GetTodosUseCase(repository)

    @Provides
    fun provideAddTodoUseCase(repository: TodoRepository) = AddTodoUseCase(repository)
}