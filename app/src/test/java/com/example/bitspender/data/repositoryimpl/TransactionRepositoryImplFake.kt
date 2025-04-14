package com.example.bitspender.data.repositoryimpl

import com.example.bitspender.domain.models.TransactionModel
import com.example.bitspender.domain.repositories.TransactionRepository
import com.example.bitspender.domain.utils.AppResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TransactionRepositoryImplFake : TransactionRepository {

    val transactions = mutableListOf<TransactionModel>()

    override suspend fun addTransaction(transaction: TransactionModel): AppResult<Unit> {
        transactions.add(transaction)
        return AppResult.Success(Unit)
    }

    override fun getTransactions(): Flow<List<TransactionModel>> {
        return flowOf(transactions)
    }

    override fun getTransactionsList(): List<TransactionModel> {
        return transactions
    }

}
