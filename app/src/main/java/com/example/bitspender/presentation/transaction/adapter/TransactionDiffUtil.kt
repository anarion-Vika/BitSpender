package com.example.bitspender.presentation.transaction.adapter

import androidx.recyclerview.widget.DiffUtil

class TransactionDiffUtil : DiffUtil.ItemCallback<TransactionUiItem>() {

    override fun areItemsTheSame(
        oldItem: TransactionUiItem,
        newItem: TransactionUiItem
    ): Boolean {
        return when {
            oldItem is TransactionUiItem.TransactionItem && newItem is TransactionUiItem.TransactionItem -> {
                oldItem.transaction.id == newItem.transaction.id
            }

            oldItem is TransactionUiItem.DateItem && newItem is TransactionUiItem.DateItem -> {
                oldItem.date == newItem.date
            }

            else -> false
        }
    }

    override fun areContentsTheSame(
        oldItem: TransactionUiItem,
        newItem: TransactionUiItem
    ): Boolean {
        return when {
            oldItem is TransactionUiItem.TransactionItem && newItem is TransactionUiItem.TransactionItem -> {
                oldItem.transaction.transactionAmount == newItem.transaction.transactionAmount &&
                        oldItem.transaction.transactionType == newItem.transaction.transactionType &&
                        oldItem.transaction.transactionCategory == newItem.transaction.transactionCategory &&
                        oldItem.transaction.timestamp == newItem.transaction.timestamp
            }

            oldItem is TransactionUiItem.DateItem && newItem is TransactionUiItem.DateItem -> {
                oldItem.date == newItem.date
            }

            else -> false
        }
    }
}