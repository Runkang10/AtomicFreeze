package io.github.runkang10.atomicfreeze.services

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

object Coroutine {
    private val coroutine = CoroutineScope(Dispatchers.IO + SupervisorJob())


    fun launch(
        context: CoroutineContext = Dispatchers.IO,
        block: CoroutineScope.() -> Unit
    ) = coroutine.launch(context) { block() }

    fun cancel() = coroutine.cancel()
}