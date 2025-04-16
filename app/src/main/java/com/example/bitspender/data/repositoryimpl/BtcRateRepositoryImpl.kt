package com.example.bitspender.data.repositoryimpl

import com.example.bitspender.data.local.btcrate.BtcRateLocalDataSource
import com.example.bitspender.data.mappers.toAppError
import com.example.bitspender.data.mappers.toDomainModel
import com.example.bitspender.data.mappers.toEntity
import com.example.bitspender.data.models.BtcRateEntity
import com.example.bitspender.data.remote.btcrate.BtcRateRemoteDataSource
import com.example.bitspender.data.remote.errorhandling.ApiResult
import com.example.bitspender.domain.models.BtcRateModel
import com.example.bitspender.domain.repositories.BtcRateRepository
import com.example.bitspender.domain.utils.AppResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class BtcRateRepositoryImpl @Inject constructor(
    private val remoteDataSource: BtcRateRemoteDataSource,
    private val localDataSource: BtcRateLocalDataSource
) : BtcRateRepository {

    override suspend fun updateBtcRate(): AppResult<Unit> {
        val btcRate = localDataSource.getBtnRate()
        if (!checkIfNeedToUpdate(btcRate)) {
            return AppResult.Success(Unit)
        }
        return when (val result = remoteDataSource.getBtcRate()) {
            is ApiResult.Success -> {
                localDataSource.insertNewBtcRate(result.data.toEntity())
                AppResult.Success(Unit)
            }

            is ApiResult.Failure -> {
                AppResult.Error(result.error.toAppError())
            }
        }
    }

    private fun checkIfNeedToUpdate(btcRate: BtcRateEntity?): Boolean {
        return btcRate?.let {
            val timeNow = System.currentTimeMillis()
            val diff = timeNow - it.timestamp
            val isSpentMoreOneHour = diff > ONE_HOUR
            isSpentMoreOneHour
        } ?: true
    }

    override fun getCurrentRate(): Flow<BtcRateModel?> {
        return localDataSource.getBtcRateFlow().map { rate -> rate?.toDomainModel() }
    }

    companion object {
        private const val ONE_HOUR = 60 * 60 * 1000
    }
}