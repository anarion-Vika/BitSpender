package com.example.bitspender.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bitspender.data.models.BtcRateEntity
import com.example.bitspender.data.models.TransactionEntity

abstract class WalletDatabase {
    @Database(
        entities = [TransactionEntity::class, BtcRateEntity::class],
        version = 1
    )


    abstract class WalletDatabase : RoomDatabase() {
        abstract fun transactionDao(): TransactionDao
        abstract fun btcRateDao(): BtcRateDao
    }
}