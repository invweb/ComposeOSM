package com.example.composeosm.presentation.state

import org.osmdroid.util.GeoPoint

data class MapState(
    val isLoading: Boolean = false,
    val mapCenter: GeoPoint = GeoPoint(55.751244, 37.618423), // MSK
    val zoomLevel: Int = 12,
    val markers: List<MarkerData> = emptyList(),
    val error: String? = null
)