package ru.musindev.myapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import ru.musindev.myapp.di.AppComponent
import ru.musindev.myapp.di.DaggerAppComponent
import ru.musindev.myapp.di.modules.DatabaseModule
import ru.musindev.myapp.di.modules.DomainModule
import ru.musindev.myapp.view.notifications.NotificationConstants.CHANNEL_ID
import ru.musindev.remote_module.DaggerRemoteComponent

class App : Application() {
    lateinit var dagger: AppComponent

    override fun onCreate() {
        super.onCreate()
        //Инициализируем экземпляр App, через который будем получать доступ к остальным переменным
        instance = this
        //Создаем компонент
        val remoteProvider = DaggerRemoteComponent.create()
        dagger = DaggerAppComponent.builder()
            .remoteProvider(remoteProvider)
            .databaseModule(DatabaseModule())
            .domainModule(DomainModule(this))
            .build()
        //Задаем имя, описание и важность канала
        val name = "WatchLaterChannel"
        val descriptionText = "FilmsSearch notification Channel"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        //Создаем канал, передав в параметры его ID(строка), имя(строка), важность(константа)
        val mChannel = NotificationChannel(CHANNEL_ID, name, importance)
        //Отдельно задаем описание
        mChannel.description = descriptionText
        //Получаем доступ к менеджеру нотификаций
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        //Регистрируем канал
        notificationManager.createNotificationChannel(mChannel)
    }

    companion object {
        //Здесь статически хранится ссылка на экземпляр App
        lateinit var instance: App
            //Приватный сеттер, чтобы нельзя было в эту переменную присвоить что-либо другое
            private set
    }
}