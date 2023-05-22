package com.phinion.studentexpensetracker.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.phinion.studentexpensetracker.models.User

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class UserDatabase: RoomDatabase() {

    abstract fun userDao(): UserDao

}