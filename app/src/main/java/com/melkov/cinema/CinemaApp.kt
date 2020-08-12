package com.melkov.cinema

import android.app.Application
import com.melkov.cinema.di.AppComponent
import com.melkov.cinema.di.DaggerAppComponent

class CinemaApp : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        appComponent = DaggerAppComponent.builder().build()
    }
}