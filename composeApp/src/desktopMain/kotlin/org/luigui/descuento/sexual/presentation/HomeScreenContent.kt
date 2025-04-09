package org.luigui.descuento.sexual.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.luigui.descuento.sexual.viewmodels.SexualDiscountViewModel

@Composable
fun HomeScreenContent(
    viewModel: SexualDiscountViewModel,
    onNavigateToNextScreen: () -> Unit,
    onSelectFolder: () -> Unit
) {
    MaterialTheme{
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "¡Bienvenido!",
                    style = MaterialTheme.typography.headlineLarge,
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    // Text field to display the selected folder path
                    TextField(
                        value = viewModel.selectedFolderPath ?: "",
                        onValueChange = { /* Read-only, so no action */ },
                        modifier = Modifier
                            .weight(1f)
                            .padding(end = 8.dp),
                        label = {
                            Text(
                                text = "Carpeta Seleccionada", // Updated to Spanish
                                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp) // Increased font size
                            )
                        },
                        readOnly = true // Make the text field read-only
                    )

                    // Button to browse and select folder (medium emphasis)
                    Button(
                        onClick = onSelectFolder,
                        modifier = Modifier.padding(start = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer, // Medium emphasis
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    ) {
                        Text(
                            text = "Examinar", // Updated to Spanish
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp) // Increased font size
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.weight(1f)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Button(
                        onClick = onNavigateToNextScreen
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