package az.zero.paging

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule

actual open class BaseTest {
    @get:Rule
    val rule = InstantTaskExecutorRule()
}
