package com.example.bitspender.presentation.addtransaction

data class AddTransactionStateScreen(
    var isLoading: Boolean = false,
    var isSaved: Boolean = false,
    var error: AddTransactionError = AddTransactionError()
)

data class AddTransactionError(
    var isError: Boolean = false,
    var textError: String = ""
)