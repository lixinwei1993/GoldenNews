package com.lixinwei.www.goldennews.data.sharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import com.lixinwei.www.goldennews.services.PollService;

/**
 * Created by welding on 2017/8/5.
 */

public class PreferencesServiceImpl {
    private static final String PREF_NEWEST_STORY = "newestStory";
    private static final String PREF_IS_ALARM_ON = "isAlarmOn";
    private static final String PREF_IS_NIGHT_MODE_ON = "isNightModeOn";
    public static final String PREF_NOTIFICATION_FREQ = "pref_notification_frequency";

    public static long getNewestStory(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getLong(PREF_NEWEST_STORY, 0);
    }

    public static void setNewestStory(Context context, long id) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putLong(PREF_NEWEST_STORY, id)
                .apply();
    }

    public static boolean isNightModeOn(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(PREF_IS_NIGHT_MODE_ON, false);
    }
    public static void setNightModeOn(Context context, boolean isOn) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(PREF_IS_NIGHT_MODE_ON, isOn)
                .apply();
    }

    public static boolean isAlarmOn(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean(PREF_IS_ALARM_ON, true);
    }
    public static void setAlarmOn(Context context, boolean isOn) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putBoolean(PREF_IS_ALARM_ON, isOn)
                .apply();
    }

    public static int getNotificationFrequency(Context context) {
        String s = PreferenceManager.getDefaultSharedPreferences(context)
                .getString(PREF_NOTIFICATION_FREQ, "14400000");


        return Integer.parseInt(s);

    }

    public static void setPrefListener(final Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).registerOnSharedPreferenceChangeListener(
                new SharedPreferences.OnSharedPreferenceChangeListener() {
                    @Override
                    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
                        if(s.equals(PREF_NOTIFICATION_FREQ)) {
                            notificationFreqChanged(context);
                            Log.i("XXXX", "" + getNotificationFrequency(context));
                        }
                    }
                }
        );
    }

    public static void notificationFreqChanged(Context context) {
        PollService.setServiceAlarm(context, isAlarmOn(context));
    }

}
