package com.example.bitspender.di

import com.example.bitspender.data.local.btcrate.BtcRateLocalDataSource
import com.example.bitspender.data.local.btcrate.BtcRateLocalDataSourceImpl
import com.example.bitspender.data.local.transactions.TransactionsLocalDataSource
import com.example.bitspender.data.local.transactions.TransactionsLocalDataSourceImpl
import com.example.bitspender.data.remote.btcrate.BtcRateRemoteDataSource
import com.example.bitspender.data.remote.btcrate.BtcRateRemoteDataSourceImpl
import com.example.bitspender.data.remote.errorhandling.DefaultErrorHandler
import com.example.bitspender.data.remote.errorhandling.ErrorHandler
import com.example.bitspender.data.repositoryimpl.BtcRateRepositoryImpl
import com.example.bitspender.data.repositoryimpl.TransactionRepositoryImpl
import com.example.bitspender.domain.repositories.BtcRateRepository
import com.example.bitspender.domain.repositories.TransactionRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
interface RepositoryModule {

    @Binds
    fun bindTransactionRepository(
        impl: TransactionRepositoryImpl
    ): TransactionRepository

    @Binds
    fun bindBtcRateRepository(
        impl: BtcRateRepositoryImpl
    ): BtcRateRepository

    @Binds
    fun bindBtcRemoteDataSource(
        impl: BtcRateRemoteDataSourceImpl
    ): BtcRateRemoteDataSource

    @Binds
    fun bindBtcLocalDataSource(
        impl: BtcRateLocalDataSourceImpl
    ): BtcRateLocalDataSource


    @Binds
    fun bindTransactionLocalDataSource(
        impl: TransactionsLocalDataSourceImpl
    ): TransactionsLocalDataSource

//    @Provides
//    @Singleton
//    fun provideErrorHandler(): ErrorHandler = DefaultErrorHandler()
}