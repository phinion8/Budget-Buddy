package com.phinion.studentexpensetracker.data.local.migrations

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


class MigrationFrom1To2 : Migration(1, 2) {
    override fun migrate(database: SupportSQLiteDatabase) {
         database.execSQL("ALTER TABLE transaction_table ADD COLUMN timeInLong Long")
    }
}