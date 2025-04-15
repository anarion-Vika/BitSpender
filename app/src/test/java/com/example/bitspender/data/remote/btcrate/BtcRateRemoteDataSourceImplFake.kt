package com.example.bitspender.data.remote.btcrate

import com.example.bitspender.data.models.BtcRateResponse
import com.example.bitspender.data.remote.errorhandling.ApiError
import com.example.bitspender.data.remote.errorhandling.ApiResult

class BtcRateRemoteDataSourceImplFake : BtcRateRemoteDataSource {

    var apiResult: ApiResult<BtcRateResponse>? = null

    override suspend fun getBtcRate(): ApiResult<BtcRateResponse> {
        return apiResult ?: ApiResult.Failure(ApiError.Unknown(Exception("Default fake error")))

    }

}