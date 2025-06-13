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
        state = rememberWindowState(size = DpSize(1280.dp, 720.dp)).apply {
            //placement = WindowPlacement.Maximized
                                            //  //   WindowPlacement.Fullscreen
        },
        undecorated = false // Optional: removes window decorations for true fullscreen
    ) {
        App()
    }
}