package com.simsek.cryptocrazytwo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

//Burayı oluşturduktan sonra Manifest içerisinde tanımlamamız lazım
@HiltAndroidApp
class CryptoCrazyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
    }
}