package projetopgm.com.br.projetopgm.fcm;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;

import projetopgm.com.br.projetopgm.MainActivity;
import projetopgm.com.br.projetopgm.R;

public class FcmService extends FirebaseMessagingService {
    public static final int NOTIFICATION_ID = 1;

    @Override
    public void handleIntent(Intent intent) {
        super.handleIntent(intent);

        Bundle extras = intent.getExtras();

        if(!extras.isEmpty()){
            sendNotification(extras.getString("mensagem"));
        }
    }


    private void sendNotification(String msg) {
        NotificationManagerCompat nm = NotificationManagerCompat.from(this);

        Intent it = new Intent(this, MainActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addNextIntent(it);
        PendingIntent pit = stackBuilder.getPendingIntent(
                0,PendingIntent.FLAG_CANCEL_CURRENT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setDefaults(Notification.DEFAULT_ALL)
                .setAutoCancel(true)
                .setContentIntent(pit)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle( getString(R.string.texto_notificacao))
                .setContentText(msg);

        nm.notify(NOTIFICATION_ID, mBuilder.build());
    }
}
