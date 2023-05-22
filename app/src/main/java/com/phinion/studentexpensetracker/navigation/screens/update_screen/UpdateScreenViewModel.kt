package com.phinion.studentexpensetracker.navigation.screens.update_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phinion.studentexpensetracker.data.repositories.Repository
import com.phinion.studentexpensetracker.models.Category
import com.phinion.studentexpensetracker.models.Transaction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateScreenViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    val title: MutableState<String> = mutableStateOf("")
    val note: MutableState<String> = mutableStateOf("")
    val amount: MutableState<String> = mutableStateOf("")
    val category: MutableState<Category?> = mutableStateOf(null)

    private val _selectedTransaction: MutableStateFlow<Transaction?> = MutableStateFlow(null)
    val selectedTransaction: StateFlow<Transaction?> = _selectedTransaction


    fun getSelectedTransaction(transactionId: Int) {
        viewModelScope.launch(Dispatchers.IO) {

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

    fun updateTitle(value: String){
        title.value = value
    }

    fun updateNote(value: String){
        note.value = value
    }

    fun updateAmount(value: String){
        amount.value = value
    }

    fun updateCategory(value: Category){
        category.value = value
    }

}

data class UiEvent(

    val selectedTransactionId: Int = -1,
    val selectedTransaction: Transaction? = null,

)