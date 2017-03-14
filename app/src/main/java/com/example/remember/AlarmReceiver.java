package com.example.remember;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v7.app.NotificationCompat;

import com.example.remember.activity.MainActivity;
import com.example.remember.database.DataSource;
import com.example.remember.model.Reminder;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        DataSource dataSource = new DataSource(context);

        int remId = intent.getIntExtra("reminder", 0);
        String ringtone = intent.getStringExtra("ringtone");
        boolean vibrate = intent.getBooleanExtra("vibrate", true);
        Reminder r = dataSource.getReminder(remId);
        showNotification(context, r, ringtone, vibrate);
        dataSource.close();

        Intent launch_intent = new Intent(context, MainActivity.class);
        launch_intent.setComponent(new ComponentName("com.example.remember", "com.example.remember.activity.MainActivity"));
        launch_intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        launch_intent.putExtra("remId", r.getId() + "");
        launch_intent.putExtra("alarm", true);
        launch_intent.addCategory(Intent.CATEGORY_LAUNCHER);
        context.startActivity(launch_intent);
    }

    private void showNotification(Context context, Reminder r, String ringtone, boolean vibrate) {
        NotificationCompat.Builder notification = new NotificationCompat.Builder(context);
        Uri ring = Uri.parse(ringtone);
        notification.setContentTitle("Remember");

        notification.setSmallIcon(R.mipmap.ic_launcher);
        notification.setContentText(r.getTitle());
        notification.setSound(ring);
        if (vibrate) {
            notification.setVibrate(new long[]{1000, 1000});
        }
        notification.setLights(Color.BLUE, 1000, 1000);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, notification.build());
    }
}
