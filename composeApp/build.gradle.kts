import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    jvm("desktop")
    
    sourceSets {
        val desktopMain by getting
        
        commonMain.dependencies {
            implementation(compose.preview)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)

            implementation("cafe.adriel.voyager:voyager-navigator:1.1.0-beta02")
            implementation("cafe.adriel.voyager:voyager-transitions:1.1.0-beta02")

            implementation("org.apache.poi:poi:5.2.3")
            implementation("org.apache.poi:poi-ooxml:5.2.3")

            implementation("org.jetbrains.compose.desktop:desktop-jvm:1.5.10")

            implementation("org.openjfx:javafx-controls:21")
            implementation("org.openjfx:javafx-fxml:21")

            implementation("org.jetbrains.kotlin:kotlin-stdlib:1.9.0")

        }
        desktopMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutines.swing)
            implementation("org.jetbrains.compose.ui:ui-util:1.5.0")
        }
    }
}


compose.desktop {
    application {
        mainClass = "org.luigui.descuento.sexual.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.luigui.descuento.sexual"
            packageVersion = "1.0.0"
        }
    }
}
