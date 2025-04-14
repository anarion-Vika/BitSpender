package com.example.bitspender.domain.usecases.transaction

import assertk.assertThat
import assertk.assertions.contains
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.example.bitspender.data.local.transactions.transactionModel
import com.example.bitspender.data.repositoryimpl.TransactionRepositoryImplFake
import com.example.bitspender.domain.models.TransactionCategory
import com.example.bitspender.domain.models.TransactionModel
import com.example.bitspender.domain.models.TransactionType
import com.example.bitspender.domain.utils.AppResult
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import com.example.bitspender.domain.utils.AppError

class AddTransactionUseCaseTest {

    private lateinit var repository: TransactionRepositoryImplFake
    private lateinit var useCase: AddTransactionUseCase

    @BeforeEach
    fun setUp() {
        repository = TransactionRepositoryImplFake()
        useCase = AddTransactionUseCase(repository)
    }

    @Test
    fun `Test add transaction`() = runBlocking {
        val amount = 50.0
        val type = TransactionType.INCOME
        val category = TransactionCategory.OTHER
        val timestamp = System.currentTimeMillis()

        val income = TransactionModel(
            id = 1,
            transactionAmount = amount,
            transactionType = type,
            transactionCategory = category,
            timestamp = timestamp
        )

        val result = useCase(income)

        assertThat(result).isInstanceOf(AppResult.Success::class.java)

        val savedModel = repository.transactions.first()

        assertThat(savedModel.transactionAmount).isEqualTo(amount)
        assertThat(savedModel.transactionType).isEqualTo(type)
        assertThat(savedModel.transactionCategory).isEqualTo(category)
        assertThat(savedModel.timestamp).isEqualTo(timestamp)
    }

    @Test
    fun `If balance is enough then add expense transaction`() = runBlocking {
        val income = transactionModel(1)
        val expense = transactionModel(2).copy(
            transactionType = TransactionType.EXPENSE
        )

        repository.addTransaction(income)

        val result = useCase(expense)

        assertThat(result).isInstanceOf(AppResult.Success::class.java)
        assertThat(repository.transactions).contains(expense)
        assertThat(repository.transactions.size).isEqualTo(2)
    }

    @Test
    fun `If expense is more than balance then return error InsufficientFunds with correct details`() =
        runBlocking {

            val income = transactionModel(1).copy(
                transactionType = TransactionType.INCOME,
                transactionAmount = 80.0
            )

            val expense = transactionModel(2).copy(
                transactionType = TransactionType.EXPENSE,
                transactionAmount = 100.0
            )

            useCase(income)

            val resultAfterTryToAddExpense = useCase(expense)

            val error = (resultAfterTryToAddExpense as AppResult.Error).error
            assertThat(error).isInstanceOf(AppError.InsufficientFunds::class.java)

            val insufficientFundsError = error as AppError.InsufficientFunds
            assertThat(insufficientFundsError.current).isEqualTo(80.0)
            assertThat(insufficientFundsError.required).isEqualTo(100.0)

        }
}
