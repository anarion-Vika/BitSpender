package com.example.bitspender.domain.usecases.transaction

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import com.example.bitspender.data.local.transactions.transactionModel
import com.example.bitspender.data.repositoryimpl.TransactionRepositoryImplFake
import com.example.bitspender.domain.models.TransactionType
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetBalanceUseCaseTest {

    private lateinit var repository: TransactionRepositoryImplFake
    private lateinit var useCase: GetBalanceUseCase

    @BeforeEach
    fun setUp() {
        repository = TransactionRepositoryImplFake()
        useCase = GetBalanceUseCase(repository)
    }

    @Test
    fun `Testing if transaction was not add then balance is 0,0`() = runTest {
        useCase().test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(0.0)
            awaitComplete()
        }
    }

    @Test
    fun `Testing returns correct sum of balance after added transaction`() = runTest {
        with(repository) {
            addTransaction(
                transactionModel(1).copy(
                    transactionAmount = 100.0,
                    transactionType = TransactionType.INCOME
                )
            )
            addTransaction(
                transactionModel(2).copy(
                    transactionAmount = 50.0,
                    transactionType = TransactionType.EXPENSE
                )
            )
            addTransaction(
                transactionModel(3).copy(
                    transactionAmount = 30.0,
                    transactionType = TransactionType.INCOME
                )
            )
        }

        useCase().test {
            val emission = awaitItem()
            assertThat(emission).isEqualTo(80.0) // 100 - 50 + 30
            awaitComplete()
        }
    }

}