package org.luigui.descuento.sexual

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "descuento-sexual",
    ) {
        App()
    }
}