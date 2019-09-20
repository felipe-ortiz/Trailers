package com.felipeortiz.trailers

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen
import timber.log.Timber


class MovieApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        Timber.plant(Timber.DebugTree())
    }
}