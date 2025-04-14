package com.example.bitspender.data.local.btcrate

import com.example.bitspender.data.models.BtcRateEntity
import kotlinx.coroutines.flow.Flow

interface BtcRateLocalDataSource {

    suspend fun insertNewBtcRate(btcRate: BtcRateEntity)

    suspend fun getBtnRate(): BtcRateEntity?

    fun getBtcRateFlow(): Flow<BtcRateEntity?>
}