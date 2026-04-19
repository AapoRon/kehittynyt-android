package com.example.luontopeli.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.luontopeli.data.local.entity.WalkSession
import com.example.luontopeli.data.repository.NatureSpotRepository
import com.example.luontopeli.data.repository.WalkRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

/**
 * ViewModel tilastonäkymälle (StatsScreen).
 *
 * Lataa kaikki kävelykerrat ja luontolöytöjen lukumäärän repositoryjen kautta.
 * Tarjoaa datan StatsScreen-näkymälle yhteenvetotilastojen (askeleet, matka,
 * löydöt, lenkit) ja kävelyhistorian näyttämiseen.
 *
 * Flow-virrat päivittyvät automaattisesti kun tietokanta muuttuu.
 */
@HiltViewModel
class StatsViewModel @Inject constructor(
    private val walkRepository: WalkRepository,
    private val spotRepository: NatureSpotRepository
) : ViewModel() {

    /** Kaikki kävelykerrat aikajärjestyksessä (uusin ensin) */
    val allSessions: StateFlow<List<WalkSession>> = walkRepository.allSessions
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    /** Luontolöytöjen kokonaismäärä */
    val totalSpots: StateFlow<Int> = spotRepository.allSpots
        .map { it.size }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = 0
        )

    /** Askeleiden kokonaismäärä */
    val totalSteps: StateFlow<Int> = allSessions.map { sessions ->
        sessions.sumOf { it.steps }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0
    )

    /** Kokonaismatka metreinä */
    val totalDistance: StateFlow<Double> = allSessions.map { sessions ->
        sessions.sumOf { it.distance }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = 0.0
    )
}
