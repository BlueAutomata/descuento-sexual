package org.luigui.descuento.sexual.presentation

import androidx.compose.foundation.Image

import androidx.compose.foundation.clickable
import androidx.compose.ui.res.painterResource

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import org.luigui.descuento.sexual.viewmodels.SexualDiscountViewModel
import java.io.File

@Composable
fun ChoosePhotoScreenContent(
    viewModel: SexualDiscountViewModel,
    onNavigateToNextScreen: () -> Unit
) {
    MaterialTheme {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = "Seleccione la persona con la que más desearía tener relaciones sexuales.",
                    style = MaterialTheme.typography.headlineLarge,
                )


                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth().height(100.dp)
                ) {
                    RendererPhotos(viewModel = viewModel)
                }

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Button(
                        onClick = onNavigateToNextScreen
                    ) {
                        Text(text = "Siguiente", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}

@Composable
fun RendererPhotos(viewModel: SexualDiscountViewModel) {
    val photos = remember { (1..30).map { "placeholder_man_$it" } }

    LazyVerticalGrid(
        columns = GridCells.Fixed(6),
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(photos.size) { index ->
            PhotoItem(
                photoName = photos[index],
                viewModel = viewModel,
                modifier = Modifier.aspectRatio(1f)
            )
        }
    }
}

@Composable
fun PhotoItem(
    photoName: String,
    viewModel: SexualDiscountViewModel,
    modifier: Modifier = Modifier
) {
    val randomPlaceholder = remember {
        val placeholder = getRandomPlaceholder()
        debugResourcePath(placeholder)
        placeholder
    }

    //val randomPlaceholder = remember { getRandomPlaceholder() }
    val imagePath = try {
        print("test")
        randomPlaceholder
        //viewModel.getPhotoPath(photoName) ?: randomPlaceholder
    } catch (e: Exception) {
        print("test2")
        randomPlaceholder
    }

    Card(
        modifier = modifier.clickable { /* Handle photo selection */ },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Image(
            painter = painterResource(imagePath),
            contentDescription = "Photo $photoName",
            contentScale = ContentScale.Fit,
            modifier = Modifier.fillMaxSize()
        )
    }
}

private fun getRandomPlaceholder(): String {
    val placeholders = listOf(
        "images/placeholder_man_1.png",
        "images/placeholder_woman_1.png"
        // Add more placeholders as needed
    )
    return placeholders.random()
}


private fun debugResourcePath(resourceName: String) {
    println("\n=== Resource Debug ===")
    println("Requested resource: $resourceName")

    val classLoader = Thread.currentThread().contextClassLoader

    // Check in regular resources
    val resourceUrl = classLoader.getResource(resourceName)
    println("Resource URL: ${resourceUrl?.toExternalForm() ?: "NOT FOUND"}")

    // Check in compose resources
    val composeResourceUrl = classLoader.getResource("compose-resources/$resourceName")
    println("Compose Resource URL: ${composeResourceUrl?.toExternalForm() ?: "NOT FOUND"}")

    // Check filesystem paths
    val projectDirs = listOf(
        "src/commonMain/composeResources",
        "src/desktopMain/resources",
        "build/composeResources"
    )

    projectDirs.forEach { dir ->
        val file = File("$dir/$resourceName")
        println("Filesystem path: ${file.absolutePath} - Exists: ${file.exists()}")
    }
    println("===================\n")
}