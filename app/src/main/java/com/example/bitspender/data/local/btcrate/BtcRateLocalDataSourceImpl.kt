package com.example.bitspender.data.local.btcrate

import com.example.bitspender.data.database.BtcRateDao
import com.example.bitspender.data.models.BtcRateEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BtcRateLocalDataSourceImpl @Inject constructor(
    private val btcRateDao: BtcRateDao
) : BtcRateLocalDataSource {

    override suspend fun insertNewBtcRate(btcRate: BtcRateEntity) {
        btcRateDao.insert(btcRate)
    }

    override suspend fun getBtnRate(): BtcRateEntity? {
        return btcRateDao.getRate()
    }

    override fun getBtcRateFlow(): Flow<BtcRateEntity?> {
        return btcRateDao.getRateFlow()
    }

}