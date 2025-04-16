package com.example.bitspender.data.local.transactions

import androidx.paging.PagingSource
import com.example.bitspender.data.models.TransactionEntity
import kotlinx.coroutines.flow.Flow

interface TransactionsLocalDataSource {

    fun getAllTransaction(): Flow<List<TransactionEntity>>

    suspend fun getAllTransactionList(): List<TransactionEntity>

    suspend fun addTransaction(transaction: TransactionEntity)

    suspend fun getTotalCount(): Int

    suspend fun getTransactionsPage(limit: Int, offset: Int): List<TransactionEntity>
}