package com.example.bitspender.domain.models

data class TransactionModel(
    val id: Int,
    val transactionAmount: Double,
    val transactionType: TransactionType,
    val transactionCategory: TransactionCategory,
    val timestamp: Long,
)
