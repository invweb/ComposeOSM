package com.example.composeosm.presentation.effect

sealed class MapEffect {
    data class ShowError(val message: String) : MapEffect()
    data class ShowToast(val message: String) : MapEffect()
}