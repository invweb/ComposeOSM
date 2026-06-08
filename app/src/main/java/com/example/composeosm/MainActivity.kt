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

        // OSMDroid initialization (in case it is not in the Application)
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
                // Setting the tile source — a regular map
                setTileSource(TileSourceFactory.MAPNIK)

                // Enabling zoom and scroll gestures
                setBuiltInZoomControls(true)
                setMultiTouchControls(true)

                // The center of the map is, for example, Moscow
                controller.setZoom(12.0)
                controller.setCenter(GeoPoint(55.751244, 37.618423))
            }
        },
        update = { mapView ->
            // Here you can update the map when the state changes (for example, from the ViewModel)
            // For example: re-centering, adding markers, etc.
        },
        modifier = Modifier.fillMaxSize()
    )
}