package com.example.bitspender.domain.usecases.transaction

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.bitspender.data.mappers.toDomainModel
import com.example.bitspender.domain.models.TransactionModel
import com.example.bitspender.domain.repositories.TransactionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetPagingTransactionsUseCase @Inject constructor(
    private val repository: TransactionRepository
) {
    operator fun invoke(pageSize:Int): Flow<PagingData<TransactionModel>> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                prefetchDistance = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                repository.getPagingTransaction(pageSize)
            }
        ).flow.map { it ->
            it.map {
                it.toDomainModel()
            }
        }
    }
}