package com.example.bitspender.data.mappers

import com.example.bitspender.data.remote.errorhandling.ApiError
import com.example.bitspender.domain.utils.AppError

fun ApiError.toAppError(): AppError = when (this) {
    is ApiError.Network -> AppError.Network
    is ApiError.Unknown -> AppError.Unknown(throwable)
    is ApiError.BadGateway -> AppError.BadGateway(code, message)
    is ApiError.Forbidden -> AppError.Forbidden(code, message)
    is ApiError.GatewayTimeout -> AppError.GatewayTimeout(code, message)
    is ApiError.ServiceUnavailable -> AppError.ServiceUnavailable(code, message)
    is ApiError.Http -> AppError.Http(code, message)
}