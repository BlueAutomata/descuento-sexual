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
                    text = "Instrucciones",
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
                            text = "A continuación, se te presentarán varias fotografías.",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 24.sp),
                            modifier = Modifier.padding(bottom = 16.dp)
                        )

                        Text(
                            text = "\nDeberás seleccionar una imagen distinta para cada una de las siguientes condiciones:",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 24.sp),
                            modifier = Modifier.padding(bottom = 4.dp)
                        )

                        // Bullet point items
                        val bulletPoints = listOf(
                            "La persona con la que más te gustaría tener relaciones sexuales.",
                            "La persona con la que menos te gustaría tener relaciones sexuales.",
                            "La persona que crees que tiene mayor probabilidad de tener una infección de transmisión sexual (ITS).",
                            "La persona que crees que tiene menor probabilidad de tener una ITS."
                        )

                        bulletPoints.forEach { point ->
                            Text(
                                text = "• $point",
                                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 24.sp),
                                modifier = Modifier
                                    .padding(start = 16.dp, bottom = 4.dp)
                                    .fillMaxWidth()
                            )
                        }

                        Text(
                            text = "\nDespués de seleccionar una imagen para cada condición, verás una barra deslizante asociada a esa imagen.",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 24.sp),
                            modifier = Modifier.padding(bottom = 4.dp)
                        )

                        Text(
                            text = "\nEsta barra representa tu disposición a esperar para tener relaciones sexuales con esa persona sin protección:",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 24.sp),
                            modifier = Modifier.padding(bottom = 4.dp)
                        )

                        val bulletPoints2 = listOf(
                            "El extremo izquierdo indica: \"definitivamente esperaría\".",
                            "Puedes mover la barra hacia la derecha si estuvieras menos dispuesta a esperar.",
                            "Selecciona el punto en la barra que refleje con mayor precisión tu disposición en este momento.",
                            "Haz clic en \"Siguiente\" una vez hayas seleccionado la imagen y ajustado la barra para continuar con la siguiente condición.",
                            "Este procedimiento se repetirá cuatro veces, una por cada condición."
                        )

                        bulletPoints2.forEach { point ->
                            Text(
                                text = "• $point",
                                style = MaterialTheme.typography.bodyLarge.copy(fontSize = 24.sp),
                                modifier = Modifier
                                    .padding(start = 16.dp, bottom = 4.dp)
                                    .fillMaxWidth()
                            )
                        }

                        Text(
                            text = "\nNo hay respuestas correctas o incorrectas. Te invitamos a responder de forma honesta y reflexiva, basándote en lo que piensas y sientes actualmente.",
                            style = MaterialTheme.typography.bodyLarge.copy(fontSize = 24.sp),
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