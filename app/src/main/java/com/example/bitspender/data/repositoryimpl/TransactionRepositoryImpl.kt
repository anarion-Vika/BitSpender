package com.example.bitspender.data.repositoryimpl

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.bitspender.data.local.transactions.TransactionsLocalDataSource
import com.example.bitspender.data.mappers.toDomainModel
import com.example.bitspender.data.mappers.toEntity
import com.example.bitspender.domain.models.TransactionModel
import com.example.bitspender.domain.repositories.TransactionRepository
import com.example.bitspender.domain.utils.AppError
import com.example.bitspender.domain.utils.AppResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import com.example.bitspender.data.models.TransactionEntity

class TransactionRepositoryImpl @Inject constructor(
    private val localDataSource: TransactionsLocalDataSource,
) : TransactionRepository {

    override suspend fun addTransaction(transaction: TransactionModel): AppResult<Unit> {
        return try {
            localDataSource.addTransaction(transaction.toEntity())
            AppResult.Success(Unit)
        } catch (e: Exception) {
            AppResult.Error(AppError.LocalError(e.message))
        }
    }

    override fun getTransactions(): Flow<List<TransactionModel>> {
        return localDataSource.getAllTransaction().map { listTransactions ->
            listTransactions.map { transaction -> transaction.toDomainModel() }
        }
    }

    override suspend fun getTransactionsList(): List<TransactionModel> {
        return localDataSource.getAllTransactionList()
            .map { transaction -> transaction.toDomainModel() }
    }

    override fun getPagingTransaction(): PagingSource<Int, TransactionModel> {
        return TransactionEntityToModelPagingSource(localDataSource.getPagingTransaction())
    }

    class TransactionEntityToModelPagingSource(
        private val source: PagingSource<Int, TransactionEntity>
    ) : PagingSource<Int, TransactionModel>() {
        override fun getRefreshKey(state: PagingState<Int, TransactionModel>): Int? {
            return state.anchorPosition
        }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TransactionModel> {
            return when (val result = source.load(params)) {
                is LoadResult.Error -> LoadResult.Error(result.throwable)
                is LoadResult.Page -> LoadResult.Page(
                    data = result.data.map { it.toDomainModel() },
                    prevKey = result.prevKey,
                    nextKey = result.nextKey
                )

                is LoadResult.Invalid -> LoadResult.Invalid()
            }
        }
    }

}