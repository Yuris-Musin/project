package ru.musindev.myapp.receivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import ru.musindev.myapp.R

class ConnectionChecker : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent == null) return
        when (intent.action) {

                // Когда батарея разряжена
            Intent.ACTION_BATTERY_LOW -> {
                Toast.makeText(context, R.string.toast_battery_low, Toast.LENGTH_SHORT).show()
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            // Когда подключена зарядка
            Intent.ACTION_POWER_CONNECTED -> {
                Toast.makeText(context, R.string.toast_power_connected, Toast.LENGTH_SHORT).show()
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }
}