package com.example.luontopeli.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.luontopeli.data.local.AppDatabase
import com.example.luontopeli.data.local.entity.WalkSession
import com.example.luontopeli.sensor.StepCounterManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WalkViewModel @Inject constructor(
    application: Application,
    private val stepCounterManager: StepCounterManager
) : AndroidViewModel(application) {

    private val _isWalking = MutableStateFlow(false)
    val isWalking: StateFlow<Boolean> = _isWalking.asStateFlow()

    private val _steps = stepCounterManager.steps
    val steps: StateFlow<Int> = _steps

    private val _distance = MutableStateFlow(0.0)
    val distance: StateFlow<Double> = _distance.asStateFlow()

    init {
        viewModelScope.launch {
            steps.collect { stepCount ->
                _distance.value = stepCount * 0.7
            }
        }
    }

    fun startWalk() {
        _isWalking.value = true
        stepCounterManager.startListening()
    }

    fun stopWalk() {
        _isWalking.value = false
        stepCounterManager.stopListening()
    }
}
