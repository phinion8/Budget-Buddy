package com.phinion.studentexpensetracker.ui.screens.transactions.add.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phinion.studentexpensetracker.data.repositories.Repository
import com.phinion.studentexpensetracker.data.local.entities.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {



    private val _titleQuery = mutableStateOf("")
    val titleQuery = _titleQuery

    fun updateTitleQuery(query: String) {
        _titleQuery.value = query
    }

    private val _noteQuery = mutableStateOf("")
    val noteQuery = _noteQuery

    fun updateNoteQuery(query: String) {
        _noteQuery.value = query
    }

    private val _amountQuery = mutableStateOf("")
    val amountQuery = _amountQuery

    fun updateAmountQuery(query: String) {
        _amountQuery.value = query
    }

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTransactionDetails(transaction = transaction)
        }
    }

    fun deleteTransaction(transaction: Transaction){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTransaction(transaction = transaction)
        }
    }


}