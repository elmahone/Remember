package com.example.remember;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;

import com.example.remember.activity.MainActivity;
import com.example.remember.activity.ReminderDetailsActivity;
import com.example.remember.database.DataSource;
import com.example.remember.model.Reminder;

public class AlarmReceiver extends BroadcastReceiver {
    DataSource dataSource;

    @Override
    public void onReceive(Context context, Intent intent) {
        dataSource = new DataSource(context);
        int remId = intent.getIntExtra("reminder", 0);
        Reminder r = dataSource.getReminder(remId);
        showNotification(context, r);
        dataSource.close();

        Intent launch_intent = new Intent(context, MainActivity.class);
        launch_intent.setComponent(new ComponentName("com.example.remember", "com.example.remember.activity.MainActivity"));
        launch_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK /*| Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED*/);
        launch_intent.putExtra("remId", r.getId() + "");
        launch_intent.putExtra("alarm", true);
        launch_intent.addCategory(Intent.CATEGORY_LAUNCHER);
        context.startActivity(launch_intent);
    }

    private void showNotification(Context context, Reminder r) {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context);
        Uri ring = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        notification.setContentTitle("Remember");

        notification.setSmallIcon(R.mipmap.ic_launcher);
        notification.setContentText(r.getTitle());
        notification.setSound(ring);
        notification.setVibrate(new long[]{1000, 1000});
        notification.setLights(Color.BLUE, 1000, 1000);


        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, notification.build());
    }
}
