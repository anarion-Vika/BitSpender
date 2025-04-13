package com.example.bitspender.data.remote

import com.example.bitspender.data.models.BtcRateResponse
import com.example.bitspender.data.remote.errorhandling.ApiResult
import retrofit2.http.GET

interface BtcRateApi {

    @GET(TICKER_END_POINT)
    suspend fun getBtcRate(): ApiResult<BtcRateResponse>

    companion object {
        private const val TICKER_END_POINT = "ticker"
    }
}
