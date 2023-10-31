package az.zero.paging

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise

//actual fun <T> runTest(block: suspend CoroutineScope.() -> T): T =
actual fun <T>runTest(block: suspend CoroutineScope.() -> T): T =
    GlobalScope.promise { block() }.asDynamic() as T
