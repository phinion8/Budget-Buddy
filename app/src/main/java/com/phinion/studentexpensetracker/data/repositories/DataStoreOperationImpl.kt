package com.phinion.studentexpensetracker.data.repositories

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.phinion.studentexpensetracker.domain.repositories.DataStoreOperations
import com.phinion.studentexpensetracker.utils.Constants.CURRENCY_PREFERENCE_KEY
import com.phinion.studentexpensetracker.utils.Constants.DAILY_BUDGET_KEY
import com.phinion.studentexpensetracker.utils.Constants.ON_BOARDING_PREFERENCES_KEY
import com.phinion.studentexpensetracker.utils.Constants.PREFERENCES_NAME
import com.phinion.studentexpensetracker.utils.Constants.USERNAME_PREFERENCES_KEY
import com.phinion.studentexpensetracker.utils.Constants.WEEKLY_BUDGET_KEY
import com.phinion.studentexpensetracker.utils.Constants.WISH_PREFERENCE_KEY
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

class DataStoreOperationImpl(context: Context) : DataStoreOperations {

    private object PreferencesKey {
        val onBoardingKey = booleanPreferencesKey(name = ON_BOARDING_PREFERENCES_KEY)
        val usernameKey = stringPreferencesKey(name = USERNAME_PREFERENCES_KEY)
        val dailyBudgetKey = floatPreferencesKey(name = DAILY_BUDGET_KEY)
        val weeklyBudgetKey = doublePreferencesKey(name = WEEKLY_BUDGET_KEY)
        val currencyKey = stringPreferencesKey(name = CURRENCY_PREFERENCE_KEY)
        val wishKey = stringPreferencesKey(name = WISH_PREFERENCE_KEY)
    }


    private val dataStore = context.dataStore


    override suspend fun saveOnBoardingState(completed: Boolean) {
        dataStore.edit { preferences ->

            preferences[PreferencesKey.onBoardingKey] = completed

        }
    }

    override fun readOnBoardingState(): Flow<Boolean> {
        return dataStore.data.catch { exception ->

            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }

        }.map { preferences ->

            val onBoardingState = preferences[PreferencesKey.onBoardingKey] ?: false
            onBoardingState

        }
    }

    override suspend fun saveUsername(name: String) {
        dataStore.edit { preferences ->

            preferences[PreferencesKey.usernameKey] = name

        }
    }

    override fun readUsername(): Flow<String> {
        return dataStore.data.catch { exception ->

            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }

        }.map { preferences ->

            val usernameState = preferences[PreferencesKey.usernameKey] ?: "User"
            usernameState

        }
    }

    override suspend fun saveDailyBudget(amount: Float) {
        dataStore.edit { preferences ->

            preferences[PreferencesKey.dailyBudgetKey] = amount

        }
    }

    override fun readDailyBudget(): Flow<Float> {
        return dataStore.data.catch { exception ->

            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }

        }.map { preferences ->

            val dailyBudgetState = preferences[PreferencesKey.dailyBudgetKey] ?: 0f
            dailyBudgetState

        }
    }

    override suspend fun saveWeeklyBudget(amount: Double) {
        dataStore.edit { preferences ->

            preferences[PreferencesKey.weeklyBudgetKey] = amount

        }
    }

    override fun readWeeklyBudget(): Flow<Double> {
        return dataStore.data.catch { exception ->

            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }

        }.map { preferences ->

            val weeklyBudgetState = preferences[PreferencesKey.weeklyBudgetKey] ?: 0.0
            weeklyBudgetState

        }
    }

    override suspend fun saveCurrency(currency: String) {
        dataStore.edit { preferences ->

            preferences[PreferencesKey.currencyKey] = currency

        }
    }

    override fun readCurrency(): Flow<String> {
        return dataStore.data.catch { exception ->

            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }

        }.map { preferences ->

            val currencyState = preferences[PreferencesKey.currencyKey] ?: "₹"
            currencyState

        }
    }

    override suspend fun saveDailyWish(wish: String) {
        dataStore.edit { preferences ->

            preferences[PreferencesKey.wishKey] = wish

        }
    }

    override fun readWish(): Flow<String> {
        return dataStore.data.catch { exception ->

            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }

        }.map { preferences ->

            val wishState = preferences[PreferencesKey.wishKey] ?: "Hello,"
            wishState

        }
    }
}