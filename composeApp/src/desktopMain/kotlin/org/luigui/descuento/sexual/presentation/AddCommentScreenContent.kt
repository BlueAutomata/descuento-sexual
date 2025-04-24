package org.luigui.descuento.sexual.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.luigui.descuento.sexual.viewmodels.SexualDiscountViewModel


@Composable
fun AddCommentScreenContent(
    viewModel: SexualDiscountViewModel,  // Consider renaming to more appropriate name
    onNavigateToNextScreen: () -> Unit,
    modifier: Modifier = Modifier
) {
    MaterialTheme {
        var comment by remember { mutableStateOf("")}

        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Header Section
            Text(
                text = "Agregar comentario",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(vertical = 16.dp)
            )

            // Instruction Text
            Text(
                text = "¿Le gustaría dejar algún comentario sobre esta tarea?",
                style = MaterialTheme.typography.headlineMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .padding(bottom = 8.dp)
            )

            // Comment Input Field
            OutlinedTextField(
                value = comment,
                onValueChange = { comment = it },
                label = { Text("Escriba su comentario aquí") },
                placeholder = { Text("Opcional") },
                modifier = Modifier
                    .fillMaxWidth(0.75f)
                    .heightIn(min = 100.dp),
                textStyle = LocalTextStyle.current.copy(fontSize = 20.sp),
                singleLine = false,
                maxLines = 10,
                shape = MaterialTheme.shapes.medium
            )

            // Spacer to push button to bottom
            Spacer(modifier = Modifier.weight(1f))

            // Next Button
            Button(
                onClick = {
                    viewModel.saveComment(comment) // Assuming you have this function
                    viewModel.addCommentToLastRow(comment)
                    onNavigateToNextScreen()
                },
                modifier = Modifier.align(Alignment.End),
                enabled = true
            ) {
                Text(
                    text = "Siguiente",
                )
            }
        }
    }
}