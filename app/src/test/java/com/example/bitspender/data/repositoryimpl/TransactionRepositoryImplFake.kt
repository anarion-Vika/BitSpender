package com.example.bitspender.data.repositoryimpl

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.bitspender.domain.models.TransactionModel
import com.example.bitspender.domain.repositories.TransactionRepository
import com.example.bitspender.domain.utils.AppResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TransactionRepositoryImplFake : TransactionRepository {

    val transactions = mutableListOf<TransactionModel>()

    override suspend fun addTransaction(transaction: TransactionModel): AppResult<Unit> {
        transactions.add(transaction)
        return AppResult.Success(Unit)
    }

    override fun getTransactions(): Flow<List<TransactionModel>> {
        return flowOf(transactions)
    }

    override suspend fun getTransactionsList(): List<TransactionModel> {
        return transactions
    }

    override fun getPagingTransaction(): PagingSource<Int, TransactionModel> {
        return object : PagingSource<Int, TransactionModel>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TransactionModel> {
                val page = params.key ?: 0
                val pageSize = params.loadSize
                val fromIndex = page * pageSize
                val toIndex = minOf(fromIndex + pageSize, transactions.size)
                return try {
                    val pagedData = if (fromIndex < toIndex) {
                        transactions
                            .sortedBy { it.id }
                            .subList(fromIndex, toIndex)
                    } else {
                        emptyList()
                    }
                    LoadResult.Page(
                        data = pagedData,
                        prevKey = if (page == 0) null else page - 1,
                        nextKey = if (toIndex >= transactions.size) null else page + 1
                    )
                } catch (e: Exception) {
                    LoadResult.Error(e)
                }
            }

            override fun getRefreshKey(state: PagingState<Int, TransactionModel>): Int? = null
        }
    }


}
