package com.dq.dqvaccine.clases;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.dq.dqvaccine.activities.HijosActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class Notificacion {

    String fecha;
    int hijoId;
    //int mes;

    public Notificacion(Context contexto, String dt, int hijoId) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar fecha = Calendar.getInstance();
        try {
            fecha.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        fecha.set(Calendar.HOUR_OF_DAY, 16);
        Intent intent = new Intent(contexto, AlarmReceiver.class);
        intent.putExtra(HijosActivity.EXTRA_HIJO_ID, hijoId);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(contexto, 001, intent, 0);

        AlarmManager am = (AlarmManager)contexto.getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, fecha.getTimeInMillis(), pendingIntent);
        System.out.println("1");
        System.out.println(fecha.getTime());
        System.out.println(fecha.getTimeInMillis());
    }
}
