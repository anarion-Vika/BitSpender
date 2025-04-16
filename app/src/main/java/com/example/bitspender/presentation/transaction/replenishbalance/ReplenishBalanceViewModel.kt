package com.example.bitspender.presentation.transaction.replenishbalance

import androidx.lifecycle.viewModelScope
import com.example.bitspender.domain.models.TransactionCategory
import com.example.bitspender.domain.models.TransactionModel
import com.example.bitspender.domain.models.TransactionType
import com.example.bitspender.domain.usecases.transaction.AddTransactionUseCase
import com.example.bitspender.domain.utils.AppResult
import com.example.bitspender.presentation.base.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class ReplenishBalanceViewModel @Inject constructor(
    private val addTransactionUseCase: AddTransactionUseCase
) : BaseViewModel() {

    private val _uiStateFlow = MutableStateFlow(ReplenishBalanceStateScreen(isLoading = true))
    val uiStateFlow
        get() = _uiStateFlow.asStateFlow()

    fun addTransaction(transactionAmount: Double) {
        viewModelScope.launch {
            val result = addTransactionUseCase(
                TransactionModel(
                    id = DEFAULT_ID,
                    transactionAmount = transactionAmount,
                    transactionType = TransactionType.INCOME,
                    transactionCategory = TransactionCategory.OTHER,
                    timestamp = System.currentTimeMillis()
                )
            )

            when (result) {
                is AppResult.Success -> {
                    _uiStateFlow.update {
                        it.copy(
                            isSaved = true
                        )
                    }
                }

                is AppResult.Error -> {
                    _uiStateFlow.update {
                        it.copy(
                            error = ReplenishBalanceError(
                                isError = true,
                                textError = result.error.toUserMessage()
                            )
                        )
                    }

                }
            }

        }

    }

    companion object {
        private const val DEFAULT_ID = 0
    }
}