package com.example.bitspender.presentation.base

import androidx.lifecycle.ViewModel
import com.example.bitspender.domain.utils.AppError

abstract class BaseViewModel : ViewModel() {

    fun AppError.toUserMessage(): String {
        return when (this) {
            is AppError.Network -> "Please check your internet connection."
            is AppError.Unknown -> throwable.message ?: "An unknown error occurred."
            is AppError.Forbidden -> "Access denied: $message"
            is AppError.BadGateway -> "Server error: $message"
            is AppError.ServiceUnavailable -> "Service is currently unavailable."
            is AppError.GatewayTimeout -> "Request timed out. Please try again later."
            is AppError.Http -> "HTTP error: $message"
            is AppError.LocalError -> message ?: "Local error occurred."
            is AppError.InsufficientFunds -> "Insufficient funds: Required $required BTC, available $current BTC."
        }
    }

}