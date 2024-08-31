package com.phinion.studentexpensetracker.navigation.screens.update_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phinion.studentexpensetracker.data.repositories.Repository
import com.phinion.studentexpensetracker.models.Category
import com.phinion.studentexpensetracker.models.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateScreenViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _selectedTransaction: MutableLiveData<Transaction> = MutableLiveData()
    val selectedTransaction: LiveData<Transaction?> = _selectedTransaction


    fun getSelectedTransaction(transactionId: Int) {
        viewModelScope.launch(Dispatchers.Main) {

            repository.getSelectedTransaction(transactionId = transactionId).collect {
                _selectedTransaction.value = it
            }

        }
    }

    fun updateSelectedTransaction(transaction: Transaction) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTransactionDetails(transaction = transaction)
        }
    }

}