package com.example.composeosm.presentation.state

import org.osmdroid.util.GeoPoint

data class MarkerData(
    val id: String,
    val position: GeoPoint,
    val title: String
)