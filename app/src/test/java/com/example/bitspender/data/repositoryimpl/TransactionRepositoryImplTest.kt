package com.example.bitspender.data.repositoryimpl


import androidx.paging.PagingSource
import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
import assertk.assertions.isInstanceOf
import com.example.bitspender.data.local.transactions.transactionModel
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class TransactionRepositoryImplTest {

    private lateinit var repositoryImplFake: TransactionRepositoryImplFake


    @BeforeEach
    fun setUp() {
        repositoryImplFake = TransactionRepositoryImplFake()
    }

    @Test
    fun `when multiple transactions are added, flow emits correct list with expected data`() =
        runTest {
            repositoryImplFake.addTransaction(transactionModel(1))
            repositoryImplFake.addTransaction(transactionModel(2))
            repositoryImplFake.addTransaction(transactionModel(3))
            repositoryImplFake.addTransaction(transactionModel(4))
            repositoryImplFake.getTransactions().test {
                val emission = awaitItem()
                assertThat(emission.size).isEqualTo(4)
                assertThat(emission.first().id).isEqualTo(1)
                awaitComplete()
            }
        }


    @Test
    fun `Test pagination - getting correct  data from last page`() = runTest {
        repeat(25) {
            repositoryImplFake.addTransaction(
                transactionModel(it).copy(transactionAmount = it * 30.0)
            )
        }

        val pageSize = 10

        val pagingSource = repositoryImplFake.getPagingTransaction(pageSize)
        val result = pagingSource.load(
            PagingSource.LoadParams.Append(
                key = 2,
                loadSize = pageSize,
                placeholdersEnabled = false
            )
        )

        assertThat(result).isInstanceOf(PagingSource.LoadResult.Page::class.java)
        val page = result as PagingSource.LoadResult.Page
        assertThat(page.data.size).isEqualTo(5)
        assertThat(page.data.last().transactionAmount).isEqualTo(24 * 30.0)
    }


    @Test
    fun `Test pagination - getting data from first page`() = runTest {
        repeat(30) {
            repositoryImplFake.addTransaction(
                transactionModel(it).copy(transactionAmount = it * 30.0)
            )
        }
        val pageSize = 10

        val pagingSource = repositoryImplFake.getPagingTransaction(pageSize)
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = 0,
                loadSize = pageSize,
                placeholdersEnabled = false
            )
        )

        assertThat(result).isInstanceOf(PagingSource.LoadResult.Page::class.java)
        val page = result as PagingSource.LoadResult.Page
        assertThat(page.data.size).isEqualTo(10)
        assertThat(page.data.first().transactionAmount).isEqualTo(0.0)
    }


}