package com.phinion.studentexpensetracker.navigation.screens.on_boarding_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phinion.studentexpensetracker.data.repositories.Repository
import com.phinion.studentexpensetracker.models.Currency
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {


    fun saveOnBoardingState(completed: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveOnBoardingState(completed = completed)
        }
    }

    fun saveUsername(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveUsername(name = name)
        }
    }

    fun saveCurrency(currency: String){
        viewModelScope.launch(Dispatchers.IO) {
            repository.saveCurrency(currency = currency)
        }
    }

}