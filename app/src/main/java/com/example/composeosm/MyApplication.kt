package com.example.composeosm

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import org.osmdroid.config.Configuration
@HiltAndroidApp
class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Configuration.getInstance()
            .load(this, getSharedPreferences("osmdroid", MODE_PRIVATE))
            // Опционально: установите путь к кэшу
            .apply {
                var osmdroidTileCache = getExternalFilesDir("tiles")?.absolutePath
            }    }
}