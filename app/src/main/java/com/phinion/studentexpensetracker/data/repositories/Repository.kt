package com.phinion.studentexpensetracker.data.repositories

import com.phinion.studentexpensetracker.data.local.dao.TransactionDao
import com.phinion.studentexpensetracker.data.local.entities.Transaction
import com.phinion.studentexpensetracker.domain.repositories.DataStoreOperations
import com.phinion.studentexpensetracker.utils.RequestState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class Repository @Inject constructor(
    private val transactionDao: TransactionDao,
    private val dataStore: DataStoreOperations
) {
    fun getSelectedTransaction(transactionId: Int): Flow<RequestState<Transaction>?> = flow {
        emit(RequestState.Loading)
        try {
            val selectedTransaction =
                transactionDao.getSelectedTransaction(transactionId = transactionId)
            emit(RequestState.Success(data = selectedTransaction))
        } catch (e: Exception) {
            emit(e.localizedMessage?.let { RequestState.Error(it) })
        }
    }.catch {
        it.localizedMessage?.let { it1 -> RequestState.Error(it1) }?.let { it2 -> emit(it2) }
    }.flowOn(Dispatchers.IO)

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

    suspend fun saveCurrency(currency: String) {
        dataStore.saveCurrency(currency = currency)
    }

    fun readCurrency(): Flow<String> {
        return dataStore.readCurrency()
    }

}