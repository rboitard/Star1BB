package fr.istic.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

import fr.istic.MainActivity;
import fr.istic.R;

/**
 * Created by Bah on 05/01/2018.
 */

public class MyService extends Service{

    private Looper mServiceLooper;
    private ServiceHandler mServiceHandler;
    private static final int NOTIFICATION_ID = 1;
    private String Tag = "MyService";


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
       // super.onCreate();
        HandlerThread thread = new HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND);
        thread.start();
        mServiceLooper = thread.getLooper();
        mServiceHandler = new ServiceHandler(mServiceLooper);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Message message = mServiceHandler.obtainMessage();
        message.arg1 = startId;
        mServiceHandler.sendMessage(message);

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private final class ServiceHandler extends Handler
    {
        public ServiceHandler(Looper looper)
        {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            showForegroundNotification();
            stopSelf(msg.arg1);
        }

        public void showForegroundNotification()
        {

            String CHANNEL_ID = "my_channel_01";
            NotificationCompat.Builder mBuilder =
                    (NotificationCompat.Builder) new NotificationCompat.Builder(getApplicationContext())
                            .setSmallIcon(R.mipmap.ic_data)
                            .setContentTitle("New Data")
                            .setContentText("You need update your database");
            Intent resultIntent = new Intent(getApplicationContext(), MainActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
            stackBuilder.addParentStack(MainActivity.class);
            stackBuilder.addNextIntent(resultIntent);
            PendingIntent resultPendingIntent =
                    stackBuilder.getPendingIntent(
                            0,
                            PendingIntent.FLAG_UPDATE_CURRENT
                    );
            mBuilder.setContentIntent(resultPendingIntent);
            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
        }
    }
}
