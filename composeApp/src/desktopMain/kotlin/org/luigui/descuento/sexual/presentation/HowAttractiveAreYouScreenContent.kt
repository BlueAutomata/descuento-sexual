package org.luigui.descuento.sexual.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.luigui.descuento.sexual.viewmodels.SexualDiscountViewModel


@Composable
fun HowAttractiveAreYouScreenContent(
    viewModel: SexualDiscountViewModel,
    onNavigateToNextScreen: () -> Unit
) {
    MaterialTheme {
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
                    text = "Registrar Participante",
                    style = androidx.compose.material3.MaterialTheme.typography.headlineLarge,
                )

                Text(
                    text = "Iniciales del primer nombre, primer y segundo apellido; seguido de los 3 últimos dígitos del documento de identidad. Ej: Ana Pérez Gil – APG037",
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                        .padding(16.dp),
                    style = androidx.compose.material3.MaterialTheme.typography.headlineSmall,
                    textAlign = TextAlign.Start
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Button(
                        onClick = {
                            onNavigateToNextScreen()
                        }
                    ) {
                        Text(
                            text = "Siguiente",
                            style = androidx.compose.material3.MaterialTheme.typography.bodyLarge
                        )
                    }
                }
            }

        }
    }
}