package org.luigui.descuento.sexual.screens

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import org.luigui.descuento.sexual.presentation.AddCommentScreenContent
import org.luigui.descuento.sexual.viewmodels.SexualDiscountViewModel

class AddCommentScreen(
    private val viewModel: SexualDiscountViewModel
): Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current

        AddCommentScreenContent(
            viewModel = viewModel,
            onNavigateToNextScreen = {
                navigator?.push(EndScreen())
            }
        )
    }
}