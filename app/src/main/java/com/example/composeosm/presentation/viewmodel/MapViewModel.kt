package com.example.composeosm.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.composeosm.presentation.effect.MapEffect
import com.example.composeosm.presentation.intent.MapIntent
import com.example.composeosm.presentation.state.MapState
import com.example.composeosm.presentation.state.MarkerData
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint

class MapViewModel : ViewModel() {
    private val _state = MutableStateFlow(MapState())
    val state: StateFlow<MapState> = _state.asStateFlow()

    private val _effect = MutableSharedFlow<MapEffect>()
    val effect: SharedFlow<MapEffect> = _effect

    fun handleIntent(intent: MapIntent) {
        when (intent) {
            is MapIntent.LoadMap -> loadMap()
            is MapIntent.MoveToLocation -> moveToLocation(intent.lat, intent.lon)
            is MapIntent.AddMarker -> addMarker(intent.lat, intent.lon, intent.title)
            is MapIntent.ClearMarkers -> clearMarkers()
        }
    }

    private fun loadMap() {
        _state.update { it.copy(isLoading = true) }
        // Здесь можно загрузить начальные данные
        _state.update { it.copy(isLoading = false) }
    }

    private fun moveToLocation(lat: Double, lon: Double) {
        _state.update {
            it.copy(
                mapCenter = GeoPoint(lat, lon),
                zoomLevel = 15
            )
        }
    }

    private fun addMarker(lat: Double, lon: Double, title: String) {
        val newMarker = MarkerData(
            id = System.currentTimeMillis().toString(),
            position = GeoPoint(lat, lon),
            title = title
        )
        _state.update { current ->
            current.copy(markers = current.markers + newMarker)
        }
        viewModelScope.launch {
            _effect.emit(MapEffect.ShowToast("Маркер добавлен: $title"))
        }
    }

    private fun clearMarkers() {
        _state.update { it.copy(markers = emptyList()) }
        viewModelScope.launch {
            _effect.emit(MapEffect.ShowToast("Все маркеры удалены"))
        }
    }
}
