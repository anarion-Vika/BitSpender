package com.example.bitspender.presentation.addtransaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitspender.domain.models.TransactionCategory
import com.example.bitspender.domain.models.TransactionModel
import com.example.bitspender.domain.models.TransactionType
import com.example.bitspender.domain.usecases.transaction.AddTransactionUseCase
import com.example.bitspender.domain.utils.AppError
import com.example.bitspender.domain.utils.AppResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

class AddTransactionViewModel @Inject constructor(
    private val addTransactionUseCase: AddTransactionUseCase
) : ViewModel() {

    private val _uiStateFlow = MutableStateFlow(AddTransactionStateScreen(isLoading = true))
    val uiStateFlow
        get() = _uiStateFlow.asStateFlow()

    fun addTransaction(transactionAmount: Double, transactionCategory: TransactionCategory) {
        viewModelScope.launch {
            val result = addTransactionUseCase(
                TransactionModel(
                    id = DEFAULT_ID,
                    transactionAmount = transactionAmount,
                    transactionType = TransactionType.EXPENSE,
                    transactionCategory = transactionCategory,
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

                    when (result.error) {

                        is AppError.InsufficientFunds -> {
                            val errorInfo = result.error
                            _uiStateFlow.update {
                                it.copy(
                                    error = AddTransactionError(
                                        isError = true,
                                        textError = "На вашому балансі недостатньо BTC. Ваш баланс: ${errorInfo.current}, необхідно: ${errorInfo.required}"
                                    )
                                )
                            }
                        }

                        else -> {
                            _uiStateFlow.update {
                                it.copy(
                                    error = AddTransactionError(
                                        isError = true,
                                        textError = result.error.toString()
                                    )
                                )
                            }
                        }
                    }

                }
            }

        }

    }

    companion object {
        private const val DEFAULT_ID = 0
    }
}