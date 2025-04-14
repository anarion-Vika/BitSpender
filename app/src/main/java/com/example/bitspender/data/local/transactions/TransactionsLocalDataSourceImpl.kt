package com.example.bitspender.data.local.transactions

import com.example.bitspender.data.database.TransactionDao
import com.example.bitspender.data.mappers.toTransactionType
import com.example.bitspender.data.models.TransactionEntity
import com.example.bitspender.domain.models.TransactionType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransactionsLocalDataSourceImpl @Inject constructor(
    private val transactionDao: TransactionDao,
) : TransactionsLocalDataSource {

    override suspend fun addTransaction(transaction: TransactionEntity) {
        transactionDao.insert(transaction)
    }

    override fun getAllTransaction(): Flow<List<TransactionEntity>> {
        return transactionDao.getAll()
    }

    override fun getBalance(): Flow<Double> {
        return transactionDao.getAll().map { transactions ->
            transactions.sumOf {
                if (it.transactionType.toTransactionType() == TransactionType.INCOME) it.transactionAmount else -it.transactionAmount
            }
        }
    }
}