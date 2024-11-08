package com.todo.list.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todo.list.domain.models.TodoEntity
import com.todo.list.domain.usecase.AddTodoUseCase
import com.todo.list.domain.usecase.GetTodosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val getTodosUseCase: GetTodosUseCase,
    private val addTodoUseCase: AddTodoUseCase
) : ViewModel() {

    private val _todos = getTodosUseCase()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _searchText = MutableStateFlow("")

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    @OptIn(FlowPreview::class)
    val filteredTodos: StateFlow<List<TodoEntity>> = _searchText
        .debounce(2000)
        .onStart { _isLoading.value = true }
        .combine(_todos) { searchText, todos ->
            _isLoading.update { false }
            if (searchText.isBlank()) todos
            else todos.filter { it.text.contains(searchText, ignoreCase = true) }
        }
        .onStart { _isLoading.value = false }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun onSearchTextChanged(query: String) {
        _searchText.value = query
        _isLoading.update { true }
    }

    fun addTodo(text: String) {
        viewModelScope.launch {
            try {
                if (text == "Error") {
                    throw Exception("Failed to add TODO")
                } else {
                    addTodoUseCase(text)
                }
                _error.value = null
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun addTodoWithErrorHandling(text: String) {
        viewModelScope.launch {
            try {
                if (text.equals("Error", ignoreCase = true)) {
                    throw Exception("Simulated error")  // Throws exception if "Error" is entered
                } else {
                    addTodoUseCase(text)
                    _error.value = null  // Clear any previous error after success
                }
            } catch (e: Exception) {
                _error.value = "Failed to add TODO"
            }
        }
    }

    fun setError(message: String) {
        _error.value = message
    }

    fun clearError() {
        _error.value = null
    }
}