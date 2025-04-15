package com.example.bitspender.presentation.transaction

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bitspender.R
import com.example.bitspender.databinding.FragmentMainTransactionBinding
import com.example.bitspender.di.utils.Injectable
import com.example.bitspender.presentation.base.BaseFragment
import com.example.bitspender.presentation.transaction.adapter.TransactionAdapter

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
        }
    }

}