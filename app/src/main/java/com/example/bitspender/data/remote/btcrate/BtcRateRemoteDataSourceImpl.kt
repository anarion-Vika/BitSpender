package com.example.bitspender.data.remote.btcrate

import com.example.bitspender.data.models.BtcRateResponse
import com.example.bitspender.data.remote.BtcRateApi
import com.example.bitspender.data.remote.errorhandling.ApiResult
import com.example.bitspender.data.remote.errorhandling.ErrorHandler
import javax.inject.Inject

class BtcRateRemoteDataSourceImpl @Inject constructor(
    private val btcRateApi: BtcRateApi,
    private val errorHandler: ErrorHandler
) : BtcRateRemoteDataSource {

    override suspend fun getBtcRate(): ApiResult<BtcRateResponse> {
        return try {
            val result = btcRateApi.getBtcRate()
            ApiResult.Success(result)
        } catch (e: Exception) {
            ApiResult.Failure(errorHandler.handle(e))
        }
    }
}