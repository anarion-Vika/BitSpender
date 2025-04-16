package com.example.bitspender.domain.repositories

import androidx.paging.PagingSource
import com.example.bitspender.domain.models.TransactionModel
import com.example.bitspender.domain.utils.AppResult
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun addTransaction(transaction: TransactionModel): AppResult<Unit>

    fun getTransactions(): Flow<List<TransactionModel>>

    suspend fun getTransactionsList(): List<TransactionModel>

    fun getPagingTransaction(pageSize: Int): PagingSource<Int, TransactionModel>
}