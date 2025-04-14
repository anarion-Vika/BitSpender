package com.example.bitspender.domain.repositories

import com.example.bitspender.domain.models.BtcRateModel
import com.example.bitspender.domain.utils.AppResult
import kotlinx.coroutines.flow.Flow

interface BtcRateRepository {

    suspend fun updateBtcRate(): AppResult<Unit>

    fun getCurrentRate(): Flow<BtcRateModel?>
}