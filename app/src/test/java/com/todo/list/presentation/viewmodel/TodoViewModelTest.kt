import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.todo.list.domain.models.TodoEntity
import com.todo.list.domain.usecase.AddTodoUseCase
import com.todo.list.domain.usecase.GetTodosUseCase
import com.todo.list.presentation.viewmodel.TodoViewModel
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class TodoViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var addTodoUseCase: AddTodoUseCase
    private lateinit var getTodosUseCase: GetTodosUseCase
    private lateinit var viewModel: TodoViewModel

    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        addTodoUseCase = mockk(relaxed = true)
        getTodosUseCase = mockk()
        every { getTodosUseCase() } returns flowOf(emptyList())
        viewModel = TodoViewModel(getTodosUseCase, addTodoUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        testDispatcher.cancel()
    }

    @Test
    fun `test addTodo with valid input`() = runTest {
        viewModel.addTodoWithErrorHandling("New TODO")

        coVerify { addTodoUseCase.invoke("New TODO") }
        assertEquals(null, viewModel.error.value)
    }

    @Test
    fun `test addTodo with error input`() = runTest {
        viewModel.addTodoWithErrorHandling("Error")
        assertNotNull(viewModel.error.value)
        assertEquals("Failed to add TODO", viewModel.error.value)
    }

}