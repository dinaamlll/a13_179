package com.example.a13_179

import android.app.Application
import com.example.a13_179.container.AppContainer
import com.example.a13_179.container.MainContainer

class EventApplications: Application() {
    lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = MainContainer()
    }
}