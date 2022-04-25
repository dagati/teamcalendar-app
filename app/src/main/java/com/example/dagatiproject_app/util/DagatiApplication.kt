package com.example.dagatiproject_app.util

import android.app.Application

class DagatiApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        SharedPref.init(applicationContext)
    }

}