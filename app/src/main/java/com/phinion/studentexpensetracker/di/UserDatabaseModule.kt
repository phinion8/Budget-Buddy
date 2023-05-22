package com.phinion.studentexpensetracker.di

import android.content.Context
import androidx.room.Room
import com.phinion.studentexpensetracker.data.local.UserDatabase
import com.phinion.studentexpensetracker.data.local.transaction_database.TransactionDatabase
import com.phinion.studentexpensetracker.data.repositories.DataStoreOperationImpl
import com.phinion.studentexpensetracker.data.repositories.DataStoreOperations
import com.phinion.studentexpensetracker.utils.Constants.TRANSACTION_DATABASE_NAME
import com.phinion.studentexpensetracker.utils.Constants.USER_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserDatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, UserDatabase::class.java, USER_DATABASE_NAME)
        .build()

    @Provides
    @Singleton
    fun provideUserDao(database: UserDatabase) = database.userDao()

    @Provides
    @Singleton
    fun provideTransactionDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, TransactionDatabase::class.java, TRANSACTION_DATABASE_NAME)
        .build()

    @Provides
    @Singleton
    fun provideTransactionDao(database: TransactionDatabase) = database.transactionDao()

    @Provides
    @Singleton
    fun provideDataStoreOperations(@ApplicationContext context: Context): DataStoreOperations{
        return DataStoreOperationImpl(context = context)
    }

}