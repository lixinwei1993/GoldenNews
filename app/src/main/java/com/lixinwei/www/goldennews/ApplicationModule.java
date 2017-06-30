package com.lixinwei.www.goldennews;

import android.content.Context;

import dagger.Module;
import dagger.Provides;

/**
 * Created by welding on 2017/6/29.
 */
@Module
public class ApplicationModule {
    private final Context mContext;

    ApplicationModule(Context context) {
        mContext = context;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }
}
