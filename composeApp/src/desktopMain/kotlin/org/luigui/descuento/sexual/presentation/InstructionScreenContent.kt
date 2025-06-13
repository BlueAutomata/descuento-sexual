package org.luigui.descuento.sexual.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.luigui.descuento.sexual.viewmodels.SexualDiscountViewModel

@Composable
fun InstructionScreenContent(
    viewModel: SexualDiscountViewModel, // Rename for clarity if needed
    onNavigateToNextScreen: () -> Unit,
) {
    MaterialTheme {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            val screenWidth = maxWidth
            val contentMaxWidth = when {
                screenWidth < 600.dp -> screenWidth * 0.9f   // 90% for small screens
                screenWidth < 900.dp -> screenWidth * 0.75f   // 80% for medium screens
                else -> screenWidth * 0.6f                   // 70% for large screens
            }

            val baseFontSize = when {
                screenWidth < 600.dp -> 16.sp
                screenWidth < 900.dp -> 18.sp
                else -> 19.sp
            }

            val titleFontSize = when {
                screenWidth < 600.dp -> 22.sp
                screenWidth < 900.dp -> 26.sp
                else -> 28.sp
            }

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Scrollable content
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(rememberScrollState())
                        .padding(horizontal = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Instrucciones",
                        style = MaterialTheme.typography.headlineLarge.copy(fontSize = titleFontSize),
                        modifier = Modifier.padding(vertical = 16.dp)
                    )

                    Column(
                        modifier = Modifier
                            .widthIn(max = contentMaxWidth)
                            .padding(bottom = 16.dp)
                    ) {
                        TextBlock("A continuación, se te presentarán varias fotografías.", baseFontSize)
                        TextBlock("Deberás seleccionar una imagen distinta para cada una de las siguientes condiciones:", baseFontSize)

                        val bulletPoints = listOf(
                            "La persona con la que más te gustaría tener relaciones sexuales.",
                            "La persona con la que menos te gustaría tener relaciones sexuales.",
                            "La persona que crees que tiene mayor probabilidad de tener una infección de transmisión sexual (ITS).",
                            "La persona que crees que tiene menor probabilidad de tener una ITS."
                        )
                        bulletPoints.forEach { BulletPoint(it, baseFontSize) }

                        TextBlock("Después de seleccionar una imagen para cada condición, verás una barra deslizante asociada a esa imagen.", baseFontSize)
                        TextBlock("Esta barra representa tu disposición a esperar para tener relaciones sexuales con esa persona sin protección:", baseFontSize)

                        val bulletPoints2 = listOf(
                            "El extremo izquierdo indica: \"definitivamente esperaría\".",
                            "Puedes mover la barra hacia la derecha si estuvieras menos dispuesta a esperar.",
                            "Selecciona el punto en la barra que refleje con mayor precisión tu disposición en este momento.",
                            "Haz clic en \"Siguiente\" una vez hayas seleccionado la imagen y ajustado la barra para continuar con la siguiente condición.",
                            "Este procedimiento se repetirá cuatro veces, una por cada condición."
                        )
                        bulletPoints2.forEach { BulletPoint(it, baseFontSize) }

                        TextBlock(
                            "No hay respuestas correctas o incorrectas. Te invitamos a responder de forma honesta y reflexiva, basándote en lo que piensas y sientes actualmente.",
                            baseFontSize,
                            topPadding = 8.dp
                        )
                    }
                }

                // Fixed button at the bottom
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 8.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Button(onClick = onNavigateToNextScreen) {
                        Text(
                            text = "Siguiente",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = baseFontSize)
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun TextBlock(text: String, fontSize: TextUnit, topPadding: Dp = 0.dp) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge.copy(
            fontSize = fontSize,
            lineHeight = fontSize * 1.4f
        ),
        modifier = Modifier.padding(top = topPadding, bottom = 10.dp),
        textAlign = TextAlign.Justify
    )
}

@Composable
private fun BulletPoint(text: String, fontSize: TextUnit) {
    Text(
        text = "• $text",
        style = MaterialTheme.typography.bodyLarge.copy(
            fontSize = fontSize,
            lineHeight = fontSize * 1.4f
        ),
        modifier = Modifier
            .padding(start = 16.dp, bottom = 6.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Start
    )
}




