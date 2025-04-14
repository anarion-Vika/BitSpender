package com.example.bitspender.domain

import com.example.bitspender.domain.models.TransactionModel
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun addTransaction(transaction: TransactionModel): Result<Unit>

    fun getTransactions(): Flow<List<TransactionModel>>

    fun getBalance(): Flow<Float>
}