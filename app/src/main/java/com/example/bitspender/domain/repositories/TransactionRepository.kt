package com.example.bitspender.domain.repositories

import androidx.paging.PagingSource
import com.example.bitspender.domain.models.TransactionModel
import kotlinx.coroutines.flow.Flow
import com.example.bitspender.domain.utils.AppResult

interface TransactionRepository {

    suspend fun addTransaction(transaction: TransactionModel): AppResult<Unit>

    fun getTransactions(): Flow<List<TransactionModel>>

    fun getTransactionsList(): List<TransactionModel>

    fun getPagingTransaction(): PagingSource<Int, TransactionModel>
}