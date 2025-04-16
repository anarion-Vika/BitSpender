package com.example.bitspender.presentation.transaction.replenishbalance

data class ReplenishBalanceStateScreen(
    var isLoading: Boolean = false,
    var isSaved: Boolean = false,
    var error: ReplenishBalanceError = ReplenishBalanceError()
)

data class ReplenishBalanceError(
    var isError: Boolean = false,
    var textError: String = ""
)