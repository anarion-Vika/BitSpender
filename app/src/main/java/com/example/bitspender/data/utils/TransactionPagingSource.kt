package com.example.bitspender.data.utils

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.bitspender.data.local.transactions.TransactionsLocalDataSource
import com.example.bitspender.data.mappers.toDomainModel
import com.example.bitspender.domain.models.TransactionModel
import javax.inject.Inject

class TransactionPagingSource @Inject constructor(
    private val localDataSource: TransactionsLocalDataSource,
    private val pageSize: Int
) : PagingSource<Int, TransactionModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TransactionModel> {
        return try {
            val offset = params.key ?: 0
            val limit = pageSize

            val data = localDataSource.getTransactionsPage(limit, offset).map { it.toDomainModel() }

            val totalCount = localDataSource.getTotalCount()

            val nextKey = if (offset + limit < totalCount) offset + limit else null
            val prevKey = if (offset == 0) null else maxOf(0, offset - limit)

            LoadResult.Page(
                data = data,
                prevKey = prevKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, TransactionModel>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.let { page ->
                position - (position % state.config.pageSize)
            }
        }
    }
}
