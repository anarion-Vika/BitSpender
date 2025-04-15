package com.example.bitspender.data.local.btcrate

import com.example.bitspender.data.models.BtcRateEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


class BtcRateLocalDataSourceImplFake : BtcRateLocalDataSource {

    var btcRate: BtcRateEntity? = null

    override suspend fun insertNewBtcRate(btcRate: BtcRateEntity) {
        this.btcRate = btcRate
    }

    override suspend fun getBtnRate(): BtcRateEntity? {
        return btcRate
    }

    override fun getBtcRateFlow(): Flow<BtcRateEntity?> {
        return flowOf(btcRate)
    }

}