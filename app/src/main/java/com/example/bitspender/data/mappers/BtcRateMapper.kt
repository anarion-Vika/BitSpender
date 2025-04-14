package com.example.bitspender.data.mappers

import com.example.bitspender.data.models.BtcRateEntity
import com.example.bitspender.data.models.BtcRateResponse
import com.example.bitspender.domain.models.BtcRateModel

fun BtcRateEntity.toDomainModel(): BtcRateModel {
    return BtcRateModel(rate = rate, timestamp = timestamp)
}

fun BtcRateModel.toEntity(): BtcRateEntity {
    return BtcRateEntity(rate = rate, timestamp = timestamp)
}

fun BtcRateResponse.toEntity(): BtcRateEntity {
    return BtcRateEntity(
        rate = this.usd.last,
        timestamp = System.currentTimeMillis()
    )
}