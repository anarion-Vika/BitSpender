package com.example.bitspender.presentation.transaction

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitspender.R
import com.example.bitspender.databinding.FragmentMainTransactionBinding
import com.example.bitspender.di.utils.Injectable
import com.example.bitspender.presentation.base.BaseFragment
import com.example.bitspender.presentation.transaction.adapter.TransactionAdapter
import com.example.bitspender.presentation.transaction.replenishbalance.ReplenishBalanceDialogFragment
import com.example.bitspender.presentation.utils.observeWithLifecycle
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainTransactionFragment : BaseFragment<FragmentMainTransactionBinding>(), Injectable {

    override val layoutResId: Int = R.layout.fragment_main_transaction

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainTransactionViewModel::class.java]
    }

    private val adapter by lazy { TransactionAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpOnClickListeners()
        setUpAdapter()
        setupObserver()
    }

    private fun setUpAdapter() {
        binding.rvTransactions.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTransactions.adapter = adapter

        val dividerItemDecoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        ContextCompat.getDrawable(requireContext(), R.drawable.divider)?.let {
            dividerItemDecoration.setDrawable(it)
        }
        binding.rvTransactions.addItemDecoration(dividerItemDecoration)
    }

    private fun setUpOnClickListeners() {
        with(binding) {
            btnAddTransaction.setOnClickListener {
                findNavController().navigate(MainTransactionFragmentDirections.actionTransactionsFragmentToAddTransactionFragment())
            }

            btnReplenish.setOnClickListener {
                openReplenishDialog()
            }
        }
    }

    private fun openReplenishDialog() {
        ReplenishBalanceDialogFragment().show(
            childFragmentManager,
            ReplenishBalanceDialogFragment::class.simpleName
        )
    }

    private fun setupObserver() {
        viewModel.uiStateFlow.observeWithLifecycle(
            viewLifecycleOwner,
            observer = ::handleUiState
        )

        lifecycleScope.launch {
            viewModel.transactions.collectLatest {
                Log.d("anarion", "Submitting to adapter")
                adapter.submitData(it)
            }
        }
    }

    private fun handleUiState(state: TransactionScreenState) {
        binding.btnAddTransaction.isEnabled = state.isAddTransactionEnabled
        binding.tvBalance.text = state.currentBalance.toString()
        binding.tvBtcRate.text = state.btcRateState

    }
}