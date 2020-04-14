package samuelpedro.moost.despesa;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.NotificationCompat;
import samuelpedro.moost.R;
import samuelpedro.moost.login.Login;
/**
 * Created by samuel on 9/29/17.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Intent notificationIntent = new Intent(context, Login.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(Login.class);
        stackBuilder.addNextIntent(notificationIntent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        Notification notification = builder.setContentTitle("Notificação Moost")
                .setContentText("Despesa agendada para hoje!")
                .setTicker("Você deve pagar uma despesa hoje!")
                .setSmallIcon(R.drawable.icone)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(),R.drawable.icon))
                .setContentIntent(pendingIntent).build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notification.vibrate = new long[]{150,300,150,600};
        notificationManager.notify(R.drawable.icon, notification);

        try{
            Uri som = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone toque = RingtoneManager.getRingtone(context,som);
            toque.play();
        }catch(Exception e){
            e.printStackTrace();
        }

    }

}
