package com.example.bitspender.data.utils

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.bitspender.data.local.transactions.TransactionsLocalDataSource
import com.example.bitspender.data.models.TransactionEntity
import javax.inject.Inject

class TransactionPagingSource @Inject constructor(
    private val localDataSource: TransactionsLocalDataSource
) : PagingSource<Int, TransactionEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TransactionEntity> {
        return try {
            val offset = params.key ?: 0
            val limit = 20

           val data = localDataSource.getTransactionsPage(limit, offset)

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

    override fun getRefreshKey(state: PagingState<Int, TransactionEntity>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.let { page ->
                // повертаємо offset, бо ми використовуємо offset замість page
                position - (position % state.config.pageSize)
            }
        }
    }
}
