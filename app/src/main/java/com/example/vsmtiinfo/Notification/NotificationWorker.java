package com.example.vsmtiinfo.Notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.vsmtiinfo.Activity.PocetniActivity;
import com.example.vsmtiinfo.Model.Notification;
import com.example.vsmtiinfo.R;

import java.util.ArrayList;


public class NotificationWorker extends Worker {
    private static final String TAG = "MyApp";
    private NotificationManager mNotificationManager;
    private NotificationChannel mChannel;
    private Context context;
    private ArrayList<Notification>lNotifications = new ArrayList<>();
    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        Log.d(TAG, "doWork: work is done");

        Data data = getInputData();
        String naslov = data.getString("naslov");
        String text = data.getString("text");
        int id = data.getInt("id",-1);
        Log.d(TAG, "doWork: " + naslov);
        displayNotification(naslov, text, id);
        return Result.success();
    }
    private void displayNotification(String title, String task, int id) {
        Log.d(TAG, "displayNotification: notification Display");
        mNotificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            mChannel = new NotificationChannel("Notification", "Test", NotificationManager.IMPORTANCE_DEFAULT);
            mNotificationManager.createNotificationChannel(mChannel);
        }

        NotificationCompat.Builder notification = new NotificationCompat.Builder(getApplicationContext(), "Notification")
                .setContentTitle(title)
                .setContentText(task)
                .setAutoCancel(true)
                .setSmallIcon(R.drawable.vsmti_circle);

        // Create an Intent for the activity you want to start
        Intent resultIntent = new Intent(context, PocetniActivity.class);

        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntentWithParentStack(resultIntent);

        // Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        notification.setContentIntent(resultPendingIntent);

        mNotificationManager.notify(id, notification.build());
    }
}
