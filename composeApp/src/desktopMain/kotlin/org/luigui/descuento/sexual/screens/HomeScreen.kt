package org.luigui.descuento.sexual.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.luigui.descuento.sexual.presentation.HomeScreenContent
import org.luigui.descuento.sexual.viewmodels.SexualDiscountViewModel

class HomeScreen(
    private val viewModel: SexualDiscountViewModel
): Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        HomeScreenContent(
            viewModel = viewModel,
            onNavigateToNextScreen = {

            }
        )
    }
}