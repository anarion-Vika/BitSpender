package com.example.bitspender.data

import com.example.bitspender.data.models.TransactionEntity
import com.example.bitspender.domain.models.TransactionCategory
import com.example.bitspender.domain.models.TransactionModel
import com.example.bitspender.domain.models.TransactionType

fun TransactionEntity.toDomainModel(): TransactionModel {
    val transactionModel = TransactionModel(
        id = id,
        timestamp = timestamp,
        transactionType = transactionType.toTransactionType(),
        transactionCategory = transactionCategory.toTransactionCategory(),
        transactionAmount = transactionAmount
    )
    return transactionModel
}

fun TransactionModel.toEntity(): TransactionEntity {
    val transactionModel = TransactionEntity(
        id = id,
        timestamp = timestamp,
        transactionType = transactionType.name,
        transactionCategory = transactionCategory.name,
        transactionAmount = transactionAmount
    )
    return transactionModel
}

private fun String.toTransactionType(): TransactionType =
    try {
        TransactionType.valueOf(this.uppercase())
    } catch (e: IllegalArgumentException) {
        throw IllegalArgumentException("Invalid transaction type: $this")
    }

private fun String.toTransactionCategory(): TransactionCategory =
    try {
        TransactionCategory.valueOf(this.uppercase())
    } catch (e: IllegalArgumentException) {
        throw IllegalArgumentException("Invalid transaction category: $this")
    }