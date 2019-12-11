package com.example.vimusic.notification;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v4.media.session.MediaSessionCompat;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.vimusic.R;
import com.example.vimusic.model.BindingModel;
import com.example.vimusic.services.NotificationActionServices;

public class CreateNotification {

    public static final String CHANNEL_ID = "channel68";

    public static final String ACTION_PREVIUOS = "actionprevious";
    public static final String ACTION_PLAY = "actionplay";
    public static final String ACTION_NEXT = "actionnext";
    public static final String ACTION_LOOP = "actionloop";

    public static Notification notification;

    public static void createNotification(Context context, String title, String artist, int Playbuttom, int position, int size) {


        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        MediaSessionCompat mediaSessionCompat = new MediaSessionCompat(context, "tag");

        Bitmap icon = BitmapFactory.decodeResource(context.getResources(), R.drawable.backround);

        PendingIntent pendingIntentPrevious;
        int drw_preview;
        if (position == 0) {
            pendingIntentPrevious = null;
            drw_preview = 0;
        } else {
            Intent intentPreview = new Intent(context, NotificationActionServices.class).setAction(ACTION_PREVIUOS);
            pendingIntentPrevious = PendingIntent.getBroadcast(context, 0,
                    intentPreview, PendingIntent.FLAG_UPDATE_CURRENT);
            drw_preview = R.drawable.ic_skip_previous_black_24dp;
        }


        Intent intentPlay = new Intent(context, NotificationActionServices.class).setAction(ACTION_PLAY);
        PendingIntent pendingIntentPlay = PendingIntent.getBroadcast(context, 0, intentPlay, PendingIntent.FLAG_UPDATE_CURRENT);


        PendingIntent pendingIntentNext;
        int drw_next;
        if (position == size) {
            pendingIntentNext = null;
            drw_next = 0;
        } else {
            Intent intentNext = new Intent(context, NotificationActionServices.class)
                    .setAction(ACTION_NEXT);
            pendingIntentNext = PendingIntent.getBroadcast(context, 0,
                    intentNext, PendingIntent.FLAG_UPDATE_CURRENT);
            drw_next = R.drawable.ic_skip_next_black_24dp;
        }


        notification = new NotificationCompat.Builder(context, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_music_note)
                .setContentTitle(title)
                .setContentText(artist)
                .setLargeIcon(icon)
                .setOnlyAlertOnce(true)//show notification for only first time
                .setShowWhen(false)
                .addAction(drw_preview, "Previous", pendingIntentPrevious)
                .addAction(Playbuttom, "Play", pendingIntentPlay)
                .addAction(drw_next, "Next", pendingIntentNext)
                .setStyle(new androidx.media.app.NotificationCompat.MediaStyle()
                        .setShowActionsInCompactView(0, 1, 2)
                        .setMediaSession(mediaSessionCompat.getSessionToken()))
                .setPriority(NotificationCompat.PRIORITY_LOW)
                .build();

        notificationManagerCompat.notify(1, notification);
    }


}
