package com.example.bitspender.data.remote.errorhandling

sealed class ApiError {
    data object Network : ApiError()
    data class Forbidden(val code: Int, val message: String) : ApiError()
    data class BadGateway(val code: Int, val message: String) : ApiError()
    data class ServiceUnavailable(val code: Int, val message: String) : ApiError()
    data class GatewayTimeout(val code: Int, val message: String) : ApiError()
    data class Http(val code: Int, val message: String) : ApiError()
    data class Unknown(val throwable: Throwable) : ApiError()
}