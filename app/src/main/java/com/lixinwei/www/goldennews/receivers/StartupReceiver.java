package com.lixinwei.www.goldennews.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.preference.PreferenceManager;

import com.lixinwei.www.goldennews.data.sharedPreferences.PreferencesServiceImpl;
import com.lixinwei.www.goldennews.services.PollService;

/**
 * Created by welding on 2017/8/5.
 */

public class StartupReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        boolean isOn = PreferencesServiceImpl.isAlarmOn(context);
        PollService.setServiceAlarm(context, isOn);
    }
}
