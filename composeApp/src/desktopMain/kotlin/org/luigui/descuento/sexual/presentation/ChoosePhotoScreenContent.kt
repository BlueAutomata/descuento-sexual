package org.luigui.descuento.sexual.presentation

import androidx.compose.foundation.Image

import androidx.compose.foundation.clickable
import androidx.compose.ui.res.painterResource

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import org.luigui.descuento.sexual.data.SexualBehavior
import org.luigui.descuento.sexual.viewmodels.SexualDiscountViewModel
import kotlin.math.ceil

@Composable
fun ChoosePhotoScreenContent(
    viewModel: SexualDiscountViewModel,
    onNavigateToNextScreen: () -> Unit
) {
    val selectPhotoPhase by viewModel.selectPhotoPhase.collectAsState()
    val photoPlaceholders by viewModel.photoPlaceholders.collectAsState()

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
                when (selectPhotoPhase) {
                    1 -> {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = buildAnnotatedString {
                                append("Seleccione la persona con la que ")
                                withStyle(style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Red
                                )) {
                                    append("más")
                                }
                                append(" desearía tener relaciones sexuales.")
                            },
                            style = MaterialTheme.typography.headlineLarge,
                        )
                    }
                    2 -> {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = buildAnnotatedString {
                                append("Seleccione la persona con la que ")
                                withStyle(style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Red
                                )) {
                                    append("menos")
                                }
                                append(" desearía tener relaciones sexuales.")
                            },
                            style = MaterialTheme.typography.headlineLarge,
                        )
                    }
                    3 -> {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = buildAnnotatedString {
                                append("Seleccione la persona que cree ")
                                withStyle(style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Red
                                )) {
                                    append("más problable")
                                }
                                append(" que tenga una ITS.")
                            },
                            style = MaterialTheme.typography.headlineLarge,
                        )
                    }
                    4 -> {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = buildAnnotatedString {
                                append("Seleccione la persona que cree ")
                                withStyle(style = SpanStyle(
                                    fontWeight = FontWeight.Bold,
                                    color = Color.Red
                                )) {
                                    append("menos problable")
                                }
                                append(" que tenga una ITS")
                            },
                            style = MaterialTheme.typography.headlineLarge,
                        )
                    }
                }

                BoxWithConstraints(
                    modifier = Modifier.fillMaxSize(0.6f).weight(1f)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        RendererPhotos(
                            viewModel = viewModel,
                            photoPlaceholders = photoPlaceholders
                        )
                    }
                }

                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.BottomEnd
                ) {
                    Button(
                        onClick = {
                            if (selectPhotoPhase == 4) {
                                onNavigateToNextScreen()
                            }
                            else {
                                viewModel.getRandomPlaceholders()
                                viewModel.updatePhotoPhase()
                            }
                        }
                    ) {
                        Text(text = "Siguiente", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}

@Composable
fun RendererPhotos(viewModel: SexualDiscountViewModel, photoPlaceholders: List<String>) {
    // val photos = remember { (1..30).map { "placeholder_man_$it" } }

    // Get all available photos based on orientation
    val photos = remember(viewModel.sexualBehavior.value) {
        if (viewModel.sexualBehavior.value == SexualBehavior.HETEROSEXUAL) {
            (1..30).map { "man_$it" } // Male placeholders
        } else {
            (1..30).map { "woman_$it" } // Female placeholders
        }
    }

    // Calculate how many times we need to repeat the list to fill the grid (6x5 = 30 items)
    val repeatedPhotos = remember(photos) {
        val repeatCount = ceil(30f / photos.size).toInt()
        List(repeatCount) { photos }.flatten().take(30)
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(6),
        modifier = Modifier.fillMaxHeight(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(photos.size) { index ->
            PhotoItem(
                photoName = repeatedPhotos[index],
                viewModel = viewModel,
                modifier = Modifier.aspectRatio(1f),
                photoPlaceholder = photoPlaceholders[index % photoPlaceholders.size]
            )
        }
    }
}

@Composable
fun PhotoItem(
    photoName: String,
    viewModel: SexualDiscountViewModel,
    modifier: Modifier = Modifier,
    photoPlaceholder: String
) {
    val imagePath = remember(photoName, photoPlaceholder) {
        viewModel.getPhotoPath(photoName) ?: photoPlaceholder
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