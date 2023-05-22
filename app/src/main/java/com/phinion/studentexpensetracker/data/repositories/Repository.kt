package com.phinion.studentexpensetracker.data.repositories

import com.phinion.studentexpensetracker.data.local.UserDao
import com.phinion.studentexpensetracker.data.local.transaction_database.TransactionDao
import com.phinion.studentexpensetracker.models.Transaction
import com.phinion.studentexpensetracker.models.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val userDao: UserDao,
    private val transactionDao: TransactionDao,
    private val dataStore: DataStoreOperations
) {


    suspend fun addUserDetails(user: User) {
        userDao.addUser(user = user)
    }


    fun getSelectedTransaction(transactionId: Int): Flow<Transaction>{
        return transactionDao.getSelectedTransaction(transactionId = transactionId)
    }

    val transactionList: Flow<List<Transaction>> = transactionDao.getAllTransactions()

    suspend fun addTransactionDetails(transaction: Transaction) {
        transactionDao.addTransactionDetails(transaction = transaction)
    }

    suspend fun updateTransactionDetails(transaction: Transaction) {
        transactionDao.updateTransactionDetails(transaction = transaction)
    }

    suspend fun deleteTransaction(transaction: Transaction) {
        transactionDao.deleteTransaction(transaction = transaction)
    }

    suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.saveOnBoardingState(completed = completed)
    }

    fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.readOnBoardingState()
    }

    suspend fun saveUsername(name: String) {
        dataStore.saveUsername(name = name)
    }

    fun readUsername(): Flow<String> {
        return dataStore.readUsername()
    }

    suspend fun saveDailyBudget(amount: Float) {
        dataStore.saveDailyBudget(amount = amount)
    }

    fun readDailyBudget(): Flow<Float> {
        return dataStore.readDailyBudget()
    }

    suspend fun saveWeeklyBudget(amount: Double) {
        dataStore.saveWeeklyBudget(amount = amount)
    }

    fun readWeeklyBudget(): Flow<Double> {
        return dataStore.readWeeklyBudget()
    }

    suspend fun saveCurrency(currency: String){
        dataStore.saveCurrency(currency = currency)
    }

    fun readCurrency(): Flow<String>{
        return dataStore.readCurrency()
    }

    suspend fun saveWish(wish: String){
        dataStore.saveDailyWish(wish = wish)
    }

    fun readWish(): Flow<String>{
        return dataStore.readWish()
    }


}