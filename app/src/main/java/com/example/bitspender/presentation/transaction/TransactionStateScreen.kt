package com.example.bitspender.presentation.transaction

data class TransactionScreenState(
    var isAddTransactionEnabled: Boolean = false,
    var btcRateState: String = "--"
)

sealed interface TransactionScreenEvent {


}