package org.luigui.descuento.sexual.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import org.luigui.descuento.sexual.data.SexualBehavior
import java.io.File

class SexualDiscountViewModel: ViewModel() {
    private val _sexualBehavior = MutableStateFlow<SexualBehavior?>(null)
    val sexualBehavior: StateFlow<SexualBehavior?> = _sexualBehavior

    private var _selectedFolderPath by mutableStateOf<String?>(getDefaultFolderPath())
    val selectedFolderPath: String? get() = _selectedFolderPath

    private val userHome: String = System.getProperty("user.home")
    private val storageFile = File(userHome, "user_selected_folder.txt")

    private val _nameAndCode = MutableStateFlow("")
    val nameAndCode: StateFlow<String> = _nameAndCode

    // private val _selectPhotoPhase = MutableStateFlow(1)
    //val selectPhotoPhase: StateFlow<Int> = _selectPhotoPhase

    private val _photoPlaceholders = MutableStateFlow<List<String>>(emptyList())
    val photoPlaceholders: StateFlow<List<String>> = _photoPlaceholders

    private val _attractivePhotoReference = MutableStateFlow("")
    val attractivePhotoReference: StateFlow<String> = _attractivePhotoReference

    private val _unattractivePhotoReference = MutableStateFlow("")
    val unattractivePhotoReference: StateFlow<String> = _unattractivePhotoReference

    private val _highSTDRiskPhoto = MutableStateFlow("")
    val highSTDRiskPhoto: StateFlow<String> = _highSTDRiskPhoto

    private val _lowSTDRiskPhoto = MutableStateFlow("")
    val lowSTDRiskPhoto: StateFlow<String> = _lowSTDRiskPhoto

    private val _selectedPhoto = MutableStateFlow<String?>(null)
    val selectedPhoto: StateFlow<String?> = _selectedPhoto

    init {
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

    // fun updatePhotoPhase() {
    //    _selectPhotoPhase.value += 1
    //}

    fun setSexualOrientation(index: Int) {
        when (index) {
            0 -> _sexualBehavior.value = SexualBehavior.HETEROSEXUAL
            1 -> _sexualBehavior.value = SexualBehavior.WSW
            else -> _sexualBehavior.value = null
        }
        print(_sexualBehavior.value)
    }

    fun updateAndCode(newName: String) {
        _nameAndCode.value = newName
    }

    fun doesFolderExist(): Boolean {
        val sanitizedCompleteName = _nameAndCode.value.trim().replace(" ", "_") ?: ""

        // Construct the folder name correctly
        val folderName = listOf(sanitizedCompleteName).filter { it.isNotEmpty() }.joinToString("_")

        val folderPath = "$selectedFolderPath${File.separator}${folderName}"
        return File(folderPath).exists()
    }

    fun getRandomPlaceholders() {
        _photoPlaceholders.value = if (_sexualBehavior.value == SexualBehavior.HETEROSEXUAL) {
            listOf(
                "images/placeholder_man_1.png",
                "images/placeholder_man_2.png",
            ).shuffled().take(2) // Get 4 random male placeholders
        } else {
            listOf(
                "images/placeholder_woman_1.png",
                "images/placeholder_woman_2.png",
            ).shuffled().take(2) // Get 4 random female placeholders
        }
    }

    fun setAttractivePhotoReference(ref: String) {
        _attractivePhotoReference.value = ref
    }

    fun setUnattractivePhotoReference(ref: String) {
        _unattractivePhotoReference.value = ref
    }

    fun setHighSTDRiskPhotoReference(ref: String) {
        _highSTDRiskPhoto.value = ref
    }

    fun setLowSTDRiskPhotoReference(ref: String) {
        _lowSTDRiskPhoto.value = ref
    }

    fun isAttractivePhotoSelected(photoName: String): Boolean {
        return _attractivePhotoReference.value == photoName
    }

    fun isUnattractivePhotoSelected(photoName: String): Boolean {
        return _unattractivePhotoReference.value == photoName
    }

    fun isHighSTDRiskPhotoSelected(photoName: String): Boolean {
        return _highSTDRiskPhoto.value == photoName
    }

    fun isLowSTDRiskPhotoSelected(photoName: String): Boolean {
        return _lowSTDRiskPhoto.value == photoName
    }

    /// Current phase (1-4)
    private val _selectPhotoPhase = MutableStateFlow(1)
    val selectPhotoPhase: StateFlow<Int> = _selectPhotoPhase

    // Store selections for each phase separately
    private val _phaseSelections = mutableStateMapOf<Int, String>(
        1 to "", // Attractive
        2 to "", // Unattractive
        3 to "", // High STD risk
        4 to ""  // Low STD risk
    )

    fun setSelectedPhoto(photoName: String) {
        _phaseSelections[_selectPhotoPhase.value] = photoName
    }

    fun isPhotoSelected(photoName: String): Boolean {
        return _phaseSelections[_selectPhotoPhase.value] == photoName
    }

    fun updatePhotoPhase() {
        _selectPhotoPhase.value += 1
    }

    // Clear all selections when needed
    fun clearAllSelections() {
        _phaseSelections.keys.forEach { _phaseSelections[it] = "" }
    }

    // Remove duplicate photo state variables
    // Remove individual isXxxPhotoSelected functions
    // Keep only the unified selection system abov

    fun debugResourcePath(resourceName: String) {
        println("\n=== Resource Debug ===")
        println("Requested resource: $resourceName")

        val classLoader = Thread.currentThread().contextClassLoader

        // Check in regular resources
        val resourceUrl = classLoader.getResource(resourceName)
        println("Resource URL: ${resourceUrl?.toExternalForm() ?: "NOT FOUND"}")

        // Check in compose resources
        val composeResourceUrl = classLoader.getResource("compose-resources/$resourceName")
        println("Compose Resource URL: ${composeResourceUrl?.toExternalForm() ?: "NOT FOUND"}")

        // Check filesystem paths
        val projectDirs = listOf(
            "src/commonMain/composeResources",
            "src/desktopMain/resources",
            "build/composeResources"
        )

        projectDirs.forEach { dir ->
            val file = File("$dir/$resourceName")
            println("Filesystem path: ${file.absolutePath} - Exists: ${file.exists()}")
        }
        println("===================\n")
    }
}