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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import org.luigui.descuento.sexual.viewmodels.SexualDiscountViewModel

@Composable
fun ChooseWaitTimeLikelihoodScreenContent(
    viewModel: SexualDiscountViewModel,
    onNavigateToNextScreen: () -> Unit
) {
    var rating by remember { mutableStateOf(5) }
    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "¿Cuál es la probabilidad de que espere 1 hora para tener sexo con protección?",
                style = MaterialTheme.typography.headlineLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(
                modifier = Modifier.weight(1f)
            )

            Box(
                modifier = Modifier
                    .height(400.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                Image(
                    painter = painterResource("images/placeholder_woman_1.png"),
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
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            if (rating != 5) {
                Button(
                    onClick = {
                        //viewModel.setWaitTimeLikelihood(rating)
                        onNavigateToNextScreen()
                    },
                    modifier = Modifier.align(Alignment.End)
                ) {
                    Text(text = "Siguiente")
                }
            }
        }
    }
}