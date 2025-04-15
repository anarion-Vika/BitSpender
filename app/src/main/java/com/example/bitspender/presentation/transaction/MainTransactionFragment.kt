package com.example.bitspender.presentation.transaction

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.bitspender.R
import com.example.bitspender.databinding.FragmentMainTransactionBinding
import com.example.bitspender.di.utils.Injectable
import com.example.bitspender.presentation.base.BaseFragment

class MainTransactionFragment : BaseFragment<FragmentMainTransactionBinding>(), Injectable {


    override val layoutResId: Int = R.layout.fragment_main_transaction

    private val viewModel by lazy {
        ViewModelProvider(this, viewModelFactory)[MainTransactionViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

}