package com.example.bitspender.data.remote.errorhandling

interface ErrorHandler {
    fun handle(throwable: Throwable): ApiError
}