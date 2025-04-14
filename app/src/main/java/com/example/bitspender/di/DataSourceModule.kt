package com.example.bitspender.di

import com.example.bitspender.data.local.btcrate.BtcRateLocalDataSource
import com.example.bitspender.data.local.btcrate.BtcRateLocalDataSourceImpl
import com.example.bitspender.data.local.transactions.TransactionsLocalDataSource
import com.example.bitspender.data.local.transactions.TransactionsLocalDataSourceImpl
import com.example.bitspender.data.remote.btcrate.BtcRateRemoteDataSource
import com.example.bitspender.data.remote.btcrate.BtcRateRemoteDataSourceImpl
import dagger.Binds
import dagger.Module

@Module
interface DataSourceModule {

    @Binds
    fun bindTransactionLocalDataSource(
        impl: TransactionsLocalDataSourceImpl
    ): TransactionsLocalDataSource

    @Binds
    fun bindBtcRateRemoteDataSource(
        impl: BtcRateRemoteDataSourceImpl
    ): BtcRateRemoteDataSource

    @Binds
    fun bindBtcRateLocalDataSource(
        impl: BtcRateLocalDataSourceImpl
    ): BtcRateLocalDataSource
}