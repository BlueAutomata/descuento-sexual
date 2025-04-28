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
                    text = "¿Qué tan atractivo se considera?",
                    style = MaterialTheme.typography.headlineLarge,
                )

                Spacer(
                    modifier = Modifier.weight(1f)
                )

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(horizontal = 32.dp)
                ) {
                    if (!hasUserInteracted) {
                        Text(
                            text ="Desliza para calificar (0-10)",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp)
                        )
                    } else {
                        Text(
                            text = "Calificación: ${sliderValue.roundToInt()}",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp)
                        )
                    }

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

                    // Scale Labels
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "0",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp)
                        )
                        Text(
                            text = "10",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp)
                        )
                    }

                    Text(
                        text = "(0 = Poco atractiva | 10 = Altamente atractiva)",
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 20.sp),
                        modifier = Modifier.padding(top = 8.dp)
                    )
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
                        onClick = {
                            onNavigateToNextScreen()
                        },
                        enabled = hasUserInteracted
                    ) {
                        Text(
                            text = "Siguiente",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp)
                        )
                    }
                }
            }
        }
    }
}