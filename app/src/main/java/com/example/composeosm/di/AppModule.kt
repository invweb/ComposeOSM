package com.example.composeosm.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    /**
     * Предоставляет контекст приложения
     * Может использоваться в других зависимостях
     */
    @Provides
    @Singleton
    fun provideAppContext(@ApplicationContext context: Context): Context = context
}