package org.luigui.descuento.sexual.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.luigui.descuento.sexual.viewmodels.SexualDiscountViewModel
import kotlin.math.roundToInt


@Composable
fun HowAttractiveAreYouScreenContent(
    viewModel: SexualDiscountViewModel,
    onNavigateToNextScreen: () -> Unit
) {
    var rating by remember { mutableStateOf(5) }
    var sliderValue by remember { mutableStateOf(rating.toFloat()) }
    var hasUserInteracted by remember { mutableStateOf(false) }

    MaterialTheme {
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            val screenWidth = maxWidth

            // Responsive layout widths
            val contentMaxWidth = when {
                screenWidth < 600.dp -> screenWidth * 0.9f
                screenWidth < 900.dp -> screenWidth * 0.8f
                else -> screenWidth * 0.7f
            }

            // Responsive text sizes
            val baseFontSize = when {
                screenWidth < 600.dp -> 18.sp
                screenWidth < 900.dp -> 20.sp
                else -> 22.sp
            }

            val titleFontSize = when {
                screenWidth < 600.dp -> 26.sp
                screenWidth < 900.dp -> 30.sp
                else -> 34.sp
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "¿Qué tan atractiva se considera?",
                    style = MaterialTheme.typography.headlineLarge.copy(fontSize = titleFontSize),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .widthIn(max = contentMaxWidth)
                        .padding(bottom = 16.dp)
                ) {
                    TextBlock(
                        "A continuación, verás una barra deslizante que va del 0 al 10. Esta barra sirve para indicar qué tan atractiva te consideras a ti misma.",
                        baseFontSize
                    )

                    TextBlock("El número 0 significa que no te consideras nada atractiva.", baseFontSize)
                    TextBlock("El número 10 significa que te consideras muy atractiva.", baseFontSize)
                    TextBlock("Por favor, desliza la barra hasta el número que mejor refleje cómo te sientes con respecto a tu propia apariencia física en este momento.", baseFontSize)
                    TextBlock("No hay respuestas correctas o incorrectas. Elige el número que mejor exprese tu opinión personal.", baseFontSize)
                }

                Column(
                    modifier = Modifier
                        .widthIn(max = contentMaxWidth)
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = if (hasUserInteracted) "\nCalificación: ${sliderValue.roundToInt()}" else "\nDesliza la barra: ${sliderValue.roundToInt()}",
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = baseFontSize),
                    )

                    Slider(
                        value = if (hasUserInteracted) sliderValue else 5f,
                        onValueChange = {
                            sliderValue = it
                            hasUserInteracted = true
                        },
                        onValueChangeFinished = {
                            rating = sliderValue.roundToInt()
                        },
                        valueRange = 0f..10f,
                        steps = 9,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("0", style = MaterialTheme.typography.bodyLarge.copy(fontSize = baseFontSize))
                        Text("10", style = MaterialTheme.typography.bodyLarge.copy(fontSize = baseFontSize))
                    }

                    Text(
                        text = "(0 = Poco atractiva | 10 = Altamente atractiva)",
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = baseFontSize),
                        modifier = Modifier.padding(top = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Button(
                        onClick = {
                            viewModel.setAttractiveness(rating)
                            onNavigateToNextScreen()
                        },
                        enabled = hasUserInteracted
                    ) {
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
private fun TextBlock(text: String, fontSize: TextUnit) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodyLarge.copy(fontSize = fontSize),
        modifier = Modifier.padding(bottom = 12.dp)
    )
}
