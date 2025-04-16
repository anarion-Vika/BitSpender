package com.example.bitspender.data.local.transactions

import app.cash.turbine.test
import assertk.assertThat
import assertk.assertions.isEqualTo
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

}