package com.example.todo_app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.todo_app.Utils.Const;
import com.example.todo_app.Utils.Sharepref;
import com.hanks.passcodeview.PasscodeView;

import java.util.Date;

public class AppLockActivity extends AppCompatActivity {
    PasscodeView passcodeView;
    String password = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_lock);

        if(Const.isSet){
            Sharepref.setDefaults("password", "1234", this.getApplicationContext());
            Const.isSet = false;
        }
        password = Sharepref.getDefaults("password", this.getApplicationContext());

        createNotificationChannel();

        passcodeView = findViewById(R.id.passcode_view);
        passcodeView.setPasscodeLength(4)
                .setLocalPasscode(password)
                .setListener(new PasscodeView.PasscodeViewListener() {
                    @Override
                    public void onFail() {
                        Toast.makeText(AppLockActivity.this, "Password fail", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(String number) {
                        startActivity(new Intent(AppLockActivity.this, MainActivity.class));
                    }
                });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(password.equals("1234")){
            sendDefaultPassword();
        }
    }

    private void sendDefaultPassword() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round);

        Notification notification = new NotificationCompat.Builder(this, Const.CHANNEL_ID)
                .setContentTitle("Your default password is:")
                .setContentText(Const.DEFAULT_PASSWORD)
                .setSmallIcon(R.drawable.lock)
                .setLargeIcon(bitmap)
                .setColor(getResources().getColor(R.color.black))
                .build();

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
        notificationManagerCompat.notify(getNotificationID(), notification);

    }

    private int getNotificationID(){
        Const.NOTIFICATION_ID = (int) new Date().getTime();
        return Const.NOTIFICATION_ID;
    }

    private void createNotificationChannel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(Const.CHANNEL_ID, name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            if(notificationManager != null){
                notificationManager.createNotificationChannel(channel);
            }

        }
    }


}