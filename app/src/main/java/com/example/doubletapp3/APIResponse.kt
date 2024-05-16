package com.example.doubletapp3

sealed class APIResponse<T>(
    data: T? = null,
    exception: Exception? = null
) {
    data class Success<T>(val data: T) : APIResponse<T>(data, null)
    data class Error<T>(val exception: Exception) : APIResponse<T>(null, exception)
}