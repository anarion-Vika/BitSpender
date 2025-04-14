package com.example.bitspender.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bitspender.data.models.BtcRateEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface BtcRateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(rate: BtcRateEntity)

    @Query("SELECT * FROM btc_rate LIMIT 1")
    fun getRateFlow(): Flow<BtcRateEntity?>

    @Query("SELECT * FROM btc_rate LIMIT 1")
    suspend fun getRate(): BtcRateEntity?
}