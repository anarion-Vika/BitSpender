package com.example.bitspender.data.local.transactions

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.bitspender.data.database.TransactionDao
import com.example.bitspender.data.models.TransactionEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TransactionDaoFake : TransactionDao {

    val transactions = mutableListOf<TransactionEntity>()

    override suspend fun insert(transaction: TransactionEntity) {
        transactions.add(transaction)
    }

    override fun getAllAsFlow(): Flow<List<TransactionEntity>> {
        return flowOf(transactions)
    }

    override suspend fun getAllAsList(): List<TransactionEntity> {
        return transactions
    }

    override fun getPagedTransaction(): PagingSource<Int, TransactionEntity> {
        return object : PagingSource<Int, TransactionEntity>() {
            override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TransactionEntity> {
                return try {
                    val page = params.key ?: 0
                    val pageSize = params.loadSize
                    val fromIndex = page * pageSize
                    val toIndex = minOf(fromIndex + pageSize, transactions.size)

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

            override fun getRefreshKey(state: PagingState<Int, TransactionEntity>): Int? = null
        }
    }


}