package com.example.peregrinario.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings
import androidx.core.content.ContextCompat.startActivity
import java.util.Calendar

fun scheduleNotification(
    context: Context,
    year: Int,
    month: Int,
    day: Int,
    hour: Int,
    minute: Int
) {
    val alarmManager = context.getSystemService(AlarmManager::class.java)
    val intent = Intent(context, NotificationReceiver::class.java)  // BroadcastReceiver responsável pela notificação
    val pendingIntent = PendingIntent.getBroadcast(
        context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    // Verifica se a permissão para alarmes exatos foi concedida (Android 12+)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
        if (!alarmManager.canScheduleExactAlarms()) {
            val settingsIntent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
            context.startActivity(settingsIntent)  // Abre a tela para o usuário conceder permissão
            return
        }
    }

    // Configurar a data e hora para o alarme
    val calendar = Calendar.getInstance().apply {
        set(Calendar.YEAR, year)
        set(Calendar.MONTH, month - 1)  // Meses começam do índice 0
        set(Calendar.DAY_OF_MONTH, day)
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
        set(Calendar.SECOND, 0)
    }

    // Agendar o alarme
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent
        )
    } else {
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent
        )
    }
}

