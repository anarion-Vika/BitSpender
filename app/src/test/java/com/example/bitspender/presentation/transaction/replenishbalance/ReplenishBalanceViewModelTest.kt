package com.example.bitspender.presentation.transaction.replenishbalance

import assertk.assertThat
import assertk.assertions.isFalse
import assertk.assertions.isTrue
import com.example.bitspender.data.repositoryimpl.TransactionRepositoryImplFake
import com.example.bitspender.domain.repositories.TransactionRepository
import com.example.bitspender.domain.usecases.transaction.AddTransactionUseCase
import com.example.bitspender.utils.MainCoroutineExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainCoroutineExtension::class)
class ReplenishBalanceViewModelTest {

    lateinit var viewModel: ReplenishBalanceViewModel
    lateinit var addTransactionUseCase: AddTransactionUseCase
    lateinit var transactionRepository: TransactionRepository

    @BeforeEach
    fun setUp() {
        transactionRepository = TransactionRepositoryImplFake()
        addTransactionUseCase = AddTransactionUseCase(transactionRepository)
        viewModel = ReplenishBalanceViewModel(addTransactionUseCase)
    }

    @Test
    fun `Test loading profile success`() = runTest {
        viewModel.addTransaction(10.0)

        advanceUntilIdle()

        assertThat(viewModel.uiStateFlow.value.isSaved).isTrue()
        assertThat(viewModel.uiStateFlow.value.error.isError).isFalse()
    }

}