package org.luigui.descuento.sexual


import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.luigui.descuento.sexual.screens.HomeScreen
import org.luigui.descuento.sexual.viewmodels.SexualDiscountViewModel

@Composable
@Preview
fun App() {
    val viewModel = remember { SexualDiscountViewModel() }
    Navigator(HomeScreen(viewModel))
}