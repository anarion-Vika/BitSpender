package com.example.bitspender.data.remote.btcrate

import com.example.bitspender.data.models.BtcRateResponse
import com.example.bitspender.data.remote.BtcRateApi
import com.example.bitspender.data.remote.errorhandling.ApiResult
import javax.inject.Inject

class BtcRateRemoteDataSourceImpl @Inject constructor(
    private val btcRateApi: BtcRateApi
) : BtcRateRemoteDataSource {

    override suspend fun getBtcRate(): ApiResult<BtcRateResponse> {
        return btcRateApi.getBtcRate()
    }
}