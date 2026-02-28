package org.luigui.descuento.sexual.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.apache.poi.ss.usermodel.Row
import org.apache.poi.ss.usermodel.Sheet
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.luigui.descuento.sexual.data.Measurement
import org.luigui.descuento.sexual.data.SexualBehavior
import org.luigui.descuento.sexual.data.SexualDesirability
import org.luigui.descuento.sexual.data.WaitTime
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class SexualDiscountViewModel: ViewModel() {
    private val _sexualBehavior = MutableStateFlow<SexualBehavior?>(null)
    val sexualBehavior: StateFlow<SexualBehavior?> = _sexualBehavior

    private var _selectedFolderPath by mutableStateOf<String?>(getDefaultFolderPath())
    val selectedFolderPath: String? get() = _selectedFolderPath

    private val userHome: String = System.getProperty("user.home")
    private val storageFile = File(userHome, "user_selected_folder.txt")

    private val _nameAndCode = MutableStateFlow("")
    val nameAndCode: StateFlow<String> = _nameAndCode

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

    /// Current phase (1-4)
    private val _selectPhotoPhase = MutableStateFlow(1)
    val selectPhotoPhase: StateFlow<Int> = _selectPhotoPhase

    private val _selectWaitingTimeProbabilityPhase = MutableStateFlow(1)
    val selectWaitingTimeProbabilityPhase: StateFlow<Int> = _selectWaitingTimeProbabilityPhase

    private val _selectedPhotoWaitTimePhase = MutableStateFlow(1)
    val selectedPhotoWaitTimePhase: StateFlow<Int> = _selectedPhotoWaitTimePhase

    private val _rating = MutableStateFlow(0)
    val rating: StateFlow<Int> = _rating

    private val _attractivenessRating = MutableStateFlow(0)
    val attractivenessRating: StateFlow<Int> =  _attractivenessRating

    private val _folderExistsMessage = MutableStateFlow(false)
    val folderExistsMessage: StateFlow<Boolean> = _folderExistsMessage.asStateFlow()

    private val _comment = MutableStateFlow("")
    val comment: StateFlow<String> = _comment

    private val _photoType = MutableStateFlow("_fake")
    val photoType: StateFlow<String> = _photoType

    fun showFolderExistsMessage() {
        _folderExistsMessage.value = true
    }

    fun resetFolderExistsMessage() {
        _folderExistsMessage.value = false
    }

    // Store selections for each phase separately
    private val _phaseSelections = mutableStateMapOf<Int, String>(
        1 to "", // Attractive
        2 to "", // Unattractive
        3 to "", // High STD risk
        4 to ""  // Low STD risk
    )

    private val _phasePlaceholderSelections = mutableStateMapOf<Int, String>(
        1 to "", // Attractive
        2 to "", // Unattractive
        3 to "", // High STD risk
        4 to ""  // Low STD risk
    )

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
        val potentialPath = "images/${photoName}${_photoType.value}.jpg"
        return if (resourceExists(potentialPath)) potentialPath else null
    }

    fun switchPhoto(real: Boolean) {
        if (real) {
            _photoType.value = ""
        }
        else {
            _photoType.value = "_fake"
        }
    }

    fun setSexualBehavior(index: Int) {
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
                "images/placeholder_man_3.png",
                "images/placeholder_man_4.png",
            ).shuffled().take(4)
        } else {
            listOf(
                "images/placeholder_woman_1.png",
                "images/placeholder_woman_2.png",
                "images/placeholder_woman_3.png",
                "images/placeholder_woman_4.png",
                "images/placeholder_woman_5.png",
                "images/placeholder_woman_6.png",
                "images/placeholder_woman_7.png",
                "images/placeholder_woman_8.png",
                "images/placeholder_woman_9.png",
                "images/placeholder_woman_10.png",
                "images/placeholder_woman_11.png",
                "images/placeholder_woman_12.png",
            ).shuffled().take(12)
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


    fun setSelectedPhoto(photoName: String) {
        _selectedPhoto.value = photoName
        _phaseSelections[_selectPhotoPhase.value] = photoName
    }

    fun resetSelectedPhoto() {
        _selectedPhoto.value = null
    }

    fun setSelectedPlaceholderPhoto(photoPlaceholderName: String) {
        _phasePlaceholderSelections[_selectPhotoPhase.value] = photoPlaceholderName
    }

    fun isPhotoSelected(photoName: String): Boolean {
        return _phaseSelections[_selectPhotoPhase.value] == photoName
    }

    fun isPhotoSelected(): Boolean {
        return _phaseSelections[_selectPhotoPhase.value] != ""
    }

    fun updatePhotoPhase() {
        _selectPhotoPhase.value += 1
    }

    fun updateWaitingTimePhase() {
        _selectWaitingTimeProbabilityPhase.value += 1
        println(_phaseSelections[1])
    }

    fun updatePhotoWaitTimePhase() {
        _selectedPhotoWaitTimePhase.value += 1
    }

    fun resetWaitingTimePhase() {
        _selectWaitingTimeProbabilityPhase.value = 1
    }

    fun getPhasePhoto(): String {
        val photoName = _phaseSelections[selectedPhotoWaitTimePhase.value]
        val placeholderName = _phasePlaceholderSelections[selectedPhotoWaitTimePhase.value]
        return if (getPhotoPath(photoName!!) != null) {
            "images/${photoName}${_photoType.value}.jpg"
        } else {
            placeholderName!!
        }
    }

    fun clearAllSelections() {
        _phaseSelections.keys.forEach { _phaseSelections[it] = "" }
    }

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

    fun setRating(score: Int) {
        _rating.value = score
    }

    fun saveMeasurement() {
        try {
            val measurement = createMeasurement()
            writeMeasurementToExcel(measurement)
        } catch (e: Exception) {
            println("Measurement Failed to save measurement: ${e.message}")
        }
    }

    private fun createMeasurement(): Measurement {
        return Measurement(
            nameAndCode = _nameAndCode.value.ifEmpty { "Unknown" },
            selfAttractiveness = _attractivenessRating.value,
            sexualBehavior = _sexualBehavior.value?.toString() ?: "Not specified",
            desirableCategory = getDesirabilityCategory(),
            photoReference = getPhasePhoto(),
            waitTime = getWaitTime(),
            probabilityScore = _rating.value,
            comment = ""

        )
    }

    private fun getDesirabilityCategory(): String {
        return when (selectedPhotoWaitTimePhase.value) {
            1 -> SexualDesirability.ATTRACTIVE
            2 -> SexualDesirability.UNATTRACTIVE
            3 -> SexualDesirability.HIGH_STD_RISK
            4 -> SexualDesirability.LOW_STD_RISK
            else -> null
        }?.toString() ?: "Unknown"
    }

    private fun getWaitTime(): String {
        return when (_selectWaitingTimeProbabilityPhase.value) {
            1 -> WaitTime.ONE_HOUR
            2 -> WaitTime.THREE_HOURS
            3 -> WaitTime.SIX_HOURS
            4 -> WaitTime.ONE_DAY
            5 -> WaitTime.ONE_WEEK
            6 -> WaitTime.ONE_MONTH
            7 -> WaitTime.THREE_MONTHS
            else -> null
        }?.toString() ?: "Not specified"
    }

    private fun writeMeasurementToExcel(measurement: Measurement) {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        val currentDateTime = LocalDateTime.now()
        val currentDate = currentDateTime.format(dateFormatter)
        val currentTime = currentDateTime.format(timeFormatter)

        try {
            // Validate required fields
            if (measurement.nameAndCode.isNullOrEmpty()) {
                throw IllegalArgumentException("Measurement nameAndCode cannot be null or empty")
            }
            if (selectedFolderPath.isNullOrEmpty()) {
                throw IllegalArgumentException("Selected folder path cannot be null or empty")
            }

            // Sanitize and prepare directory paths
            val folderName = measurement.nameAndCode!!
            val rootDirectoryPath = "$selectedFolderPath${File.separator}"
            val rootFilePath = "$rootDirectoryPath${File.separator}data.xlsx"
            val userDirectoryPath = "$selectedFolderPath${File.separator}$folderName"
            val userFilePath = "$userDirectoryPath${File.separator}data.xlsx"

            // Ensure directories exist
            File(rootDirectoryPath).takeUnless { it.exists() }?.mkdirs()
            File(userDirectoryPath).takeUnless { it.exists() }?.mkdirs()

            val rootFile = File(rootFilePath)
            val userFile = File(userFilePath)

            // Create or load root workbook and add data
            val rootWorkbook = if (rootFile.exists()) {
                FileInputStream(rootFile).use { fis ->
                    WorkbookFactory.create(fis)
                }
            } else {
                XSSFWorkbook().also { wb ->
                    wb.createSheet("Sheet1").apply {
                        createHeaderRow(this)
                    }
                }
            }

            // Create or load user workbook and add data
            val userWorkbook = if (userFile.exists()) {
                FileInputStream(userFile).use { fis ->
                    WorkbookFactory.create(fis)
                }
            } else {
                XSSFWorkbook().also { wb ->
                    wb.createSheet("Sheet1").apply {
                        createHeaderRow(this)
                    }
                }
            }

            // Process both workbooks similarly
            listOf(rootWorkbook to rootFile, userWorkbook to userFile).forEach { (workbook, file) ->
                val sheet = workbook.getSheetAt(0) ?: workbook.createSheet("Sheet1").also {
                    createHeaderRow(it)
                }

                // Create new data row
                val newRow = sheet.createRow(sheet.lastRowNum + 1)

                // Translate values to Spanish
                val sexualDesirabilitySpanish = when (measurement.desirableCategory) {
                    "ATTRACTIVE" -> "ATRACTIVO"
                    "UNATTRACTIVE" -> "POCO ATRACTIVO"
                    "HIGH_STD_RISK" -> "ALTO RIESGO DE ITS"
                    "LOW_STD_RISK" -> "BAJO RIESGO DE ITS"
                    else -> measurement.desirableCategory ?: "Desconocido"
                }

                val sexualBehaviorSpanish = when (measurement.sexualBehavior) {
                    "WSW" -> "msm"
                    "HETEROSEXUAL" -> "heterosexual"
                    "Protected" -> "protegido"
                    "Unprotected" -> "sin protección"
                    else -> measurement.sexualBehavior ?: "Desconocido"
                }

                val waitTimeSpanish = when (measurement.waitTime) {
                    "ONE_HOUR" -> "1 hora"
                    "THREE_HOURS" -> "3 horas"
                    "SIX_HOURS" -> "6 horas"
                    "ONE_DAY" -> "1 día"
                    "ONE_WEEK" -> "1 semana"
                    "ONE_MONTH" -> "1 mes"
                    "THREE_MONTHS" -> "3 meses"
                    else -> measurement.waitTime ?: "No especificado"
                }

                // Populate cells with measurement data
                newRow.apply {
                    createCell(0).setCellValue(currentDate)
                    createCell(1).setCellValue(currentTime)
                    createCell(2).setCellValue(measurement.nameAndCode)
                    createCell(3).setCellValue(measurement.selfAttractiveness?.toString() ?: "N/A")  // New cell
                    createCell(4).setCellValue(sexualBehaviorSpanish)
                    createCell(5).setCellValue(sexualDesirabilitySpanish)
                    createCell(6).setCellValue(measurement.photoReference)
                    createCell(7).setCellValue(waitTimeSpanish)
                    createCell(8).setCellValue(measurement.probabilityScore?.toString() ?: "N/A")
                    createCell(9).setCellValue(measurement.comment)
                }

                // Write workbook
                FileOutputStream(file).use { fos ->
                    workbook.write(fos)
                }
            }
        } catch (e: Exception) {
            println("ExcelWrite Error writing measurement to Excel: ${e.message}")
            throw e
        }
    }

    private fun createHeaderRow(sheet: Sheet) {
        sheet.createRow(0).apply {
            createCell(0).setCellValue("Fecha")
            createCell(1).setCellValue("Hora")
            createCell(2).setCellValue("Nombre y Código")
            createCell(3).setCellValue("Atractivo Propio")  // New column
            createCell(4).setCellValue("Comportamiento Sexual")
            createCell(5).setCellValue("Categoría de Deseabilidad")
            createCell(6).setCellValue("Referencia de Foto")
            createCell(7).setCellValue("Tiempo de Espera")
            createCell(8).setCellValue("Probabilidad")
            createCell(9).setCellValue("Comentario")
        }
    }

    fun addCommentToLastRow(comment: String) {
        try {
            // Validate required fields
            if (selectedFolderPath.isNullOrEmpty()) {
                throw IllegalArgumentException("Selected folder path cannot be null or empty")
            }
            if (_nameAndCode.value.isEmpty()) {
                throw IllegalArgumentException("Measurement nameAndCode cannot be null or empty")
            }

            // Prepare directory paths
            val rootDirectoryPath = "$selectedFolderPath${File.separator}"
            val rootFilePath = "$rootDirectoryPath${File.separator}data.xlsx"
            val userDirectoryPath = "$selectedFolderPath${File.separator}${_nameAndCode.value}"
            val userFilePath = "$userDirectoryPath${File.separator}data.xlsx"

            // List of files to update (root and user-specific)
            val filesToUpdate = listOf(
                File(rootFilePath) to "root",
                File(userFilePath) to "user"
            )

            filesToUpdate.forEach { (file, fileType) ->
                if (!file.exists()) {
                    println("$fileType Excel file does not exist at ${file.absolutePath}")
                    return@forEach  // Skip this file if it doesn't exist
                }

                // Load workbook
                val workbook = FileInputStream(file).use { fis ->
                    WorkbookFactory.create(fis)
                }

                val sheet = workbook.getSheetAt(0) ?: throw IllegalStateException("Sheet not found in $fileType file")

                // Get the last row (data row, skipping header)
                val lastRow = sheet.getRow(sheet.lastRowNum) ?: throw IllegalStateException("No data rows found in $fileType file")

                lastRow.getCell(9, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK).setCellValue(comment)  // Changed from 8 to 9

                // Write workbook
                FileOutputStream(file).use { fos ->
                    workbook.write(fos)
                }

                println("Comment added successfully to the last row in $fileType file")
            }
        } catch (e: Exception) {
            println("Error adding comment to Excel: ${e.message}")
            throw e
        }
    }

    fun saveComment(comment: String) {
        _comment.value = comment
    }

    fun setAttractiveness(rating: Int) {
        _attractivenessRating.value = rating
    }
}

