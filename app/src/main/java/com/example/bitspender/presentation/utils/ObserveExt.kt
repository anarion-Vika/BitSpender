package com.example.bitspender.presentation.utils

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

fun <T> Flow<T>.observeWithLifecycle(
    lifecycleOwner: LifecycleOwner,
    state: Lifecycle.State = Lifecycle.State.STARTED,
    delay: Long = 0L,
    observer: (T) -> Unit
) {
    lifecycleOwner.lifecycleScope.launch {
        if (delay != 0L) delay(delay)
        flowWithLifecycle(lifecycleOwner.lifecycle, state)
            .collect { value ->
                observer(value)
            }
    }
}