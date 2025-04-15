package com.example.bitspender.presentation.transaction.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bitspender.databinding.ItemDateBinding
import com.example.bitspender.databinding.ItemTransactionBinding
import com.example.bitspender.domain.models.TransactionModel

class TransactionAdapter :
    PagingDataAdapter<TransactionUiItem, RecyclerView.ViewHolder>(TransactionDiffUtil()) {

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is TransactionUiItem.TransactionItem -> TYPE_TRANSACTION
            is TransactionUiItem.DateItem -> TYPE_DATE_SEPARATOR
            null -> throw IllegalStateException("Invalid item type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_TRANSACTION -> TransactionViewHolder(
                ItemTransactionBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

            TYPE_DATE_SEPARATOR -> DateSeparatorViewHolder(
                ItemDateBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )

            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is TransactionUiItem.TransactionItem -> (holder as TransactionViewHolder).bind(item.transaction)
            is TransactionUiItem.DateItem -> (holder as DateSeparatorViewHolder).bind(item.date)
            null ->  throw IllegalArgumentException("Invalid ViewHolder")
        }
    }


    class TransactionViewHolder(private val binding: ItemTransactionBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(transaction: TransactionModel) {
            binding.tvAmount.text = "${transaction.transactionAmount} BTC"
            binding.tvTime.text = (transaction.timestamp).toString() // форматни, як хочеш
            binding.tvCategory.text = transaction.transactionCategory.name.lowercase()
        }
    }

    class DateSeparatorViewHolder(private val binding: ItemDateBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(date: String) {
            binding.tvDate.text = date
        }
    }


    companion object {
        private const val TYPE_TRANSACTION = 0
        private const val TYPE_DATE_SEPARATOR = 1

    }
}
