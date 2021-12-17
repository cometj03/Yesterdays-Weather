package com.ctrlz.yesterdays_weather.network

import kotlinx.coroutines.*

suspend fun <T> safeApiCall(
    apiCall: suspend () -> T
): Result<T> {
    return withContext(Dispatchers.IO) {
        try {
            Result.success(apiCall())
        } catch (throwable: Throwable) {
            Result.failure(throwable)
        }
    }
}

suspend fun <T> asyncSafeApiCall(
    apiCall: suspend () -> T
): Result<T> {
    return coroutineScope {
        async {
            try {
                Result.success(apiCall())
            } catch (throwable: Throwable) {
                Result.failure(throwable)
            }
        }
    }.await()
}