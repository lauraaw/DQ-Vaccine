package com.dq.dqvaccine.clases;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.dq.dqvaccine.activities.HijosActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static android.app.PendingIntent.FLAG_UPDATE_CURRENT;
import static android.content.Context.ALARM_SERVICE;

//Clase que instancia las notificaciones
public class Notificacion {
    //Recibe como paremetro la fecha, el id del hijo, nombre y mes
    public Notificacion(Context contexto, String dt, int hijoId, String nombre, int mes) {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Calendar fecha = Calendar.getInstance();
        try {
            fecha.setTime(sdf.parse(dt));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(fecha.getTime());
        Intent intent = new Intent(contexto, AlarmReceiver.class);
        intent.putExtra(HijosActivity.EXTRA_HIJO_ID, hijoId);
        intent.putExtra("mes", mes);
        intent.putExtra("nombre", nombre);
        intent.setAction(String.valueOf(hijoId)+String.valueOf(mes));
        int random = (int)System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(contexto, random, intent, FLAG_UPDATE_CURRENT);

        AlarmManager am = (AlarmManager)contexto.getSystemService(ALARM_SERVICE);
        am.set(AlarmManager.RTC_WAKEUP, fecha.getTimeInMillis(), pendingIntent);
    }
}
