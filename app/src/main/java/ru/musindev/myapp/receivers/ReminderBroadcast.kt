package ru.musindev.myapp.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import ru.musindev.myapp.data.Film
import ru.musindev.myapp.view.notifications.NotificationConstants
import ru.musindev.myapp.view.notifications.NotificationHelper

class ReminderBroadcast : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val bundle = intent?.getBundleExtra(NotificationConstants.FILM_BUNDLE_KEY)
        val film: Film = bundle?.get(NotificationConstants.FILM_KEY) as Film
        NotificationHelper.createNotification(context!!, film)
    }
}