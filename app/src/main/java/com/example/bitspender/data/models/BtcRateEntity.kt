package com.example.bitspender.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "btc_rate")
data class BtcRateEntity(
    @PrimaryKey
    val id: Int = BTC_RATE_ID,
    val rate: Double,
    val timestamp: Long
) {

    companion object {
        const val BTC_RATE_ID = 0
    }
}
