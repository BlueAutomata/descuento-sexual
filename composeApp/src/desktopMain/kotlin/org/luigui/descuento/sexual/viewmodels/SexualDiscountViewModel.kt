package org.luigui.descuento.sexual.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.luigui.descuento.sexual.data.SexualOrientation

class SexualDiscountViewModel: ViewModel() {
    private val _sexualOrientation = MutableStateFlow<SexualOrientation?>(null)
    val sexualOrientation: StateFlow<SexualOrientation?> = _sexualOrientation

    private fun resourceExists(path: String): Boolean {
        return try {
            // For JVM/Desktop
            val resource = this::class.java.classLoader.getResource(path)
            resource != null
        } catch (e: Exception) {
            false
        }
    }

    fun getPhotoPath(photoName: String): String? {
        val potentialPath = "$photoName.png"
        return if (resourceExists(potentialPath)) potentialPath else null
    }

    fun setSexualOrientation(index: Int) {
        when (index) {
            0 -> _sexualOrientation.value = SexualOrientation.HETEROSEXUAL
            1 -> _sexualOrientation.value = SexualOrientation.LESBIAN
            else -> _sexualOrientation.value = null
        }
    }
}