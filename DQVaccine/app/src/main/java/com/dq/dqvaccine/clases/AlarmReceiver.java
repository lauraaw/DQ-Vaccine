package com.dq.dqvaccine.clases;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;

import com.dq.dqvaccine.MainActivity;
import com.dq.dqvaccine.R;
import com.dq.dqvaccine.activities.HijosActivity;
import com.dq.dqvaccine.activities.HijosDetalleActivity;
import com.dq.dqvaccine.activities.VacunasActivity;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        System.out.println("2");
        NotificationCompat.Builder mBuilder =
            new NotificationCompat.Builder(context)
                    .setSmallIcon(R.drawable.ic_notifications)
                    .setContentTitle("Vacunar")
                    .setContentText("Blablabla");
        Intent resultIntent = new Intent(context, VacunasActivity.class);
        int id = intent.getIntExtra(HijosActivity.EXTRA_HIJO_ID, 1);
        resultIntent.putExtra(HijosActivity.EXTRA_HIJO_ID, id);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(VacunasActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder.setAutoCancel(true);
        mNotificationManager.notify(1, mBuilder.build());
    }
}