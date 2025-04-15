package com.example.bitspender.presentation.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.example.bitspender.domain.usecases.btcrate.GetBtcRateUseCase
import com.example.bitspender.domain.usecases.transaction.GetBalanceUseCase
import com.example.bitspender.domain.usecases.transaction.GetPagingTransactionsUseCase
import com.example.bitspender.presentation.transaction.adapter.TransactionUiItem
import com.example.bitspender.presentation.utils.formatDateToUi
import com.example.bitspender.presentation.utils.toLocalDate
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainTransactionViewModel @Inject constructor(
    private val getBtcRateUseCase: GetBtcRateUseCase,
    private val getBalanceUseCase: GetBalanceUseCase,
    private val getPagingTransactionsUseCase: GetPagingTransactionsUseCase
) : ViewModel() {

    init {
        loadTransactionList()
    }

    private fun loadTransactionList() {
        viewModelScope.launch {

            val transactions = Pager(
                config = PagingConfig(
                    pageSize = 20,
                    enablePlaceholders = false
                ),
                pagingSourceFactory = { getPagingTransactionsUseCase() }
            ).flow.cachedIn(this)

            transactions.map { pagingData ->
                pagingData
                    .map {
                        TransactionUiItem.TransactionItem(it)
                    }
                    .insertSeparators { before, after ->
                        val beforeDate =
                            before?.transaction?.timestamp?.toLocalDate()?.formatDateToUi()
                        val afterDate =
                            after?.transaction?.timestamp?.toLocalDate()?.formatDateToUi()

                        if (beforeDate != afterDate && afterDate != null) {
                            TransactionUiItem.DateItem(afterDate)
                        } else null
                    }
            }
        }
    }


}