package com.example.bitspender.data.local.transactions

import com.example.bitspender.data.models.TransactionEntity
import com.example.bitspender.domain.models.TransactionCategory
import com.example.bitspender.domain.models.TransactionModel
import com.example.bitspender.domain.models.TransactionType

fun transactionEntity(id: Int) = TransactionEntity(
    id = id,
    transactionAmount = 10.0,
    transactionType = TransactionType.INCOME.name,
    transactionCategory = TransactionCategory.OTHER.name,
    timestamp = System.currentTimeMillis()
)


fun transactionModel(id: Int) = TransactionModel(
    id = id,
    100.0,
    TransactionType.INCOME,
    TransactionCategory.OTHER,
    System.currentTimeMillis()
)