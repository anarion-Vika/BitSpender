package com.example.bitspender.data.repositoryimpl

import com.example.bitspender.data.local.transactions.TransactionsLocalDataSource
import com.example.bitspender.data.mappers.toDomainModel
import com.example.bitspender.data.mappers.toEntity
import com.example.bitspender.domain.models.TransactionModel
import com.example.bitspender.domain.repositories.TransactionRepository
import com.example.bitspender.domain.utils.AppError
import com.example.bitspender.domain.utils.AppResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val localDataSource: TransactionsLocalDataSource,
) : TransactionRepository {

    override suspend fun addTransaction(transaction: TransactionModel): AppResult<Unit> {
        return try {
            localDataSource.addTransaction(transaction.toEntity())
            AppResult.Success(Unit)
        } catch (e: Exception) {
            AppResult.Error(AppError.LocalError(e.message))
        }
    }

    override fun getTransactions(): Flow<List<TransactionModel>> {
        return localDataSource.getAllTransaction().map { listTransactions ->
            listTransactions.map { transaction -> transaction.toDomainModel() }
        }
    }

    override fun getTransactionsList(): List<TransactionModel> {
        return localDataSource.getAllTransactionList()
            .map { transaction -> transaction.toDomainModel() }
    }

}