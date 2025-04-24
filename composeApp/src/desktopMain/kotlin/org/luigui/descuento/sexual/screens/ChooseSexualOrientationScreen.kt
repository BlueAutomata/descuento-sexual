package org.luigui.descuento.sexual.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.luigui.descuento.sexual.presentation.ChooseSexualOrientationContent
import org.luigui.descuento.sexual.viewmodels.SexualDiscountViewModel

class ChooseSexualOrientationScreen(
    private val viewModel: SexualDiscountViewModel
): Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        ChooseSexualOrientationContent(
            viewModel = viewModel,
            onNavigateToNextScreen = {
                navigator?.push(ChoosePhotoScreen(viewModel))
            }
        )
    }
}