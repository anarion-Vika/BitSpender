package com.example.bitspender.presentation.transaction

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.example.bitspender.domain.models.TransactionModel
import com.example.bitspender.domain.usecases.btcrate.GetBtcRateUseCase
import com.example.bitspender.domain.usecases.btcrate.UpdateBtcRateUseCase
import com.example.bitspender.domain.usecases.transaction.GetBalanceUseCase
import com.example.bitspender.domain.usecases.transaction.GetPagingTransactionsUseCase
import com.example.bitspender.domain.utils.AppResult
import com.example.bitspender.presentation.base.BaseViewModel
import com.example.bitspender.presentation.transaction.adapter.TransactionUiItem
import com.example.bitspender.presentation.utils.formatDateToUi
import com.example.bitspender.presentation.utils.toLocalDate
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainTransactionViewModel @Inject constructor(
    private val getBtcRateUseCase: GetBtcRateUseCase,
    private val getBalanceUseCase: GetBalanceUseCase,
    private val getPagingTransactionsUseCase: GetPagingTransactionsUseCase,
    private val updateBtcRateUseCase: UpdateBtcRateUseCase,
) : BaseViewModel() {

    private val _uiStateFlow = MutableStateFlow(TransactionScreenState(isLoading = true))
    val uiStateFlow
        get() = _uiStateFlow.asStateFlow()

    private val refreshTrigger = MutableSharedFlow<Unit>(replay = 1)

    @OptIn(ExperimentalCoroutinesApi::class)
    val transactions: StateFlow<PagingData<TransactionUiItem>> = refreshTrigger
        .flatMapLatest {
            getPagingTransactionsUseCase(PAGE_SIZE)
                .map(::mapPagingData)
                .cachedIn(viewModelScope)
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = PagingData.empty()
        )

    init {
        getBalance()
        getBtcRate()
        refreshPaging()
    }

    private fun getBalance() {
        viewModelScope.launch {
            getBalanceUseCase().collect { newBalance ->
                _uiStateFlow.update {
                    it.copy(
                        currentBalance = newBalance,
                        isAddTransactionEnabled = newBalance > EMPTY_BALANCE
                    )
                }
            }
        }
    }

    private fun getBtcRate() {
        viewModelScope.launch {
            val result = updateBtcRateUseCase()
            when (result) {
                is AppResult.Success -> {
                    Unit
                }

                is AppResult.Error -> {
                    _uiStateFlow.update {
                        it.copy(
                            error = TransactionError(
                                isError = true,
                                textError = result.error.toUserMessage()
                            )
                        )
                    }
                }
            }

            getBtcRateUseCase().collect { newBalance ->
                _uiStateFlow.update {
                    it.copy(btcRateState = newBalance?.rate.toString())
                }
            }
        }
    }

    private fun mapPagingData(pagingData: PagingData<TransactionModel>): PagingData<TransactionUiItem> {
        return pagingData
            .map {
                Log.d("anarion", it.toString())
                TransactionUiItem.TransactionItem(it)
            }
            .insertSeparators { before, after ->
                val beforeDate = before?.transaction?.timestamp?.toLocalDate()?.formatDateToUi()
                val afterDate = after?.transaction?.timestamp?.toLocalDate()?.formatDateToUi()

                if (beforeDate != afterDate && afterDate != null) {
                    TransactionUiItem.DateItem(afterDate)
                } else null
            }
    }

    fun refreshPaging() {
        viewModelScope.launch {
            refreshTrigger.emit(Unit)
        }
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val EMPTY_BALANCE = 0.0

    }
}