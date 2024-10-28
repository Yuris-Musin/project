package ru.musindev.myapp.view.notifications

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.DatePickerDialog
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import ru.musindev.myapp.R
import ru.musindev.myapp.data.Film
import ru.musindev.myapp.receivers.ReminderBroadcast
import ru.musindev.myapp.view.MainActivity
import java.util.*

object NotificationHelper {

    private var notifyId = 0
    private var filmName = ""

    @SuppressLint("MissingPermission")
    fun createNotification(context: Context, film: Film) {
        val mIntent = Intent(context, MainActivity::class.java)

        val flags = PendingIntent.FLAG_UPDATE_CURRENT or
                PendingIntent.FLAG_IMMUTABLE

        val pendingIntent =
            PendingIntent.getActivity(context, 0, mIntent, flags)

        val builder = NotificationCompat.Builder(context, NotificationConstants.CHANNEL_ID).apply {
            setSmallIcon(R.drawable.baseline_access_time_24)
            setContentTitle("Не забудьте посмотреть!")
            setContentText(film.title)
            priority = NotificationCompat.PRIORITY_DEFAULT
            setContentIntent(pendingIntent)
            setAutoCancel(true)
        }

        val notificationManager = NotificationManagerCompat.from(context)

        if (filmName != film.title) {
            notifyId++
        }
        filmName = film.title

        Glide.with(context)
            .asBitmap()
            .load(film.poster)
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {
                }
                //Этот коллбэк отрабатоет когда мы успешно получим битмап
                @SuppressLint("MissingPermission")
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    // //Создаем нотификации 'big picture'
                    builder.setStyle(NotificationCompat.BigPictureStyle().bigPicture(resource))
                    //Обновляем нотификацю
                    notificationManager.notify(notifyId, builder.build())
                }
            })
        //Отправляем изначальную нотификацю в стандартном исполнении
        notificationManager.notify(notifyId, builder.build())
    }

    fun notificationSet(context: Context, film: Film) {
        val calendar = Calendar.getInstance()
        val currentYear = calendar.get(Calendar.YEAR)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)
        DatePickerDialog(
            context,
            { _, dpdYear, dpdMonth, dayOfMonth ->
                val timeSetListener =
                    TimePickerDialog.OnTimeSetListener { _, hourOfDay, pickerMinute ->
                        val pickedDateTime = Calendar.getInstance()
                        pickedDateTime.set(
                            dpdYear,
                            dpdMonth,
                            dayOfMonth,
                            hourOfDay,
                            pickerMinute,
                            0
                        )
                        val dateTimeInMillis = pickedDateTime.timeInMillis
                        //После того, как получим время, вызываем метод, который создаст Alarm
                        createWatchLaterEvent(context, dateTimeInMillis, film)
                    }
                TimePickerDialog(
                    context,
                    timeSetListener,
                    currentHour,
                    currentMinute,
                    true
                ).show()
            },
            currentYear,
            currentMonth,
            currentDay
        ).show()
    }
    private fun createWatchLaterEvent(context: Context, dateTimeInMillis: Long, film: Film) {
        //Получаем доступ к AlarmManager
        val alarmManager =
            context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        //Создаем интент для запуска ресивера
        val intent = Intent(film.title, null, context, ReminderBroadcast()::class.java)
        //Кладем в него фильм
        val bundle = Bundle()
        bundle.putParcelable(NotificationConstants.FILM_KEY, film)
        intent.putExtra(NotificationConstants.FILM_BUNDLE_KEY, bundle)
        //Создаем пендинг интент для запуска извне приложения
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        //Устанавливаем Alarm
        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            dateTimeInMillis,
            pendingIntent
        )
    }
}