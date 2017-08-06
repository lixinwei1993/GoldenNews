package com.lixinwei.www.goldennews.settings;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.design.widget.Snackbar;

import com.lixinwei.www.goldennews.R;
import com.lixinwei.www.goldennews.data.sharedPreferences.PreferencesServiceImpl;
import com.lixinwei.www.goldennews.services.PollService;

/**
 * Created by welding on 2017/8/6.
 */

public class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {
    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(PollService.ACTION_SHOW_NOTIFICATION);
        getActivity().registerReceiver(mOnShowNotification, filter, PollService.PERM_PRIVATE, null);
    }
    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(mOnShowNotification);
    }

    private BroadcastReceiver mOnShowNotification = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            setResultCode(Activity.RESULT_CANCELED);
        }
    };

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        if(s.equals(PreferencesServiceImpl.PREF_NOTIFICATION_FREQ))
        {
            PollService.setServiceAlarm(getActivity(), PreferencesServiceImpl.isAlarmOn(getActivity()));
            int dura = PreferencesServiceImpl.getNotificationFrequency(getActivity());
            String ss;
            switch(dura) {
                case 60000:
                    ss = "1 min";
                    break;
                default:
                    ss = dura/3600000 + " hour";
                    break;
            }
            Snackbar.make(getActivity().findViewById(android.R.id.content), "通知频率已改为：" + ss, Snackbar.LENGTH_SHORT).show();
        }

    }
}
