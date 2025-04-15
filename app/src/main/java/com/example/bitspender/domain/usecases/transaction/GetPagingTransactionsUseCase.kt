package com.example.bitspender.domain.usecases.transaction

import androidx.paging.PagingSource
import com.example.bitspender.domain.models.TransactionModel
import com.example.bitspender.domain.repositories.TransactionRepository
import javax.inject.Inject

class GetPagingTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
) {

    operator fun invoke(): PagingSource<Int, TransactionModel> {
        return repository.getPagingTransaction()
    }
}