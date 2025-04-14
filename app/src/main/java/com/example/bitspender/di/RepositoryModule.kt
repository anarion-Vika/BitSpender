package com.example.bitspender.di

import com.example.bitspender.data.repositoryimpl.BtcRateRepositoryImpl
import com.example.bitspender.data.repositoryimpl.TransactionRepositoryImpl
import com.example.bitspender.domain.repositories.BtcRateRepository
import com.example.bitspender.domain.repositories.TransactionRepository
import dagger.Binds
import dagger.Module

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
}