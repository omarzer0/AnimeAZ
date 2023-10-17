package az.zero.paging

import kotlinx.coroutines.CancellableContinuation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Delay
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Runnable
import kotlinx.coroutines.launch
import platform.Foundation.NSDate
import platform.Foundation.NSDefaultRunLoopMode
import platform.Foundation.NSRunLoop
import platform.Foundation.NSTimer
import platform.Foundation.addTimeInterval
import platform.Foundation.performBlock
import platform.Foundation.runUntilDate
import kotlin.coroutines.CoroutineContext
/*
 * Copyright 2020 IceRock MAG Inc. Use of this source code is governed by the Apache 2.0 license.
 */

@DelicateCoroutinesApi
actual fun <T> runTest(block: suspend CoroutineScope.() -> T): T {
    val expectation = Expectation<Result<T>>()

    GlobalScope.launch(MainRunLoopDispatcher) {
        expectation.fulfill(kotlin.runCatching { block.invoke(this) })
    }

    val result: Result<T> = expectation.wait() ?: throw RuntimeException("runBlocking failed")

    return result.getOrThrow()
}

private class Expectation<T> {
    private var waiting = true
    private var result: T? = null

    fun fulfill(result: T?) {
        waiting = false
        this.result = result
    }

    fun wait(): T? {
        while (waiting) {
            advanceRunLoop()
        }

        return result
    }

    fun advanceRunLoop() {
        val date = NSDate().addTimeInterval(1.0) as NSDate
        NSRunLoop.mainRunLoop.runUntilDate(date)
    }
}

@OptIn(InternalCoroutinesApi::class)
private object MainRunLoopDispatcher : CoroutineDispatcher(), Delay {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        NSRunLoop.mainRunLoop.performBlock {
            block.run()
        }
    }

    override fun scheduleResumeAfterDelay(
        timeMillis: Long,
        continuation: CancellableContinuation<Unit>
    ) {
        val timer = NSTimer(
            fireDate = NSDate().addTimeInterval((timeMillis / 1000).toDouble()) as NSDate,
            interval = 0.0,
            repeats = false,
            block = {
                val result = continuation.tryResume(Unit)
                if (result != null) continuation.completeResume(result)
            }
        )
        NSRunLoop.mainRunLoop.addTimer(timer, NSDefaultRunLoopMode)
    }
}

