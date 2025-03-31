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
import kotlin.system.exitProcess

@Composable
fun EndScreenContent() {
    MaterialTheme{
        Box(
            modifier = Modifier.fillMaxSize(), // Use fillMaxSize to cover the entire screen
            contentAlignment = Alignment.Center // Center the content
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally, // Center horizontally
                verticalArrangement = Arrangement.Center // Center vertically
            ) {
                Text(
                    text = "¡Gracias por participar!",
                    style = MaterialTheme.typography.headlineLarge.copy(fontSize = 32.sp),
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Button(onClick = { exitProcess(0) }) {
                    Text(
                        text = "Cerrar Programa",
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp)
                    )
                }
            }
        }
    }
}