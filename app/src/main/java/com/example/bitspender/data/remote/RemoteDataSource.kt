package com.example.bitspender.data.remote

import com.example.bitspender.data.models.BtcRateResponse
import com.example.bitspender.data.remote.errorhandling.ApiResult
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val btcRateApi: BtcRateApi
) {

    suspend fun getBtcRate(): ApiResult<BtcRateResponse> {
        return btcRateApi.getBtcRate()
    }
}