package com.example.bitspender.data.remote.btcrate

import com.example.bitspender.data.models.BtcRateResponse
import com.example.bitspender.data.remote.errorhandling.ApiResult

interface BtcRateRemoteDataSource {

    suspend fun getBtcRate(): ApiResult<BtcRateResponse>
}