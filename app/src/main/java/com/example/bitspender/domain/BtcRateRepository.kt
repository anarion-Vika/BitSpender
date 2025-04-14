package com.example.bitspender.domain

import com.example.bitspender.domain.models.BtcRateModel
import kotlinx.coroutines.flow.Flow

interface BtcRateRepository {

    suspend fun updateBtcRate(): Result<Unit>

    fun getCurrentRate(): Flow<BtcRateModel>
}