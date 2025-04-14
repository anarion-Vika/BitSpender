package com.example.bitspender.data.local.transactions

import com.example.bitspender.data.models.TransactionEntity
import kotlinx.coroutines.flow.Flow

interface TransactionsLocalDataSource {

    fun getAllTransaction(): Flow<List<TransactionEntity>>

    fun getAllTransactionList(): List<TransactionEntity>

    suspend fun addTransaction(transaction: TransactionEntity)

}