package com.example.luontopeli.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.luontopeli.data.local.entity.WalkSession
import com.example.luontopeli.sensor.StepCounterManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class WalkViewModel @Inject constructor(
    application: Application,
    private val stepCounterManager: StepCounterManager
) : AndroidViewModel(application) {

    private val _isWalking = MutableStateFlow(false)
    val isWalking: StateFlow<Boolean> = _isWalking.asStateFlow()

    private val _currentSession = MutableStateFlow<WalkSession?>(null)
    val currentSession: StateFlow<WalkSession?> = _currentSession.asStateFlow()

    private val _steps = stepCounterManager.steps
    val steps: StateFlow<Int> = _steps

    private val _distance = MutableStateFlow(0.0)
    val distance: StateFlow<Double> = _distance.asStateFlow()

    init {
        viewModelScope.launch {
            steps.collect { stepCount ->
                _distance.value = stepCount * 0.7
                _currentSession.update { current ->
                    current?.copy(
                        steps = stepCount,
                        distance = stepCount * 0.7
                    )
                }
            }
        }
    }

    fun startWalk() {
        val session = WalkSession()
        _currentSession.value = session
        _isWalking.value = true
        stepCounterManager.startListening()
    }

    fun stopWalk() {
        _isWalking.value = false
        stepCounterManager.stopListening()
        _currentSession.update { it?.copy(
            endTime = System.currentTimeMillis(),
            isActive = false
        )}
    }
}

fun formatDistance(meters: Double): String {
    return if (meters < 1000.0) {
        "${meters.toInt()} m"
    } else {
        String.format(Locale.getDefault(), "%.1f km", meters / 1000.0)
    }
}

fun formatDuration(startTime: Long, endTime: Long = System.currentTimeMillis()): String {
    val seconds = (endTime - startTime) / 1000
    val minutes = seconds / 60
    val hours = minutes / 60
    return when {
        hours > 0 -> "${hours}h ${minutes % 60}min"
        minutes > 0 -> "${minutes}min ${seconds % 60}s"
        else -> "${seconds}s"
    }
}

fun Long.toFormattedDate(): String {
    val date = java.util.Date(this)
    val format = java.text.SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    return format.format(date)
}
