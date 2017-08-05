package com.lixinwei.www.goldennews.data.sharedPreferences;

import android.content.Context;
import android.preference.PreferenceManager;

/**
 * Created by welding on 2017/8/5.
 */

public class PreferencesServiceImpl {
    private static final String PREF_NEWEST_STORY = "newestStory";
    private static final String PREF_IS_ALARM_ON = "isAlarmOn";

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

}
