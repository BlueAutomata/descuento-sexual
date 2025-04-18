package org.luigui.descuento.sexual.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import org.luigui.descuento.sexual.viewmodels.SexualDiscountViewModel

@Composable
fun ChooseWaitTimeLikelihoodScreenContent(
    viewModel: SexualDiscountViewModel,
    onNavigateToNextScreen: () -> Unit
) {
    val selectedPhotoWaitTimePhase by viewModel.selectedPhotoWaitTimePhase.collectAsState()
    val selectWaitingTimeProbabilityPhase by viewModel.selectWaitingTimeProbabilityPhase.collectAsState()

    var rating by remember { mutableStateOf(5) }
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
                    value = rating.toFloat(),
                    onValueChange = { rating = it.toInt() },
                    valueRange = 0f..10f,
                    steps = 1,
                    modifier = Modifier.fillMaxWidth()
                )

                // Scale Labels
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "0", style = MaterialTheme.typography.bodyLarge)
                    Text(text = "10", style = MaterialTheme.typography.bodyLarge)
                }

                Text(
                    text = "(0 = Sin protección | 10 = Esperaría por protección)",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            if (rating != 5) {
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
                        rating = 5
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = "Siguiente")
                }
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
            " que tenga una ETS"
        )
        4 -> Triple(
            "La persona que cree ",
            "menos probable",
            " que tenga una ETS"
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