package org.luigui.descuento.sexual.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.luigui.descuento.sexual.presentation.RegisterParticipantScreenContent
import org.luigui.descuento.sexual.viewmodels.SexualDiscountViewModel

class RegisterParticipantScreen(
    private val viewModel: SexualDiscountViewModel
): Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        RegisterParticipantScreenContent(
            viewModel = viewModel,
            onNavigateToNextScreen = {
                navigator?.push(InstructionScreen(viewModel))
            }
        )
    }

}