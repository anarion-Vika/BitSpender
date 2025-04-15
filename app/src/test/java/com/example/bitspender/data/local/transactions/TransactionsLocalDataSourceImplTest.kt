package com.example.bitspender.data.local.transactions

import androidx.paging.PagingSource
import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TransactionsLocalDataSourceImplTest {

    private lateinit var daoFake: TransactionDaoFake

    private lateinit var localDataSource: TransactionsLocalDataSourceImpl

    @BeforeEach
    fun setUp() {
        daoFake = TransactionDaoFake()
        localDataSource = TransactionsLocalDataSourceImpl(daoFake)
    }

    @Test
    fun `if items was not add to cache, then cache is empty`() {
        val cache = daoFake.transactions
        assertThat(cache.size).isEqualTo(0)
    }

    @Test
    fun `Test if add 1 items , then size added item is 1`() = runTest {
        val cache = daoFake.transactions
        daoFake.insert(transactionEntity(1))
        assertThat(cache.size).isEqualTo(1)

        localDataSource.getAllTransaction().test {
            assertThat(awaitItem().size).isEqualTo(1)
            awaitComplete()
        }
        val listTransaction = localDataSource.getAllTransactionList()
        assertThat(listTransaction.size).isEqualTo(1)
        assertThat(listTransaction.first().id).isEqualTo(1)
    }

    @Test
    fun `Test pagination - getting data from first page`() = runTest {
        repeat(30) {
            daoFake.insert(
                transactionEntity(it).copy(transactionAmount = it * 30.0)
            )
        }

        val pagingSource = localDataSource.getPagingTransaction()
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 0,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        assertThat(result).isInstanceOf(PagingSource.LoadResult.Page::class.java)
        val page = result as PagingSource.LoadResult.Page
        assertThat(page.data.size).isEqualTo(10)
        assertThat(page.data.first().transactionAmount).isEqualTo(0.0)
    }

    @Test
    fun `Test pagination - getting correct  data from last page`() = runTest {
        repeat(25) {
            daoFake.insert(
                transactionEntity(it).copy(transactionAmount = it * 30.0)
            )
        }

        val pagingSource = localDataSource.getPagingTransaction()
        val result = pagingSource.load(
            PagingSource.LoadParams.Append(
                key = 2,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        assertThat(result).isInstanceOf(PagingSource.LoadResult.Page::class.java)
        val page = result as PagingSource.LoadResult.Page
        assertThat(page.data.size).isEqualTo(5)
        assertThat(page.data.last().transactionAmount).isEqualTo(24 * 30.0)
    }


}