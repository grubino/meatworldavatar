package com.kramerica.meatworldavatar.notifier;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.kramerica.meatworldavatar.meatdomain.AvatarDao;
import com.kramerica.meatworldavatar.meatdomain.DaoMaster;
import com.kramerica.meatworldavatar.meatdomain.EventDao;
import com.kramerica.meatworldavatar.meatdomain.RelevanceDao;
import com.kramerica.meatworldavatar.meatdomain.HabitDao;
import com.kramerica.meatworldavatar.meatdomain.DaoSession;
import com.kramerica.meatworldavatar.notifier.dummy.NotificationIndex;

import java.util.EnumMap;
import java.util.Map;

public class MeatWorldDataSink extends Service {

    private NotificationManager mNM;

    private SQLiteDatabase mDB;
    private DaoMaster mDBMaster;
    private DaoSession mDBSession;

    private AvatarDao mAvatarDao;
    private HabitDao mHabitDao;
    private EventDao mEventDao;
    private RelevanceDao mRelevanceDao;
    private Cursor cursor;

    private IBinder mBinder = new MWDataSinkBinder();

    private EnumMap<NotificationIndex, Notification> mNotificationPool;

    public class MWDataSinkBinder extends Binder {
        MeatWorldDataSink getService() {
            return MeatWorldDataSink.this;
        }
    }

    public class MeatDataSinkServerThread implements Runnable, SensorEventListener {

        private SensorManager mSensorManager;
        private Map<String, Sensor> mSensorMap;

        public MeatDataSinkServerThread() {
            mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
            for(Sensor s : mSensorManager.getSensorList(Sensor.TYPE_ALL)) {
                mSensorManager.registerListener(this, s, SensorManager.SENSOR_DELAY_NORMAL);
                mSensorMap.put(s.getName(), s);
            }
        }
        public void run() {
            boolean stop = false;
            while(!stop) {
                try {
                    Thread.sleep(100, 0);
                } catch (final InterruptedException e) {
                    MeatWorldDataSink.this.mNM.notify(
                            NotificationIndex.INTERRUPT_NOTIFICATION.ordinal(),
                            MeatWorldDataSink.this.mNotificationPool.get(NotificationIndex.INTERRUPT_NOTIFICATION));
                }
            }
        }

        @Override
        public void onSensorChanged(SensorEvent event) {
            switch(event.sensor.getType()) {
                case Sensor.TYPE_ACCELEROMETER:
                    break;
                default:
                    break;
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    }

    @Override
    public void onCreate() {
        mNM = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "avatar-db", null);
        mDB = helper.getWritableDatabase();
        mDBMaster = new DaoMaster(mDB);
        mDBSession = mDBMaster.newSession();

        mAvatarDao = mDBSession.getAvatarDao();
        mHabitDao = mDBSession.getHabitDao();
        mEventDao = mDBSession.getEventDao();
        mRelevanceDao = mDBSession.getRelevanceDao();

        createNotifications();
        showStartupNotification();
    }

    private void createNotifications() {
        for (NotificationIndex i: NotificationIndex.values()) {
            CharSequence text = i.message();
            Notification n =
                    new Notification.Builder(getApplicationContext())
                            .setContentTitle(text)
                            .setContentText(text)
                            .setSmallIcon(R.drawable.ic_launcher)
                            .build();
            mNotificationPool.put(i, n);
        }
    }

    private void showStartupNotification() {
        CharSequence text = getText(R.string.notification_mw_data_sink_started);
        mNM.notify(
                NotificationIndex.STARTUP_NOTIFICATION.ordinal(),
                mNotificationPool.get(NotificationIndex.STARTUP_NOTIFICATION));
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("MeatWorldDataSink", String.format("Received startId %d: %s", startId, intent));
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        mNM.cancel(NotificationIndex.STARTUP_NOTIFICATION.ordinal());
        Toast.makeText(this, R.string.notification_mw_data_sink_stopped, Toast.LENGTH_SHORT);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public MeatWorldDataSink() {
    }

}
