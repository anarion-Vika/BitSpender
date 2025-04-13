package com.example.bitspender.data.models

import com.squareup.moshi.Json

data class BtcRateResponse(
    @Json(name = "USD")
    val usd: CurrencyRateDto
)