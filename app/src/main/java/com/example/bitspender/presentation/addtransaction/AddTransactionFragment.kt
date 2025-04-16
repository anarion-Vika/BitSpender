package com.example.bitspender.presentation.addtransaction

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.bitspender.R
import com.example.bitspender.databinding.FragmentAddTransactionBinding
import com.example.bitspender.di.utils.Injectable
import com.example.bitspender.domain.models.TransactionCategory
import com.example.bitspender.presentation.base.BaseFragment
import com.example.bitspender.presentation.utils.observeWithLifecycle

class AddTransactionFragment : BaseFragment<FragmentAddTransactionBinding>(), Injectable {

    override val layoutResId: Int = R.layout.fragment_add_transaction

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[AddTransactionViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpListener()
        setUpTransactionCategory()
        setupObserver()
    }

    private fun setUpTransactionCategory() {
        val list = TransactionCategory.entries.map {
            it.name
        }.toTypedArray()

        val picker = binding.npTransactionCategory.apply {
            minValue = 0
            maxValue = list.count().dec().takeIf { it >= 0 } ?: 0
            displayedValues = list
        }
        binding.btnAddTransaction.setOnClickListener {
            val transactionCategory = TransactionCategory.valueOf(list[picker.value])

            binding.etTransactionAmount.text.toString().toDoubleOrNull()?.let {
                viewModel.addTransaction(
                    transactionCategory = transactionCategory,
                    transactionAmount = it
                )
            }

        }

    }

    private fun setUpListener() {
        with(binding) {

            etTransactionAmount.doAfterTextChanged {
                it.toString().toDoubleOrNull()?.let { transactionAmount ->
                    btnAddTransaction.isEnabled = transactionAmount > DOUBLE_ZERO
                }
            }
        }

    }

    private fun setupObserver() {
        viewModel.uiStateFlow.observeWithLifecycle(
            viewLifecycleOwner,
            observer = ::handleUiState
        )
    }

    private fun handleUiState(state: AddTransactionStateScreen) {
        when {
            state.isSaved -> {
                findNavController().popBackStack()
            }

            state.error.isError -> {
                Toast.makeText(requireActivity(), state.error.textError, Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        private const val DOUBLE_ZERO = 0.0
    }
}