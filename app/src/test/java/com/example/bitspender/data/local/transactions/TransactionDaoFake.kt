package com.example.bitspender.data.local.transactions

import com.example.bitspender.data.database.TransactionDao
import com.example.bitspender.data.models.TransactionEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TransactionDaoFake : TransactionDao {

    val transactions = mutableListOf<TransactionEntity>()

    override suspend fun insert(transaction: TransactionEntity) {
        transactions.add(transaction)
    }

    override fun getAllAsFlow(): Flow<List<TransactionEntity>> {
        return flowOf(transactions)
    }

    override suspend fun getAllAsList(): List<TransactionEntity> {
        return transactions
    }

    override suspend fun getTransactionsPage(limit: Int, offset: Int): List<TransactionEntity> {
        return transactions
    }

    override suspend fun getTotalCount(): Int {
        return transactions.size
    }

}