package com.compose.cocktaildakk_compose.ui.detail.model

enum class ImageCropStatus {
    WAITING,
    MODIFYING,
    CROPPING,
    ;

    fun isModifying(action: () -> Unit) {
        if (this == MODIFYING) {
            action()
        }
    }

    fun isCropping(action: () -> Unit) {
        if (this == CROPPING) {
            action()
        }
    }
}