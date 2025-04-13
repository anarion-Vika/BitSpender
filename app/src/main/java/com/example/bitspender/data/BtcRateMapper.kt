package com.example.bitspender.data

import com.example.bitspender.data.models.BtcRateEntity
import com.example.bitspender.domain.models.BtcRateModel

fun BtcRateEntity.toDomainModel() = with(this) {
    BtcRateModel(rate = rate, timestamp = timestamp)
}

fun BtcRateModel.toEntity() = with(this) {
    BtcRateEntity(rate = rate, timestamp = timestamp)
}