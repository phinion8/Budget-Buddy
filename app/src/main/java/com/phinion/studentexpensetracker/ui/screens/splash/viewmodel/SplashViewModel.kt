package com.phinion.studentexpensetracker.ui.screens.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.phinion.studentexpensetracker.data.repositories.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _readOnBoardingState = MutableStateFlow(false)
    val readOnBoardingState: StateFlow<Boolean> = _readOnBoardingState

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _readOnBoardingState.value = repository.readOnBoardingState().stateIn(viewModelScope).value
        }
    }

}