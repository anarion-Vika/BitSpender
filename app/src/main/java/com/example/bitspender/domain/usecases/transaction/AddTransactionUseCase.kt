package com.example.bitspender.domain.usecases.transaction

import com.example.bitspender.domain.TransactionRepository
import com.example.bitspender.domain.models.TransactionModel
import javax.inject.Inject

class AddTransactionUseCase @Inject constructor(
    private val repository: TransactionRepository
) {

    suspend operator fun invoke(transactionModel: TransactionModel): Result<Unit> {
        return repository.addTransaction(transactionModel)
    }
}