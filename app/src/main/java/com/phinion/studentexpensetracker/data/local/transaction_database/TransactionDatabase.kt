package com.phinion.studentexpensetracker.data.local.transaction_database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.phinion.studentexpensetracker.models.Transaction

@Database(entities = [Transaction::class], version = 2, exportSchema = false)
@TypeConverters(DatabaseConverter::class)
abstract class TransactionDatabase: RoomDatabase() {

    abstract fun transactionDao(): TransactionDao

}