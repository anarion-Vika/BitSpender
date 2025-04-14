package com.example.bitspender.domain.usecases.transaction

import com.example.bitspender.domain.models.TransactionType
import com.example.bitspender.domain.repositories.TransactionRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetBalanceUseCase @Inject constructor(
    private val repository: TransactionRepository
) {

    operator fun invoke(): Flow<Double> {
        return repository.getTransactions().map { transactions ->
            transactions.sumOf {
                if (it.transactionType == TransactionType.INCOME) it.transactionAmount else -it.transactionAmount
            }
        }
    }
}