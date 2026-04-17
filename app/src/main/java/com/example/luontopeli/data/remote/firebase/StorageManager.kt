// 📁 data/remote/firebase/StorageManager.kt
package com.example.luontopeli.data.remote.firebase

import android.net.Uri
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Offline-tilassa toimiva tallennushallinta (no-op).
 */
@Singleton
class StorageManager @Inject constructor() {

    suspend fun uploadImage(imageUri: Uri, fileName: String): Result<String> {
        // Palautetaan paikallinen polku, koska emme käytä pilveä
        return Result.success(imageUri.toString())
    }
}