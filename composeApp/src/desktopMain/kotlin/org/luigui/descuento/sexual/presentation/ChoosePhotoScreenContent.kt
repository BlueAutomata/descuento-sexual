package org.luigui.descuento.sexual.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.PointerIcon
import androidx.compose.ui.input.pointer.onPointerEvent
import androidx.compose.ui.input.pointer.pointerHoverIcon
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.times
import androidx.compose.ui.zIndex
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            // Top text
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

            // Photos grid - now using a regular grid
            Box(
                modifier = Modifier.weight(1f, fill = true),
                contentAlignment = Alignment.Center
            ) {
                RendererPhotos(
                    viewModel = viewModel,
                    photoPlaceholders = photoPlaceholders
                )
            }

            // Button at the bottom
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
                        viewModel.resetSelectedPhoto()
                    },
                    enabled = viewModel.isPhotoSelected()
                ) {
                    Text(
                        text = "Siguiente",
                        style = MaterialTheme.typography.bodyLarge.copy(fontSize = 18.sp),
                    )
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
    val selectedPhoto by viewModel.selectedPhoto.collectAsState()
    val photos = remember(viewModel.sexualBehavior.value) {
        if (viewModel.sexualBehavior.value == SexualBehavior.HETEROSEXUAL) {
            (1..30).map { "man_$it" }
        } else {
            (1..30).map { "woman_$it" }
        }
    }

    val repeatedPhotos = remember(photos) {
        val repeatCount = ceil(30f / photos.size).toInt()
        List(repeatCount) { photos }.flatten().take(30)
    }

    // Fixed grid layout (6 columns x 5 rows)
    val columns = 6
    val rows = 5

    // Track hover state separately from selection
    var hoveredPhoto by remember { mutableStateOf<String?>(null) }

    // Determine which photo to show in preview (selected takes priority)
    val previewPhoto = remember(selectedPhoto, hoveredPhoto) {
        print("Selected photos is: ${selectedPhoto.toString()}")
        selectedPhoto ?: hoveredPhoto
    }

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        val boxMaxWidth = maxWidth
        val boxMaxHeight = maxHeight

        // Calculate maximum possible item size that fits
        val maxItemWidth = (boxMaxWidth - (8.dp * (columns - 1))) / columns
        val maxItemHeight = (boxMaxHeight - (8.dp * (rows - 1))) / rows
        val itemSize = min(maxItemWidth, maxItemHeight)

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Main photo grid
            Column(
                modifier = Modifier.weight(0.7f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                repeat(rows) { rowIndex ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        repeat(columns) { columnIndex ->
                            val index = rowIndex * columns + columnIndex
                            if (index < repeatedPhotos.size) {
                                val photoName = repeatedPhotos[index]
                                val isSelected by remember(photoName, currentPhase) {
                                    derivedStateOf { viewModel.isPhotoSelected(photoName) }
                                }

                                Box(
                                    modifier = Modifier
                                        .size(itemSize)
                                        .pointerHoverIcon(PointerIcon.Hand)
                                        .pointerInput(photoName) {
                                            awaitPointerEventScope {
                                                while (true) {
                                                    val event = awaitPointerEvent()
                                                    when (event.type) {
                                                        PointerEventType.Enter -> {
                                                            hoveredPhoto = photoName
                                                        }
                                                        PointerEventType.Exit -> {
                                                            // Only clear hover if it's the current hovered photo
                                                            if (hoveredPhoto == photoName) {
                                                                hoveredPhoto = null
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        .clickable {
                                            viewModel.setSelectedPhoto(photoName)
                                            viewModel.setSelectedPlaceholderPhoto(
                                                photoPlaceholders[index % photoPlaceholders.size]
                                            )
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    PhotoItem(
                                        photoName = photoName,
                                        viewModel = viewModel,
                                        modifier = Modifier.fillMaxSize(),
                                        photoPlaceholder = photoPlaceholders[index % photoPlaceholders.size],
                                        isSelected = isSelected,
                                        showBorder = true
                                    )
                                }
                            } else {
                                // Empty space for incomplete rows
                                Spacer(modifier = Modifier.size(itemSize))
                            }
                        }
                    }
                }
            }

            // Persistent preview area
            Box(
                modifier = Modifier
                    .weight(0.3f)
                    .heightIn(max = boxMaxHeight)
                    .padding(8.dp),
                contentAlignment = Alignment.TopCenter
            ) {
                previewPhoto?.let { photoName ->
                    val index = repeatedPhotos.indexOf(photoName)
                    val imagePath = viewModel.getPhotoPath(photoName)
                        ?: photoPlaceholders[index % photoPlaceholders.size]

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .padding(4.dp),
                        elevation = CardDefaults.cardElevation(4.dp)
                    ) {
                        Image(
                            painter = painterResource(imagePath),
                            contentDescription = "Preview of $photoName",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                } ?: run {
                    // Placeholder when no image is hovered or selected
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .background(Color.LightGray.copy(alpha = 0.3f))
                            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp)),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Pasa el cursor sobre una imagen (o haz clic en ella) para ver la vista previa",
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PhotoItem(
    photoName: String,
    viewModel: SexualDiscountViewModel,
    modifier: Modifier = Modifier,
    photoPlaceholder: String,
    isSelected: Boolean,
    showBorder: Boolean = true
) {
    val imagePath = remember(photoName, photoPlaceholder) {
        viewModel.getPhotoPath(photoName) ?: photoPlaceholder
    }

    // Selection styling
    val borderColor = if (isSelected) Color.Blue else Color.Transparent
    val borderWidth = if (isSelected && showBorder) 4.dp else 0.dp
    val overlayColor = if (isSelected) Color.Blue.copy(alpha = 0.3f) else Color.Transparent

    Card(
        modifier = modifier
            .aspectRatio(1f)
            .border(borderWidth, borderColor, RoundedCornerShape(8.dp)),
        elevation = CardDefaults.cardElevation(if (isSelected) 8.dp else 2.dp)
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
        }
    }
}