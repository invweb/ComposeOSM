package com.example.composeosm

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.composeosm.ui.theme.ComposeOSMTheme
import dagger.hilt.android.AndroidEntryPoint
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import timber.log.Timber
import java.io.File


    private val REQUIRED_PERMISSIONS = arrayOf<String?>(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE
    )

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Turning on the Log on debug build
        Timber.plant(Timber.DebugTree())

        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
            Constants.REQUEST_CODE
        )
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            Constants.REQUEST_CODE
        )
        if(BuildConfig.DEBUG){
            Timber.d("DEBUG build")
        }

        if (!arePermissionsGranted(this)) {
            Timber.d("${!arePermissionsGranted(this)}")
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                Constants.PERMISSIONS_REQUEST_CODE
            )
        } else {
            initializeMap(this)
        }

        // OSMDroid initialization (in case it is not in the Application)
        Configuration.getInstance().load(
            this,
            getSharedPreferences("osmdroid", MODE_PRIVATE)
        )
        setContent {
            ComposeOSMTheme {
                MapScreen(this)
            }
        }
    }
}

@Composable
fun MapScreen(activity: MainActivity) {
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

fun initializeMap(activity: MainActivity) {
    Timber.d("initializeMap")
    Configuration.getInstance().userAgentValue = BuildConfig.APPLICATION_ID

    val baseDir: File = File(activity.getExternalFilesDir(null), "osmdroid")
    if (!baseDir.exists()) {
        baseDir.mkdirs()
    }
    Configuration.getInstance().osmdroidBasePath = baseDir
    Configuration.getInstance().osmdroidTileCache = File(baseDir, "tiles")
}

private fun arePermissionsGranted(activity: Activity): Boolean {
    for (permission in REQUIRED_PERMISSIONS) {
        if (ContextCompat.checkSelfPermission(
                activity,
                permission.toString()
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Timber.d("arePermissionsGranted: false")
            ActivityCompat.requestPermissions(activity, REQUIRED_PERMISSIONS, 101)
            return false
        }
    }
    Timber.d("arePermissionsGranted: true")
    return true
}