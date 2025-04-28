package org.luigui.descuento.sexual.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.luigui.descuento.sexual.viewmodels.SexualDiscountViewModel

@Composable
fun InstructionScreenContent(
    viewModel: SexualDiscountViewModel,  // Consider renaming to more appropriate name
    onNavigateToNextScreen: () -> Unit,
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
            ){
                Text(
                    modifier = Modifier.padding(16.dp),
                    text = "INSTRUCCIONES",
                    style = MaterialTheme.typography.headlineLarge,
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.6f)
                        .padding(16.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "A continuación, encontrarás una serie de situaciones hipotéticas relacionadas con decisiones personales. En cada una, se te pedirá que indiques, usando una escala del 0 al 10, la probabilidad de tomar una decisión en función de una demora en el acceso a un recurso.",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 30.sp),
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        Text(
                            text = "Recuerda:",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight.Bold,
                                fontSize = 30.sp
                            ),
                            modifier = Modifier.padding(bottom = 4.dp)
                        )

                        // Bullet point items
                        val bulletPoints = listOf(
                            "No hay respuestas correctas o incorrectas.",
                            "Por favor, responde de forma honesta según lo que harías en esa situación.",
                            "Toda la información es anónima y confidencial."
                        )

                        bulletPoints.forEach { point ->
                            Text(
                                text = "• $point",
                                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 30.sp),
                                modifier = Modifier
                                    .padding(start = 16.dp, bottom = 4.dp)
                                    .fillMaxWidth()
                            )
                        }

                        Text(
                            text = "Cuando estés lista, puedes comenzar.",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 30.sp),
                            modifier = Modifier.padding(top = 8.dp)
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
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp)
                        )
                    }
                }
            }
        }
    }
}