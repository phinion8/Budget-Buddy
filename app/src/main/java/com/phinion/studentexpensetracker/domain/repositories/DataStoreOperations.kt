package com.phinion.studentexpensetracker.domain.repositories

import kotlinx.coroutines.flow.Flow


interface DataStoreOperations {
    suspend fun saveOnBoardingState(completed: Boolean)
    fun readOnBoardingState(): Flow<Boolean>

    suspend fun saveUsername(name: String)
    fun readUsername(): Flow<String>

    suspend fun saveDailyBudget(amount: Float)
    fun readDailyBudget(): Flow<Float>

    suspend fun saveWeeklyBudget(amount: Double)
    fun readWeeklyBudget(): Flow<Double>

    suspend fun saveCurrency(currency: String)
    fun readCurrency(): Flow<String>

    suspend fun saveDailyWish(wish: String)
    fun readWish(): Flow<String>
}