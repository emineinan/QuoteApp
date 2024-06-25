package com.example.quoteapp

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data object Loading : Result<Nothing>()
    data class Error(val exception: Throwable) : Result<Nothing>()
}