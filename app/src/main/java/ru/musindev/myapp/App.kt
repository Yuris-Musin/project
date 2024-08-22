package ru.musindev.myapp

import android.app.Application
import okhttp3.internal.Internal.instance
import ru.musindev.myapp.di.AppComponent
import ru.musindev.myapp.di.DaggerAppComponent
import ru.musindev.myapp.di.modules.DatabaseModule
import ru.musindev.myapp.di.modules.DomainModule
import ru.musindev.myapp.di.modules.RemoteModule

class App : Application() {
    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        instance = this
        //Создаем компонент
        dagger = DaggerAppComponent.builder()
            .remoteModule(RemoteModule())
            .databaseModule(DatabaseModule())
            .domainModule(DomainModule(this))
            .build()
    }

    companion object {
        lateinit var instance: App
            private set
    }
}