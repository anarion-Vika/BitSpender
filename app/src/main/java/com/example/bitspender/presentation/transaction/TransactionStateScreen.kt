package com.example.bitspender.presentation.transaction

data class TransactionScreenState(
    var isLoading: Boolean = false,
    var isAddTransactionEnabled: Boolean = false,
    var btcRateState: String = "--"
)

sealed interface TransactionScreenEvent {


}