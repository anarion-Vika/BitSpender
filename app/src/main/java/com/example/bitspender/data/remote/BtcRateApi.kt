package com.example.bitspender.data.remote

import com.example.bitspender.data.models.BtcRateResponse
import retrofit2.http.GET

interface BtcRateApi {

    @GET(TICKER_END_POINT)
    suspend fun getBtcRate(): BtcRateResponse

    companion object {
        private const val TICKER_END_POINT = "ticker"
    }
}
