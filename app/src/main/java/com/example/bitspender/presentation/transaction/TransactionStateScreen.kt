package com.example.bitspender.presentation.transaction

data class TransactionScreenState(
    var isLoading: Boolean = false,
    var isAddTransactionEnabled: Boolean = false,
    var btcRateState: String = "--",
    var currentBalance: Double = 0.0,
    var error: TransactionError = TransactionError()
)

data class TransactionError(
    var isError: Boolean = false,
    var textError: String = ""
)