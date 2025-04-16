package com.example.bitspender.presentation.transaction

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import com.example.bitspender.data.database.TransactionDao
import com.example.bitspender.data.mappers.toDomainModel
import com.example.bitspender.data.models.TransactionEntity
import com.example.bitspender.domain.usecases.btcrate.GetBtcRateUseCase
import com.example.bitspender.domain.usecases.btcrate.UpdateBtcRateUseCase
import com.example.bitspender.domain.usecases.transaction.AddTransactionUseCase
import com.example.bitspender.domain.usecases.transaction.GetBalanceUseCase
import com.example.bitspender.domain.usecases.transaction.GetPagingTransactionsUseCase
import com.example.bitspender.presentation.transaction.adapter.TransactionUiItem
import com.example.bitspender.presentation.utils.formatDateToUi
import com.example.bitspender.presentation.utils.toLocalDate
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainTransactionViewModel @Inject constructor(
    private val getBtcRateUseCase: GetBtcRateUseCase,
    private val getBalanceUseCase: GetBalanceUseCase,
    private val getPagingTransactionsUseCase: GetPagingTransactionsUseCase,
    private val addTransactionUseCase: AddTransactionUseCase,
    private val updateBtcRateUseCase: UpdateBtcRateUseCase,
    private val dao: TransactionDao,
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow(TransactionScreenState(isLoading = true))
    val uiStateFlow
        get() = _uiStateFlow.asStateFlow()

    private val _transactions2 = MutableStateFlow<PagingData<TransactionUiItem>>(PagingData.empty())
    val transactions2: StateFlow<PagingData<TransactionUiItem>> = _transactions2


    private val _sideEffectFlow = MutableSharedFlow<TransactionScreenEvent>(extraBufferCapacity = 1)
    val sideEffectFlow
        get() = _sideEffectFlow.asSharedFlow()

    init {
        getBalance()
        getBtcRate()

        Log.d("anarion", "transactions Flow INIT")
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
            updateBtcRateUseCase()


            getBtcRateUseCase().collect { newBalance ->
                _uiStateFlow.update {
                    it.copy(btcRateState = newBalance?.rate.toString())
                }
            }
        }
    }

    val transactions: Flow<PagingData<TransactionUiItem>> =
        getPagingTransactionsUseCase()
            .map { pagingData ->
                mapPagingData(pagingData)
            }
            .cachedIn(viewModelScope)


    private fun mapPagingData(pagingData: PagingData<TransactionEntity>): PagingData<TransactionUiItem> {
        return pagingData
            .map {
                Log.d("anarion", it.toString())
                TransactionUiItem.TransactionItem(it.toDomainModel()) }
            .insertSeparators { before, after ->
                val beforeDate = before?.transaction?.timestamp?.toLocalDate()?.formatDateToUi()
                val afterDate = after?.transaction?.timestamp?.toLocalDate()?.formatDateToUi()

                if (beforeDate != afterDate && afterDate != null) {
                    TransactionUiItem.DateItem(afterDate)
                } else null
            }
    }

    companion object {
        private const val PAGE_SIZE = 20
        private const val EMPTY_BALANCE = 0.0

    }
}