package com.ctrlz.yesterdays_weather.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

suspend fun <T> safeApiCall(
    dispatcher: CoroutineDispatcher,
    apiCall: suspend () -> T
): Result<T> {
    return withContext(dispatcher) {
        try {
            Result.success(apiCall())
        } catch (throwable: Throwable) {
            Result.failure(throwable)
        }
    }
}