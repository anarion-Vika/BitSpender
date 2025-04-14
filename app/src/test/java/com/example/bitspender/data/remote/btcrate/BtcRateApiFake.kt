package com.example.bitspender.data.remote.btcrate

import com.example.bitspender.data.models.BtcRateResponse
import com.example.bitspender.data.models.CurrencyRateDto
import com.example.bitspender.data.remote.BtcRateApi
import kotlin.random.Random

class BtcRateApiFake : BtcRateApi {

    override suspend fun getBtcRate(): BtcRateResponse {
        return BtcRateResponse(CurrencyRateDto(Random.nextDouble()))
    }
}