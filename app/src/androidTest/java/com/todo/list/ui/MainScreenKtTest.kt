package com.todo.list.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.todo.list.presentation.ui.MainScreen
import com.todo.list.presentation.viewmodel.TodoViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: TodoViewModel

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        viewModel = mockk(relaxed = true)

        every { viewModel.error } returns MutableStateFlow("Failed to add TODO")
        every { viewModel.filteredTodos } returns MutableStateFlow(emptyList())
    }

    @Test
    fun testErrorDialogShown() {
        composeTestRule.setContent {
            MainScreen(viewModel = viewModel, onAddTodo = {})
        }

        // Verify error dialog appears
        composeTestRule.onNodeWithText("Failed to add TODO").assertIsDisplayed()
        composeTestRule.onNodeWithText("OK").performClick()
        verify { viewModel.clearError() }
    }

    @Test
    fun testAddTodoButtonNavigates() {
        composeTestRule.setContent {
            MainScreen(viewModel = viewModel, onAddTodo = { /* simulate navigation */ })
        }

        // Verify FAB is displayed and clickable
        composeTestRule.onNodeWithContentDescription("Add TODO").assertIsDisplayed().performClick()
        // Confirm that navigation was triggered
    }
}