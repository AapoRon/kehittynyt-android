// 📁 data/remote/firebase/AuthManager.kt
package com.example.luontopeli.data.remote.firebase

import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Offline-tilassa toimiva käyttäjähallinta.
 * Generoi paikallisen UUID:n käyttäjätunnisteeksi.
 * @Singleton varmistaa, että sama ID pysyy käytössä koko sovelluksen ajan.
 */
@Singleton
class AuthManager @Inject constructor() {
    private val localUserId: String = UUID.randomUUID().toString()

    val currentUserId: String get() = localUserId
    val isSignedIn: Boolean get() = true

    suspend fun signInAnonymously(): Result<String> = Result.success(localUserId)

    fun signOut() {
        /* Ei tarvita offline-tilassa */
    }
}