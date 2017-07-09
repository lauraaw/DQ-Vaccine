package com.dq.dqvaccine.clases;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.dq.dqvaccine.R;
import com.dq.dqvaccine.activities.HijosActivity;
import com.dq.dqvaccine.activities.VacunasActivity;
//Clase que construye la alarma para la notificacion
public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int id = intent.getIntExtra(HijosActivity.EXTRA_HIJO_ID, 1);
        int mes = intent.getIntExtra("mes", 0);
        String nombre = intent.getStringExtra("nombre");
        String texto = "";
        switch (mes) {
            case 0:
                texto = "Mes 0";
                break;
            case 2:
                texto = "Mes 2";
                break;
            case 4:
                texto = "Mes 4";
                break;
            case 6:
                texto = "Mes 6";
                break;
            case 12:
                texto = "Mes 12";
                break;
            case 15:
                texto = "Mes 15";
                break;
            case 18:
                texto = "Mes 18";
                break;
            case 48:
                texto = "Mes 48";
                break;
        }
        NotificationCompat.Builder mBuilder =
            new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_notifications)
                    .setContentTitle(nombre)
                    .setContentText("Vacuna pendiente. " +  texto);
        Intent resultIntent = new Intent(context, VacunasActivity.class);
        resultIntent.putExtra(HijosActivity.EXTRA_HIJO_ID, id);
        resultIntent.setAction(nombre+String.valueOf(mes));
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(VacunasActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        int random = (int)System.currentTimeMillis();
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(random, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder.setAutoCancel(true);
        mNotificationManager.notify(random, mBuilder.build());
    }
}