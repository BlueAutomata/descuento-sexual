package org.luigui.descuento.sexual.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.luigui.descuento.sexual.data.SexualOrientation
import java.io.File

class SexualDiscountViewModel: ViewModel() {
    private val _sexualOrientation = MutableStateFlow<SexualOrientation?>(null)
    val sexualOrientation: StateFlow<SexualOrientation?> = _sexualOrientation

    private var _selectedFolderPath by mutableStateOf<String?>(getDefaultFolderPath())
    val selectedFolderPath: String? get() = _selectedFolderPath

    private val userHome: String = System.getProperty("user.home")
    private val storageFile = File(userHome, "user_selected_folder.txt")

    init {
        // Load the last selected folder path when the ViewModel is created
        loadSelectedFolderPath()
    }

    fun updateSelectedFolderPath(path: String) {
        _selectedFolderPath = path
        saveSelectedFolderPath(path)
    }

    private fun saveSelectedFolderPath(path: String) {
        try {
            storageFile.writeText(path)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getDefaultFolderPath(): String {
        return File(System.getProperty("user.home"), "Documents").absolutePath
    }

    private fun loadSelectedFolderPath() {
        try {
            if (storageFile.exists()) {
                _selectedFolderPath = storageFile.readText()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

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