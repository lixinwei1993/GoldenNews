package com.lixinwei.www.goldennews.services;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.lixinwei.www.goldennews.R;
import com.lixinwei.www.goldennews.app.GoldenNewsApplication;
import com.lixinwei.www.goldennews.data.domain.ZhihuService;
import com.lixinwei.www.goldennews.data.model.DailyStories;
import com.lixinwei.www.goldennews.data.model.TopStory;
import com.lixinwei.www.goldennews.data.sharedPreferences.PreferencesServiceImpl;
import com.lixinwei.www.goldennews.newslist.NewsListActivity;
import com.lixinwei.www.goldennews.util.Utils;

import javax.inject.Inject;

import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by welding on 2017/8/5.
 */

public class PollService extends IntentService {
    private static final String TAG = "PollService";

    public static final String ACTION_SHOW_NOTIFICATION =
            "com.lixinwei.www.GoldenNewsApplication.SHOW_NOTIFICATION";
    public static final String PERM_PRIVATE =
            "com.lixinwei.www.goldennews.PRIVATE";
    public static final String REQUEST_CODE = "REQUEST_CODE";
    public static final String NOTIFICATION = "NOTIFICATION";

    @Inject
    ZhihuService mZhihuService;

    public static Intent newIntent(Context context) {
        return new Intent(context, PollService.class);
    }

    public static void setServiceAlarm(Context context, boolean isOn) {
        Intent i = PollService.newIntent(context);
        PendingIntent pi = PendingIntent.getService(context, 0, i, 0);
        AlarmManager alarmManager = (AlarmManager)
                context.getSystemService(Context.ALARM_SERVICE);
        if (isOn) {
            alarmManager.setInexactRepeating(AlarmManager.ELAPSED_REALTIME,
                    SystemClock.elapsedRealtime(), PreferencesServiceImpl.getNotificationFrequency(context), pi);
        } else {
            alarmManager.cancel(pi);
            pi.cancel();
        }

        PreferencesServiceImpl.setAlarmOn(context, isOn);
    }

    public static boolean isPollServiceAlarmOn(Context context) {
        Intent i = PollService.newIntent(context);
        PendingIntent pi = PendingIntent
                .getService(context, 0, i, PendingIntent.FLAG_NO_CREATE);
        return pi != null;
    }

    public PollService() {
        super(TAG);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        GoldenNewsApplication.getGoldenNewsApplication(this).getApplicationComponent().inject(this);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (!Utils.isConnected(this)) {
            return;
        }

        long lastId = PreferencesServiceImpl.getNewestStory(this);

        mZhihuService.getDailyStories().subscribe(new DisposableObserver<DailyStories>() {
            @Override
            public void onNext(@NonNull DailyStories dailyStories) {
                TopStory story = dailyStories.getTopStories().get(0);
                long currentId = story.getId();
                long lastId = PreferencesServiceImpl.getNewestStory(PollService.this);
                if(true) {
                    PreferencesServiceImpl.setNewestStory(PollService.this, currentId);
                    Log.i(TAG, "Received an story " + currentId);

                    Resources resources = getResources();
                    Intent i = NewsListActivity.newIntent(PollService.this);
                    PendingIntent pi = PendingIntent.getActivity(PollService.this, 0, i, 0);
                    Notification notification = new NotificationCompat.Builder(PollService.this)
                            .setTicker(resources.getString(R.string.new_story_title))
                            .setSmallIcon(android.R.drawable.ic_menu_report_image)
                            .setContentTitle(resources.getString(R.string.new_story_title))
                            .setContentText(story.getTitle())
                            .setContentIntent(pi)
                            .setAutoCancel(true)
                            .build();


                    showBackgroundNotification(0, notification);
                }
            }

            private void showBackgroundNotification(int requestCode, Notification notification) {
                Intent i = new Intent(ACTION_SHOW_NOTIFICATION);
                i.putExtra(REQUEST_CODE, requestCode);
                i.putExtra(NOTIFICATION, notification);
                sendOrderedBroadcast(i, PERM_PRIVATE, null, null,
                        Activity.RESULT_OK, null, null);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });

        Log.i(TAG, "Received an intent" + intent);

    }
}
