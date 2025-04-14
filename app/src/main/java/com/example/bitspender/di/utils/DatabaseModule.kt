package com.example.bitspender.di.utils

import android.content.Context
import androidx.room.Room
import com.example.bitspender.data.local.BtcRateDao
import com.example.bitspender.data.local.TransactionDao
import com.example.bitspender.data.local.WalletDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideWalletDatabase(context: Context): WalletDatabase {
        return Room.databaseBuilder(
            context,
            WalletDatabase::class.java,
            DATABASE_NAME
        ).build()
    }

    @Provides
    fun provideTransactionDao(db: WalletDatabase): TransactionDao = db.transactionDao()

    @Provides
    fun provideBtcRateDao(db: WalletDatabase): BtcRateDao = db.btcRateDao()

    private const val DATABASE_NAME = "wallet_db"
}