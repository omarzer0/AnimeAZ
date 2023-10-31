package az.zero.paging

import kotlinx.coroutines.CoroutineScope

actual fun <T> runTest(block: suspend CoroutineScope.() -> T): T =
    kotlinx.coroutines.runBlocking(block = block)