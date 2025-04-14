package com.example.bitspender.domain.utils

sealed class AppError {
    data object Network : AppError()
    data class Unknown(val throwable: Throwable) : AppError()
    data class Forbidden(val code: Int, val message: String) : AppError()
    data class BadGateway(val code: Int, val message: String) : AppError()
    data class ServiceUnavailable(val code: Int, val message: String) : AppError()
    data class GatewayTimeout(val code: Int, val message: String) : AppError()
    data class Http(val code: Int, val message: String) : AppError()
    data class LocalError(val message: String?) : AppError()
    data class InsufficientFunds(val required: Double, val current: Double) : AppError()
}