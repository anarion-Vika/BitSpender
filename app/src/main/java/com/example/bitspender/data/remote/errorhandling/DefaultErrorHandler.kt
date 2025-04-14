package com.example.bitspender.data.remote.errorhandling

import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

class DefaultErrorHandler @Inject constructor() : ErrorHandler {
    override fun handle(throwable: Throwable): ApiError = when (throwable) {
        is SocketTimeoutException,
        is ConnectException -> {
            ApiError.Network
        }
        is IOException -> ApiError.Network
        is HttpException -> handleHttpException(throwable)
        else -> ApiError.Unknown(throwable)
    }

    private fun handleHttpException(httpException: HttpException): ApiError {
        return when (val code = httpException.code()) {
            403 -> ApiError.Forbidden(code, httpException.message ?: "Http error")
            502 -> ApiError.BadGateway(code, httpException.message ?: "Http error")
            503 -> ApiError.ServiceUnavailable(code, httpException.message ?: "Http error")
            504 -> ApiError.GatewayTimeout(code, httpException.message ?: "Http error")
            else -> ApiError.Http(code, httpException.message ?: "Http error")
        }
    }
}