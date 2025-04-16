package com.example.bitspender.di

import androidx.lifecycle.ViewModel
import com.example.bitspender.di.utils.ViewModelKey
import com.example.bitspender.presentation.addtransaction.AddTransactionViewModel
import com.example.bitspender.presentation.transaction.MainTransactionViewModel
import com.example.bitspender.presentation.transaction.replenishbalance.ReplenishBalanceViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainTransactionViewModel::class)
    abstract fun bindMainTransactionViewModel(viewModel: MainTransactionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddTransactionViewModel::class)
    abstract fun bindAddTransactionViewModel(viewModel: AddTransactionViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ReplenishBalanceViewModel::class)
    abstract fun bindReplenishBalanceViewModel(viewModel: ReplenishBalanceViewModel): ViewModel

}