package com.example.bitspender.domain.usecases.transaction

import com.example.bitspender.domain.repositories.TransactionRepository
import com.example.bitspender.domain.models.TransactionModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
) {

    operator fun invoke(): Flow<List<TransactionModel>> {
        return repository.getTransactions()
    }
}