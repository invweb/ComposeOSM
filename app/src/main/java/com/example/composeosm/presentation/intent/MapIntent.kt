package com.example.composeosm.presentation.intent

sealed class MapIntent {
    object LoadMap : MapIntent()
    data class MoveToLocation(val lat: Double, val lon: Double) : MapIntent()
    data class AddMarker(val lat: Double, val lon: Double, val title: String) : MapIntent()
    object ClearMarkers : MapIntent()
}