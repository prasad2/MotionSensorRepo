package com.coen268.arttherapy;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


/**
 * Created by DELL on 16-02-2015.
 */
public class NotifyReceiver extends BroadcastReceiver {
    DrawCanvas canvas = new DrawCanvas();
    @Override
    public void onReceive(Context context, Intent intent) {


        int requestCode = 0;
        int flags = 0;
        Intent intent1 = new Intent(context, DrawCanvas.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, requestCode, intent1, flags);

        int id = 12345;
        Notification notification = new Notification.Builder(context)
                .setContentTitle("Canvas")
                .setContentText("Want to start a new Painting !!!")
                .setSmallIcon(android.R.drawable.ic_menu_day)
                .setContentIntent(pendingIntent)
                .build();

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(id, notification);

    }


}
