package com.example.bitspender.domain.repositories

import com.example.bitspender.domain.models.TransactionModel
import kotlinx.coroutines.flow.Flow
import com.example.bitspender.domain.utils.AppResult

interface TransactionRepository {

    suspend fun addTransaction(transaction: TransactionModel): AppResult<Unit>

    fun getTransactions(): Flow<List<TransactionModel>>

    fun getBalance(): Flow<Double>
}