package com.example.powermmanagementapplication.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.powermmanagementapplication.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

public class NotificationHandlerService extends FirebaseMessagingService{

    private static final String FIREBASE_MESSAGING = "FirebaseMessaging";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        Log.d(FIREBASE_MESSAGING, "Message received: " + message.getNotification().getBody());
        showNotification(message.getNotification());
    }

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
        Log.d(FIREBASE_MESSAGING, token);
    }

    public void showNotification(RemoteMessage.Notification message) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "com.example.powermmanagementapplication"; //your app package name

        NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "Notification",
                NotificationManager.IMPORTANCE_DEFAULT);

        notificationChannel.setDescription("Techrush Channel");
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(R.color.primary_color);
        notificationChannel.setVibrationPattern(new long[]{0, 1000, 500, 1000});
        notificationManager.createNotificationChannel(notificationChannel);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.alert_white)
                .setContentTitle(message.getTitle())
                .setContentText(message.getBody())
                .setContentInfo("Info");

        notificationManager.notify(new Random().nextInt(),notificationBuilder.build());

    }

}
