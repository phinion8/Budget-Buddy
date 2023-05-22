package com.phinion.studentexpensetracker.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.phinion.studentexpensetracker.models.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM user_data_table WHERE id = :userId")
    fun getUserDetails(userId: Int): Flow<User>

    @Insert
    suspend fun addUser(user: User)

    @Update
    suspend fun updateUserDetails(user: User)
}