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
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.luigui.descuento.sexual.viewmodels.SexualDiscountViewModel

@Composable
fun ChooseSexualOrientationContent(
    viewModel: SexualDiscountViewModel,
    onNavigateToNextScreen: () -> Unit
) {
    var selectedIndex by remember { mutableIntStateOf(-1) }
    val options = listOf("Tengo sexo con hombres.", "Tengo sexo con mujeres.")

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
            ) {
                Text(
                    text = "Seleccione su comportamiento sexual",
                    style = MaterialTheme.typography.headlineLarge,
                )

                Spacer(
                    modifier = Modifier.weight(1f)
                )

                SingleChoiceSegmentedButtonRow(
                    modifier = Modifier.fillMaxWidth(0.75f)
                ) {
                    options.forEachIndexed { index, label ->
                        SegmentedButton(
                            shape = SegmentedButtonDefaults.itemShape(
                                index = index,
                                count = options.size
                            ),
                            onClick = { selectedIndex = index },
                            selected = index == selectedIndex,
                            label = {
                                Text(
                                    label,
                                    style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                                    maxLines = 1,
                                    textAlign = TextAlign.Center
                                )
                            }
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.weight(1f)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    if (selectedIndex >= 0) {
                        Button(
                            onClick = {
                                viewModel.setSexualBehavior(selectedIndex)
                                viewModel.getRandomPlaceholders()
                                onNavigateToNextScreen()
                            }
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
}