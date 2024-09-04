package com.phinion.studentexpensetracker.ui.screens.transactions.update.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phinion.studentexpensetracker.data.repositories.Repository
import com.phinion.studentexpensetracker.data.local.entities.Transaction
import com.phinion.studentexpensetracker.domain.models.Category
import com.phinion.studentexpensetracker.domain.models.getCategoryList
import com.phinion.studentexpensetracker.utils.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateScreenViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _selectedTransactionState: MutableStateFlow<RequestState<Transaction>> = MutableStateFlow(RequestState.Idle)
    val selectedTransactionState = _selectedTransactionState.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _titleQuery: MutableState<String> = mutableStateOf("")
    val titleQuery: State<String> = _titleQuery

    private val _noteQuery: MutableState<String> = mutableStateOf("")
    val noteQuery: State<String> = _noteQuery

    private val _amountQuery: MutableState<String> = mutableStateOf("")
    val amountQuery: State<String> = _amountQuery

    private val _dropDownCategory = mutableStateOf(getCategoryList()[0])
    val dropDownCategory: State<Category> = _dropDownCategory

    private val _currentDate = mutableStateOf("")
    val currentDate: State<String> = _currentDate

    private val _timeInLong = mutableStateOf(0L)
    val timeInLong: State<Long> = _timeInLong

    fun updateLoading(value: Boolean){
        viewModelScope.launch {
            _loading.emit(value)
        }
    }

    fun updateTimeInLong(time: Long) {
        _timeInLong.value = time
    }

    fun updateAmountQuery(query: String) {
        _amountQuery.value = query
    }

    fun updateDropDownCategory(category: Category) {
        _dropDownCategory.value = category
    }

    fun updateCurrentDate(date: String) {
        _currentDate.value = date
    }

    fun updateTitleQuery(query: String){
        _titleQuery.value = query
    }

    fun updateNoteQuery(query: String) {
        viewModelScope.launch {
            _noteQuery.value = query
        }
    }

    fun getSelectedTransaction(transactionId: Int) {
        viewModelScope.launch(Dispatchers.Main) {

            repository.getSelectedTransaction(transactionId = transactionId).collect {result->
                when(result){
                    is RequestState.Loading -> {
                        updateLoading(true)
                    }
                    is RequestState.Success -> {
                        updateTitleQuery(result.data.title)
                        updateNoteQuery(result.data.note)
                        updateAmountQuery(result.data.amount)
                        updateDropDownCategory(result.data.category)
                        updateCurrentDate(result.data.time)
                        updateTimeInLong(result.data.timeInLong)
                        updateLoading(false)
                    }
                    is RequestState.Error -> {
                        updateLoading(false)
                    }
                    else -> {}
                }
            }

        }
    }

    fun updateSelectedTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTransactionDetails(transaction = transaction)
        }
    }

}