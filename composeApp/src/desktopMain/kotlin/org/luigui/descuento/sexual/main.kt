package org.luigui.descuento.sexual

import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "descuento-sexual",
        state = rememberWindowState(placement = WindowPlacement.Fullscreen).apply {
            //WindowPlacement.Fullscreen
            //placement = WindowPlacement.Fullscreen
            //placement = WindowPlacement.Maximized
                                            //  //   WindowPlacement.Fullscreen size = DpSize(1920.dp, 1080.dp)
        },
        undecorated = true // Optional: removes window decorations for true fullscreen
    ) {
        App()
    }
}