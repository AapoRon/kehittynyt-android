// 📁 data/remote/firebase/FirestoreManager.kt
package com.example.luontopeli.data.remote.firebase

import com.example.luontopeli.data.local.entity.NatureSpot
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Offline-tilassa toimiva Firestore-hallinta (no-op).
 * Kaikki data tallennetaan vain paikalliseen Room-tietokantaan.
 */
@Singleton
class FirestoreManager @Inject constructor() {

    suspend fun saveSpot(spot: NatureSpot): Result<Unit> = Result.success(Unit)

    fun getUserSpots(userId: String): Flow<List<NatureSpot>> = flowOf(emptyList())
}