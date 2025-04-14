package com.example.bitspender.data.local

import com.example.bitspender.data.models.BtcRateEntity
import com.example.bitspender.data.models.TransactionEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val transactionDao: TransactionDao,
    private val btcRateDao: BtcRateDao
) {

    suspend fun addTransaction(transactionEntity: TransactionEntity) {
        transactionDao.insert(transactionEntity)
    }

    suspend fun getAllTransaction(): Flow<List<TransactionEntity>> {
        return transactionDao.getAll()
    }

    suspend fun insertNewBtcRate(btcRate: BtcRateEntity) {
        btcRateDao.insert(btcRate)
    }

    suspend fun getBtnRate(): BtcRateEntity? {
        return btcRateDao.getRate()
    }

    fun getBtcRateFlow(): Flow<BtcRateEntity?> {
        return btcRateDao.getRateFlow()
    }

}