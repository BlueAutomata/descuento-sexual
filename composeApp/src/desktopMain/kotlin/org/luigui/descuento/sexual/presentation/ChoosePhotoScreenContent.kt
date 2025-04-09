package org.luigui.descuento.sexual.presentation

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
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
                        },
                        enabled = viewModel.isPhotoSelected()
                    ) {
                        Text(
                            text = "Siguiente",
                            style = MaterialTheme.typography.bodyLarge,

                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RendererPhotos(
    viewModel: SexualDiscountViewModel,
    photoPlaceholders: List<String>
) {
    val currentPhase by viewModel.selectPhotoPhase.collectAsState()

    // Get photos based on orientation
    val photos = remember(viewModel.sexualBehavior.value) {
        if (viewModel.sexualBehavior.value == SexualBehavior.HETEROSEXUAL) {
            (1..30).map { "man_$it" }
        } else {
            (1..30).map { "woman_$it" }
        }
    }

    // Repeat photos to fill grid
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
        items(repeatedPhotos.size) { index ->
            val photoName = repeatedPhotos[index]
            val isSelected by remember(photoName, currentPhase) {
                derivedStateOf { viewModel.isPhotoSelected(photoName) }
            }

            PhotoItem(
                photoName = photoName,
                viewModel = viewModel,
                modifier = Modifier.aspectRatio(1f),
                photoPlaceholder = photoPlaceholders[index % photoPlaceholders.size],
                isSelected = isSelected
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PhotoItem(
    photoName: String,
    viewModel: SexualDiscountViewModel,
    modifier: Modifier = Modifier,
    photoPlaceholder: String,
    isSelected: Boolean
) {
    // State to track hover
    var isHovered by remember { mutableStateOf(false) }

    // Animation for smooth scaling
    val scale by animateFloatAsState(
        targetValue = if (isHovered) 1.25f else 1f,
        animationSpec = tween(durationMillis = 150)
    )

    val imagePath = remember(photoName, photoPlaceholder) {
        viewModel.getPhotoPath(photoName) ?: photoPlaceholder
    }

    // Enhanced selection styling
    val borderColor = if (isSelected) Color.Blue else Color.Transparent
    val borderWidth = if (isSelected) 4.dp else 0.dp
    val overlayColor = if (isSelected) Color.Blue.copy(alpha = 0.3f) else Color.Transparent

    // Combined hover and selection elevation
    val elevation = when {
        isSelected && isHovered -> 12.dp
        isSelected -> 8.dp
        isHovered -> 6.dp
        else -> 2.dp
    }

    Card(
        modifier = modifier
            .aspectRatio(1f)
            .clickable { viewModel.setSelectedPhoto(photoName) }
            .border(borderWidth, borderColor, RoundedCornerShape(8.dp))
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .pointerHoverIcon(PointerIcon.Hand) // Changes cursor to hand
            .onPointerEvent(PointerEventType.Enter) { isHovered = true }
            .onPointerEvent(PointerEventType.Exit) { isHovered = false }
            .zIndex(if (isHovered) 1f else 0f), // Bring hovered item to front
        elevation = CardDefaults.cardElevation(elevation)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(imagePath),
                contentDescription = "Photo $photoName",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

            if (isSelected) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(overlayColor)
                )
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Selected",
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(4.dp)
                        .size(24.dp),
                    tint = Color.White
                )
            }

            // Optional: Add subtle shadow when hovered
            if (isHovered && !isSelected) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.05f))
                )
            }
        }
    }
}