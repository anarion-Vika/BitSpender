package com.example.bitspender.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bitspender.data.models.TransactionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(transaction: TransactionEntity)

    @Query("SELECT * FROM transactions ORDER BY timestamp DESC")
    suspend fun getAll(): Flow<List<TransactionEntity>>
}