package org.luigui.descuento.sexual.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.luigui.descuento.sexual.viewmodels.SexualDiscountViewModel

@Composable
fun RegisterParticipantScreenContent(
    viewModel: SexualDiscountViewModel,
    onNavigateToNextScreen: () -> Unit
) {
    val nameAndCode by viewModel.nameAndCode.collectAsState()
    val isFormValid = nameAndCode.isNotBlank()
    val snackbarHostState = remember { SnackbarHostState() }

    // Show snackbar when folder exists
    LaunchedEffect(Unit) {
        viewModel.folderExistsMessage.collect { showMessage ->
            if (showMessage) {
                snackbarHostState.showSnackbar(
                    message = "Ya existe una carpeta para este usuario.",
                    duration = SnackbarDuration.Short,
                )
                viewModel.resetFolderExistsMessage()
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "Registrar Participante",
                    style = MaterialTheme.typography.headlineLarge,
                )

                Text(
                    text = "Iniciales del primer nombre, primer y segundo apellido; seguido de los 3 últimos dígitos del documento de identidad. Ej: Ana Pérez Gil – APG037",
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .padding(16.dp),
                    style = MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Start
                )

                TextField(
                    value = nameAndCode,
                    onValueChange = { newText ->
                        val filteredText = newText.replace("[^a-zA-Z0-9]".toRegex(), "")
                        viewModel.updateAndCode(filteredText)
                    },
                    label = {
                        Text(
                            text = "Iniciales + 3 últimos dígitos.",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp)
                        )
                    },
                    placeholder = {
                        Text(
                            text = "APG037",
                            style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp, color = Color.Gray)
                        )
                    },
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(0.75f),
                    textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Characters,
                        autoCorrectEnabled = false
                    )
                )

                Spacer(modifier = Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Button(
                        onClick = {
                            if (viewModel.doesFolderExist()) {
                                viewModel.showFolderExistsMessage()
                            } else {
                                onNavigateToNextScreen()
                            }
                        },
                        enabled = isFormValid
                    ) {
                        Text(
                            text = "Siguiente",
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }
        }
    }
}