package az.zero.paging

import kotlinx.coroutines.CoroutineScope

expect fun <T> runTest(block: suspend CoroutineScope.() -> T): T
