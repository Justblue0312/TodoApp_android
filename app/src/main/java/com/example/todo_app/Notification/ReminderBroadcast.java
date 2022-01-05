package com.example.todo_app.Notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.todo_app.R;
import com.example.todo_app.Utils.Const;
import com.example.todo_app.Utils.Sharepref;

public class ReminderBroadcast extends BroadcastReceiver {
    String label, task;
    @Override
    public void onReceive(Context context, Intent intent) {

        label = Sharepref.getDefaults("label", context.getApplicationContext());
        task = Sharepref.getDefaults("task", context.getApplicationContext());

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Const.CHANNEL_STATIC)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(label)
                .setContentText(task)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(200, builder.build());
    }
}
