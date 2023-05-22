package com.phinion.studentexpensetracker.data.local.transaction_database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.phinion.studentexpensetracker.models.Transaction
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {

    @Query("SELECT * FROM transaction_table ORDER BY id DESC")
    fun getAllTransactions(): Flow<List<Transaction>>

    @Query("SELECT * FROM transaction_table WHERE id = :transactionId")
    fun getSelectedTransaction(transactionId: Int): Flow<Transaction>

    @Insert
    suspend fun addTransactionDetails(transaction: Transaction)

    @Update
    suspend fun updateTransactionDetails(transaction: Transaction)

    @Delete
    suspend fun deleteTransaction(transaction: Transaction)

}