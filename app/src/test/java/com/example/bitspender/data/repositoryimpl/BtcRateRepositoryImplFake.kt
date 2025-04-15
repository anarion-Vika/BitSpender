package com.example.bitspender.data.repositoryimpl

import com.example.bitspender.data.local.btcrate.BtcRateLocalDataSourceImplFake
import com.example.bitspender.data.mappers.toAppError
import com.example.bitspender.data.mappers.toDomainModel
import com.example.bitspender.data.mappers.toEntity
import com.example.bitspender.data.remote.btcrate.BtcRateRemoteDataSourceImplFake
import com.example.bitspender.data.remote.errorhandling.ApiResult
import com.example.bitspender.domain.models.BtcRateModel
import com.example.bitspender.domain.repositories.BtcRateRepository
import com.example.bitspender.domain.utils.AppResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class BtcRateRepositoryImplFake(
    private val localDataSourceImplFake: BtcRateLocalDataSourceImplFake,
    private val remoteDataSourceImplFake: BtcRateRemoteDataSourceImplFake
) : BtcRateRepository {

    override suspend fun updateBtcRate(): AppResult<Unit> {
        return when (val result = remoteDataSourceImplFake.getBtcRate()) {
            is ApiResult.Success -> {
                localDataSourceImplFake.insertNewBtcRate(result.data.toEntity())
                AppResult.Success(Unit)
            }

            is ApiResult.Failure -> {
                AppResult.Error(result.error.toAppError())
            }
        }
    }


    override fun getCurrentRate(): Flow<BtcRateModel?> {
        return flowOf(localDataSourceImplFake.btcRate?.toDomainModel())
    }
}