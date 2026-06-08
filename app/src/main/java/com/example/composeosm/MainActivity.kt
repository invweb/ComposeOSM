package com.example.composeosm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.example.composeosm.ui.theme.ComposeOSMTheme
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Инициализация OSMDroid (на случай, если не в Application)
        Configuration.getInstance().load(
            this,
            getSharedPreferences("osmdroid", MODE_PRIVATE)
        )

        setContent {
            ComposeOSMTheme {
                MapScreen()
            }
        }
    }
}

@Composable
fun MapScreen() {
    AndroidView(
        factory = { context ->
            MapView(context).apply {
                // Устанавливаем источник тайлов — обычная карта
                setTileSource(TileSourceFactory.MAPNIK)

                // Включаем жесты масштабирования и прокрутки
                setBuiltInZoomControls(true)
                setMultiTouchControls(true)

                // Центр карты — например, Москва
                controller.setZoom(12.0)
                controller.setCenter(GeoPoint(55.751244, 37.618423))
            }
        },
        update = { mapView ->
            // Здесь можно обновлять карту при изменении состояния (например, из ViewModel)
            // Например: перецентрирование, добавление маркеров и т.д.
        },
        modifier = Modifier.fillMaxSize()
    )
}