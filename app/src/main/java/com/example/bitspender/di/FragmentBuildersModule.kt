package com.example.bitspender.di

import com.example.bitspender.di.scope.FragmentScope
import com.example.bitspender.presentation.addtransaction.AddTransactionFragment
import com.example.bitspender.presentation.transaction.MainTransactionFragment
import com.example.bitspender.presentation.transaction.replenishbalance.ReplenishBalanceDialogFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeMainTransactionFragment(): MainTransactionFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeAddTransactionFragment(): AddTransactionFragment

    @FragmentScope
    @ContributesAndroidInjector
    abstract fun contributeReplenishBalanceDialogFragment(): ReplenishBalanceDialogFragment
}