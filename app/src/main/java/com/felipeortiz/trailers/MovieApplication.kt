package com.felipeortiz.trailers

import android.app.Application
import com.felipeortiz.trailers.di.AppComponent
import com.felipeortiz.trailers.di.AppModule
import com.felipeortiz.trailers.di.DaggerAppComponent
import com.felipeortiz.trailers.di.ServiceModule
import com.jakewharton.threetenabp.AndroidThreeTen
import timber.log.Timber


class MovieApplication : Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
        Timber.plant(Timber.DebugTree())

        INSTANCE = this
        component = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .serviceModule(ServiceModule())
            .build()
    }

    companion object {
        private var INSTANCE: MovieApplication? = null

        fun get(): MovieApplication = INSTANCE!!
    }

}