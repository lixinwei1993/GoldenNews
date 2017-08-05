package com.lixinwei.www.goldennews.receivers;

import android.app.Activity;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationManagerCompat;

import com.lixinwei.www.goldennews.services.PollService;

/**
 * Created by welding on 2017/8/5.
 */

public class NotificationReceiver extends BroadcastReceiver {
    //TODO 作为顺序发送的broadcast的最后一个接受者，即作为result receiver。
    //面试考点：如何判定可见性并在可见时不发送Intent！！
    @Override
    public void onReceive(Context context, Intent intent) {
        if (getResultCode() != Activity.RESULT_OK) {
// A foreground activity cancelled the broadcast
            return;
        }
        int requestCode = intent.getIntExtra(PollService.REQUEST_CODE, 0);
        Notification notification = (Notification)
                intent.getParcelableExtra(PollService.NOTIFICATION);
        NotificationManagerCompat notificationManager =
                NotificationManagerCompat.from(context);
        notificationManager.notify(requestCode, notification);
    }
}
