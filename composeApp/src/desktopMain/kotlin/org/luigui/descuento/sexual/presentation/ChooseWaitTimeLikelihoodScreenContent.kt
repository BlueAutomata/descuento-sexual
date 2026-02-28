package org.luigui.descuento.sexual.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import org.luigui.descuento.sexual.viewmodels.SexualDiscountViewModel
import kotlin.math.roundToInt

@Composable
fun ChooseWaitTimeLikelihoodScreenContent(
    viewModel: SexualDiscountViewModel,
    onNavigateToNextScreen: () -> Unit
) {
    val selectedPhotoWaitTimePhase by viewModel.selectedPhotoWaitTimePhase.collectAsState()
    val selectWaitingTimeProbabilityPhase by viewModel.selectWaitingTimeProbabilityPhase.collectAsState()

    var rating by remember { mutableStateOf(0) }
    val allowedValues = listOf(0, 1, 2, 3, 4, 5, 6)
    val valueDescriptions = mapOf(
        1 to "Definitivamente tendría relaciones sexuales sin condón",
        2 to "Muy probablemente tendría sexo sin condón",
        3 to "Probablemente tendría sexo sin condón",
        4 to "No estoy segura de si esperaría o no",
        5 to "Probablemente esperaría para usar condón",
        6 to "Definitivamente esperaría para tener relaciones sexuales con condón"
    )
    var sliderPosition by remember { mutableStateOf(0f) }

    val scrollState = rememberScrollState()

    MaterialTheme {
        BoxWithConstraints {
            val screenWidth = maxWidth
            val screenHeight = maxHeight
            val isCompact = screenWidth < 600.dp
            val isShortHeight = screenHeight < 900.dp

            val bodyStyle = when {
                screenWidth < 1280.dp -> MaterialTheme.typography.bodyLarge
                screenWidth < 1600.dp -> MaterialTheme.typography.bodyLarge
                else -> MaterialTheme.typography.bodyLarge
            }

            val headlineStyle = when {
                screenWidth < 1280.dp -> MaterialTheme.typography.headlineMedium
                screenWidth < 1600.dp -> MaterialTheme.typography.headlineMedium
                else -> MaterialTheme.typography.headlineLarge
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(if (isCompact) 12.dp else 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(if (isCompact) 12.dp else 20.dp)
            ) {
                SexualDesirabilityText(selectedPhotoWaitTimePhase, headlineStyle)
                QuestionText(selectWaitingTimeProbabilityPhase, headlineStyle)

                Spacer(modifier = Modifier.height(8.dp))

                if (isShortHeight) {
                    // Horizontal layout for short height screens
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .height(400.dp)
                                .clip(RoundedCornerShape(8.dp))
                        ) {
                            Image(
                                painter = painterResource(viewModel.getPhasePhoto()),
                                contentDescription = "Illustration",
                                contentScale = ContentScale.Fit,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(top = 4.dp),
                            horizontalAlignment = Alignment.Start
                        ) {
                            Text(
                                text = if (sliderPosition < 0) "⚠️ Por favor, selecciona una opción" else "Seleccionado: $rating",
                                style = headlineStyle.copy(
                                    fontWeight = FontWeight.Bold,
                                    color = if (sliderPosition < 0) MaterialTheme.colorScheme.error
                                    else MaterialTheme.colorScheme.primary
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(bottom = 8.dp),
                                textAlign = TextAlign.Center
                            )

                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text("Sin protección", style = bodyStyle)

                                Slider(
                                    value = sliderPosition.coerceAtLeast(0f),
                                    onValueChange = { newPosition ->
                                        sliderPosition = newPosition
                                        if (newPosition >= 0) {
                                            rating = allowedValues[newPosition.roundToInt().coerceIn(0, allowedValues.size - 1)]
                                        }
                                        else {
                                            rating = 0
                                        }
                                    },
                                    valueRange = -0.5f..(allowedValues.size - 1).toFloat(),
                                    steps = allowedValues.size - 2,
                                    modifier = Modifier
                                        .weight(1f)
                                        .padding(horizontal = 8.dp)
                                )

                                Text("Esperaría por protección", style = bodyStyle)
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            valueDescriptions.forEach { (value, description) ->
                                Text(
                                    text = description,
                                    style = if (sliderPosition >= 0 && value == rating) {
                                        bodyStyle.copy(fontWeight = FontWeight.Bold)
                                    } else {
                                        bodyStyle
                                    },
                                    modifier = Modifier.padding(vertical = 4.dp),
                                    color = if (sliderPosition >= 0 && value == rating) {
                                        MaterialTheme.colorScheme.primary
                                    } else {
                                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                    }
                                )
                            }
                        }
                    }
                } else {
                    // Vertical layout for regular screens
                    Box(
                        modifier = Modifier
                            .height(350.dp)
                            .clip(RoundedCornerShape(8.dp))
                    ) {
                        Image(
                            painter = painterResource(viewModel.getPhasePhoto()),
                            contentDescription = "Illustration",
                            contentScale = ContentScale.FillHeight,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth(if (isCompact) 0.9f else 0.6f)
                            .padding(horizontal = if (isCompact) 16.dp else 32.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "Sin protección",
                                style = bodyStyle
                            )

                            Slider(
                                value = sliderPosition.coerceAtLeast(0f),
                                onValueChange = { newPosition ->
                                    sliderPosition = newPosition
                                    if (newPosition >= 0) {
                                        rating = allowedValues[newPosition.roundToInt().coerceIn(0, allowedValues.size - 1)]
                                    }
                                    else {
                                        rating = 0
                                    }
                                },
                                valueRange = -0.5f..(allowedValues.size - 1).toFloat(),
                                steps = allowedValues.size - 2,
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(horizontal = 8.dp)
                            )

                            Text(
                                text = "Esperaría por protección",
                                style = bodyStyle
                            )
                        }

                        Text(
                            text = if (sliderPosition < 0) "⚠️ Por favor, selecciona una opción" else "Seleccionado: $rating",
                            style = headlineStyle.copy(
                                fontWeight = FontWeight.Bold,
                                color = if (sliderPosition < 0) MaterialTheme.colorScheme.error
                                else MaterialTheme.colorScheme.primary
                            ),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        valueDescriptions.forEach { (value, description) ->
                            Text(
                                text = description,
                                style = if (sliderPosition >= 0 && value == rating) {
                                    bodyStyle.copy(fontWeight = FontWeight.Bold)
                                } else {
                                    bodyStyle
                                },
                                modifier = Modifier.padding(vertical = 4.dp),
                                color = if (sliderPosition >= 0 && value == rating) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))


            }

            Button(
                onClick = {
                    viewModel.setRating(rating)
                    viewModel.saveMeasurement()

                    if (selectWaitingTimeProbabilityPhase == 7 && selectedPhotoWaitTimePhase == 4) {
                        onNavigateToNextScreen()
                    } else if (selectWaitingTimeProbabilityPhase == 7) {
                        viewModel.resetWaitingTimePhase()
                        viewModel.updatePhotoWaitTimePhase()
                    } else {
                        viewModel.updateWaitingTimePhase()
                    }

                    rating = 0
                    sliderPosition = allowedValues.indexOf(0).toFloat()
                },
                enabled = rating != 0,
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.BottomEnd)
            ) {
                Text("Siguiente", style = bodyStyle)
            }
        }
    }
}



@Composable
private fun SexualDesirabilityText(phase: Int, textStyle: TextStyle) {
    val (prefix, highlightedText, suffix) = when (phase) {
        1 -> Triple("La persona con la que ", "más", " desearía tener relaciones sexuales")
        2 -> Triple("La persona con la que ", "menos", " desearía tener relaciones sexuales")
        3 -> Triple("La persona que cree ", "más probable", " que tenga una infección de transmisión sexual")
        4 -> Triple("La persona que cree ", "menos probable", " que tenga una infección de transmisión sexual")
        else -> return
    }

    Text(
        modifier = Modifier.padding(16.dp),
        text = buildAnnotatedString {
            append(prefix)
            withStyle(
                style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Red)
            ) {
                append(highlightedText)
            }
            append(suffix)
        },
        style = textStyle
    )
}

@Composable
private fun QuestionText(phase: Int, textStyle: TextStyle) {
    val timeString = when (phase) {
        1 -> "1 hora"
        2 -> "3 horas"
        3 -> "6 horas"
        4 -> "1 día"
        5 -> "1 semana"
        6 -> "1 mes"
        7 -> "3 meses"
        else -> return
    }

    Text(
        modifier = Modifier.padding(16.dp),
        text = buildAnnotatedString {
            append("¿Cuál es la probabilidad de que espere ")
            withStyle(
                style = SpanStyle(fontWeight = FontWeight.Bold, color = Color.Red)
            ) {
                append(timeString)
            }
            append(" para tener sexo con protección?")
        },
        style = textStyle
    )
}




