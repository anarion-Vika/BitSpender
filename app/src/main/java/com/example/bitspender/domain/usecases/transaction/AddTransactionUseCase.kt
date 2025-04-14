package com.example.bitspender.domain.usecases.transaction

import com.example.bitspender.domain.repositories.TransactionRepository
import com.example.bitspender.domain.models.TransactionModel
import javax.inject.Inject
import com.example.bitspender.domain.utils.AppResult

class AddTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {

    suspend operator fun invoke(transactionModel: TransactionModel): AppResult<Unit> {
        return repository.addTransaction(transactionModel)
    }
}