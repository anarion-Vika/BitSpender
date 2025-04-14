package com.example.bitspender.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val transactionAmount: Double,
    val transactionType: String,
    val transactionCategory: String,
    val timestamp: Long,
)