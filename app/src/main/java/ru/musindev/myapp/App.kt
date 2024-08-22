package ru.musindev.myapp

import android.app.Application
import ru.musindev.myapp.di.AppComponent
import ru.musindev.myapp.di.DaggerAppComponent

class App : Application() {
    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        //Создаем компонент
        dagger = DaggerAppComponent.create()
    }

    companion object {
        lateinit var instance: App
            private set
    }
}