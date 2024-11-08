package com.todo.list.ui

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.todo.list.presentation.ui.DetailsScreen
import com.todo.list.presentation.viewmodel.TodoViewModel
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailsScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: TodoViewModel

    @Before
    fun setup() {
        viewModel = mockk(relaxed = true)
    }

    @ExperimentalCoroutinesApi
    @Test
    fun testAddTodoWithLoadingIndicator() = runBlockingTest {
        composeTestRule.setContent {
            DetailsScreen(viewModel = viewModel, onBack = {})
        }

        // Enter a new TODO text
        composeTestRule.onNodeWithText("Enter TODO").performTextInput("New TODO")
        // Click on Add button
        composeTestRule.onNodeWithText("Add TODO").performClick()

        // Check if loading indicator is shown
        composeTestRule.onNodeWithContentDescription("Loading").assertIsDisplayed()

        // Verify addTodoWithErrorHandling is called with "New TODO"
        verify { viewModel.addTodoWithErrorHandling("New TODO") }
    }
}