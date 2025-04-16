package com.example.bitspender.data.local.transactions

import androidx.paging.PagingSource
import com.example.bitspender.data.database.TransactionDao
import com.example.bitspender.data.models.TransactionEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TransactionsLocalDataSourceImpl @Inject constructor(
    private val transactionDao: TransactionDao,
) : TransactionsLocalDataSource {

    override suspend fun addTransaction(transaction: TransactionEntity) {
        transactionDao.insert(transaction)
    }

    override fun getAllTransaction(): Flow<List<TransactionEntity>> {
        return transactionDao.getAllAsFlow()
    }

    override suspend fun getAllTransactionList(): List<TransactionEntity> {
        return transactionDao.getAllAsList()
    }

    override suspend fun getTransactionsPage(
        limit: Int,
        offset: Int
    ): List<TransactionEntity>{
        return transactionDao.getTransactionsPage(limit, offset)
    }

    override suspend fun getTotalCount(): Int {
        return transactionDao.getTotalCount()
    }

}