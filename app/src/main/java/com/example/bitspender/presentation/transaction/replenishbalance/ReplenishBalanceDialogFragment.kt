package com.example.bitspender.presentation.transaction.replenishbalance

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.bitspender.databinding.DialogFragmentReplenishBalanceBinding
import com.example.bitspender.di.utils.Injectable
import com.example.bitspender.di.utils.ViewModelFactory
import com.example.bitspender.presentation.utils.observeWithLifecycle
import javax.inject.Inject

class ReplenishBalanceDialogFragment(
    private val listener: OnReplenishBalanceListener
) : DialogFragment(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[ReplenishBalanceViewModel::class.java]
    }
    private var binding: DialogFragmentReplenishBalanceBinding? = null

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val width = (resources.displayMetrics.widthPixels * 0.96).toInt()
        dialog?.window?.setLayout(width, WindowManager.LayoutParams.WRAP_CONTENT)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DialogFragmentReplenishBalanceBinding.inflate(inflater, container, false)
        this.binding = binding
        setupListeners()
        setupObserver()
        return binding.root
    }

    private fun setupListeners() {
        binding?.let {
            with(it) {
                btnReplenish.setOnClickListener {
                    etTransactionAmount.text.toString().toDoubleOrNull()?.let {
                        viewModel.addTransaction(
                            transactionAmount = it
                        )
                    }
                }

                etTransactionAmount.doAfterTextChanged {
                    it.toString().toDoubleOrNull()?.let { transactionAmount ->
                        btnReplenish.isEnabled = transactionAmount > DOUBLE_ZERO
                    }
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

    private fun handleUiState(state: ReplenishBalanceStateScreen) {
        when {
            state.isSaved -> {
                listener.onReplenishedBalance()
                dismiss()
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