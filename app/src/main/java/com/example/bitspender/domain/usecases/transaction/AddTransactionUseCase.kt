package com.example.bitspender.domain.usecases.transaction

import com.example.bitspender.domain.repositories.TransactionRepository
import com.example.bitspender.domain.models.TransactionModel
import com.example.bitspender.domain.models.TransactionType
import com.example.bitspender.domain.utils.AppError
import javax.inject.Inject
import com.example.bitspender.domain.utils.AppResult

class AddTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {

    suspend operator fun invoke(transaction: TransactionModel): AppResult<Unit> {
        val currentBalance = repository.getTransactionsList().sumOf {
            if (it.transactionType == TransactionType.INCOME) it.transactionAmount else -it.transactionAmount
        }

        val isExpense = transaction.transactionType == TransactionType.EXPENSE

        return if (isExpense && transaction.transactionAmount > currentBalance) {

            AppResult.Error(
                AppError.InsufficientFunds(
                    required = transaction.transactionAmount,
                    current = currentBalance
                )
            )
        } else {
            repository.addTransaction(transaction)
        }
    }
}