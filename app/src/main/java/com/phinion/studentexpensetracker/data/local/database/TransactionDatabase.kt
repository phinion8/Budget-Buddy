package com.phinion.studentexpensetracker.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.phinion.studentexpensetracker.data.local.coverters.Converters
import com.phinion.studentexpensetracker.data.local.dao.TransactionDao
import com.phinion.studentexpensetracker.data.local.entities.Transaction

@Database(entities = [Transaction::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class TransactionDatabase: RoomDatabase() {

    abstract fun transactionDao(): TransactionDao

}