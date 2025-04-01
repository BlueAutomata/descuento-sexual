package org.luigui.descuento.sexual.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import org.luigui.descuento.sexual.data.SexualOrientation

class SexualDiscountViewModel: ViewModel() {
    private val _sexualOrientation = MutableStateFlow<SexualOrientation?>(null)
    val sexualOrientation: MutableStateFlow<SexualOrientation?> = _sexualOrientation

    fun setSexualOrientation(index: Int) {
        when (index) {
            0 -> _sexualOrientation.value = SexualOrientation.HETEROSEXUAL
            1 -> _sexualOrientation.value = SexualOrientation.LESBIAN
            else -> _sexualOrientation.value = null
        }

        print(_sexualOrientation.value)
    }
}