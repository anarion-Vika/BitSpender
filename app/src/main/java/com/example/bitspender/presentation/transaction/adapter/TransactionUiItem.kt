package com.example.bitspender.presentation.transaction.adapter

import com.example.bitspender.domain.models.TransactionModel


sealed class TransactionUiItem {
    data class TransactionItem(val transaction: TransactionModel) : TransactionUiItem()
    data class DateItem(val date: String) : TransactionUiItem()
}