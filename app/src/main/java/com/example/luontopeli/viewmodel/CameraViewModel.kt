package com.example.luontopeli.viewmodel

import android.content.Context
import android.graphics.Bitmap
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.luontopeli.data.local.entity.NatureSpot
import com.example.luontopeli.data.repository.NatureSpotRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor(
    private val repository: NatureSpotRepository
) : ViewModel() {

    private val _capturedImagePath = MutableStateFlow<String?>(null)
    val capturedImagePath: StateFlow<String?> = _capturedImagePath.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun takePhoto(context: Context, imageCapture: ImageCapture) {
        _isLoading.value = true
        imageCapture.takePicture(
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    // Tässä pitäisi oikeasti tallentaa kuva tiedostoon ja asettaa polku
                    // Demo-mielessä asetetaan vain joku arvo
                    _capturedImagePath.value = "temp_path"
                    _isLoading.value = false
                    image.close()
                }

                override fun onError(exception: ImageCaptureException) {
                    _isLoading.value = false
                }
            }
        )
    }

    fun clearCapturedImage() {
        _capturedImagePath.value = null
    }

    fun saveCurrentSpot() {
        viewModelScope.launch {
            // Logiikka tallentamiseen
            _capturedImagePath.value = null
        }
    }

    fun saveNatureSpot(name: String, latitude: Double, longitude: Double) {
        viewModelScope.launch {
            val spot = NatureSpot(
                name = name,
                latitude = latitude,
                longitude = longitude,
                timestamp = System.currentTimeMillis()
            )
            repository.insertSpot(spot)
        }
    }
}
