package com.phinion.studentexpensetracker.data.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phinion.studentexpensetracker.data.repositories.Repository
import com.phinion.studentexpensetracker.models.Currency
import com.phinion.studentexpensetracker.models.Transaction
import com.phinion.studentexpensetracker.models.User
import com.phinion.studentexpensetracker.utils.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _currencyValue: MutableStateFlow<String> = MutableStateFlow("")
    val currencyValue: StateFlow<String> = _currencyValue

    private val _username: MutableStateFlow<String> = MutableStateFlow("")
    val username: StateFlow<String> = _username

    private val _dailyBudget: MutableStateFlow<Float> = MutableStateFlow(0f)
    val dailyBudget: StateFlow<Float> = _dailyBudget

    val value: Double = 0.0
    private val _weeklyBudget: MutableStateFlow<Double> = MutableStateFlow(0.0)
    val weeklyBudget: StateFlow<Double> = _weeklyBudget


    private val _allTransactions =
        MutableStateFlow<RequestState<List<Transaction>>>(RequestState.Idle)
    val allTransaction: StateFlow<RequestState<List<Transaction>>> = _allTransactions


    init {

        viewModelScope.launch(Dispatchers.IO) {
            _currencyValue.value = repository.readCurrency().stateIn(viewModelScope).value
        }

        viewModelScope.launch(Dispatchers.IO) {
            _dailyBudget.value = repository.readDailyBudget().stateIn(viewModelScope).value
        }

        viewModelScope.launch(Dispatchers.IO) {
            _weeklyBudget.value = repository.readWeeklyBudget().stateIn(viewModelScope).value
        }

        viewModelScope.launch(Dispatchers.IO) {
            _username.value = repository.readUsername().stateIn(viewModelScope).value
        }

        viewModelScope.launch(Dispatchers.IO) {
            try {

                repository.transactionList.collect {
                    _allTransactions.value = RequestState.Success(it)
                }

            } catch (e: Exception) {
                _allTransactions.value = RequestState.Error(e)
            }


        }

    }

    fun saveDailyBudget(amount: Float) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveDailyBudget(amount = amount)
        }
    }

    fun saveWeeklyBudget(amount: Double) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveWeeklyBudget(amount = amount)
        }
    }

    fun saveUsername(name: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveUsername(name = name)
        }
    }

    fun saveCurrency(currency: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveCurrency(currency = currency)
        }
    }


    private val _userNameQuery = mutableStateOf("")
    val userNameQuery = _userNameQuery

    private val _userBalanceQuery = mutableStateOf(0f)
    val userBalanceQuery = _userBalanceQuery

    fun addUserDetails(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addUserDetails(user = user)
        }
    }




    fun updateUserNameQuery(query: String) {

        _userNameQuery.value = query

    }

    fun updateUserBalanceQuery(query: Float) {

        _userBalanceQuery.value = query

    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTransaction(transaction = transaction)
        }
    }


}