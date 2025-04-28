package org.luigui.descuento.sexual.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.luigui.descuento.sexual.viewmodels.SexualDiscountViewModel
import kotlin.math.roundToInt

@Composable
fun ChooseWaitTimeLikelihoodScreenContent(
    viewModel: SexualDiscountViewModel,
    onNavigateToNextScreen: () -> Unit
) {
    val selectedPhotoWaitTimePhase by viewModel.selectedPhotoWaitTimePhase.collectAsState()
    val selectWaitingTimeProbabilityPhase by viewModel.selectWaitingTimeProbabilityPhase.collectAsState()

    var rating by remember { mutableStateOf(3) }

    val allowedValues = listOf(0, 2, 3, 4, 5, 7, 10)
    val valueDescriptions = mapOf(
        0 to "Definitivamente tendría relaciones sexuales sin condón",
        2 to "Muy probablemente tendría sexo sin condón",
        3 to "", // Empty string for 3 (not displayed)
        4 to "Probablemente tendría sexo sin condón",
        5 to "No estoy seguro/a de si esperaría o no",
        7 to "Probablemente esperaría para usar condón",
        10 to "Definitivamente esperaría para tener relaciones sexuales con condón"
    )

    var sliderPosition by remember {
        mutableStateOf(allowedValues.indexOf(3).toFloat())
    }

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            SexualDesirabilityText(selectedPhotoWaitTimePhase)
            QuestionText(selectWaitingTimeProbabilityPhase)

            Spacer(
                modifier = Modifier.weight(1f)
            )

            Box(
                modifier = Modifier
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

            // 0-10 Rating Scale
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(horizontal = 32.dp)
            ) {
                Slider(
                    value = sliderPosition,
                    onValueChange = { newPosition ->
                        sliderPosition = newPosition
                        rating = allowedValues[newPosition.roundToInt()]
                    },
                    valueRange = 0f..(allowedValues.size - 1).toFloat(),
                    steps = allowedValues.size - 2,
                    modifier = Modifier.fillMaxWidth()
                )

                // Selected value (or "Nothing selected" placeholder)
                Text(
                    text = if (rating == 3) "Selecciona una opción" else "Seleccionado: $rating",
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontWeight = FontWeight.Bold,
                        color = if (rating == 3) MaterialTheme.colorScheme.error
                        else MaterialTheme.colorScheme.primary
                    ),
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )

                // Short summary (0 = No protection, 10 = Protection)
                Text(
                    text = "(Sin protección | Esperaría por protección)",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .align(Alignment.CenterHorizontally),
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Persistent warning (always visible, but only red when unselected)
                Text(
                    text = "⚠️ Por favor, selecciona una opción",
                    color = if (rating == 3) MaterialTheme.colorScheme.error
                    else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    fontWeight = if (rating == 3) FontWeight.Bold else FontWeight.Normal,
                    modifier = Modifier.padding(bottom = 8.dp),
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, fontSize = 18.sp)
                )

                // Value descriptions - filter out empty descriptions
                Column {
                    valueDescriptions.filter { it.value.isNotEmpty() }.forEach { (value, description) ->
                        Text(
                            text = description,
                            style = if (value == rating) {
                                MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold, fontSize = 18.sp)
                            } else {
                                MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp)
                            },
                            modifier = Modifier.padding(vertical = 4.dp),
                            color = if (value == rating) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = {
                    viewModel.setRating(rating)
                    viewModel.saveMeasurement()

                    if (selectWaitingTimeProbabilityPhase == 7 &&
                        selectedPhotoWaitTimePhase == 4) {
                        onNavigateToNextScreen()
                    }
                    else if (selectWaitingTimeProbabilityPhase == 7) {
                        viewModel.resetWaitingTimePhase()
                        viewModel.updatePhotoWaitTimePhase()
                    }
                    else {
                        viewModel.updateWaitingTimePhase()
                    }
                    rating = 3
                    sliderPosition = allowedValues.indexOf(3).toFloat()
                },
                enabled = rating != 3,
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = "Siguiente",
                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp)
                )
            }
        }
    }
}

@Composable
private fun SexualDesirabilityText(phase: Int) {
    val (prefix, highlightedText, suffix) = when (phase) {
        1 -> Triple(
            "La persona con la que ",
            "más",
            " desearía tener relaciones sexuales"
        )
        2 -> Triple(
            "La persona con la que ",
            "menos",
            " desearía tener relaciones sexuales"
        )
        3 -> Triple(
            "La persona que cree ",
            "más probable",
            " que tenga una infección de transmisión sexual"
        )
        4 -> Triple(
            "La persona que cree ",
            "menos probable",
            " que tenga una infección de transmisión sexual"
        )
        else -> return
    }

    Text(
        modifier = Modifier.padding(16.dp),
        text = buildAnnotatedString {
            append(prefix)
            withStyle(
                style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
            ) {
                append(highlightedText)
            }
            append(suffix)
        },
        style = MaterialTheme.typography.headlineLarge,
    )
}

@Composable
private fun QuestionText(phase: Int) {
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
                style = SpanStyle(
                    fontWeight = FontWeight.Bold,
                    color = Color.Red
                )
            ) {
                append(timeString)
            }
            append(" para tener sexo con protección?")
        },
        style = MaterialTheme.typography.headlineLarge,
    )
}