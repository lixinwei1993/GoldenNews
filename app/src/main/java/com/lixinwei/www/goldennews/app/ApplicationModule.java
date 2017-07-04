package com.lixinwei.www.goldennews.app;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by welding on 2017/6/29.
 */
@Module
public class ApplicationModule {
    private final GoldenNewsApplication mGoldenNewsApplication;

    ApplicationModule(GoldenNewsApplication application) {
        mGoldenNewsApplication = application;
    }

    //TODO for what? why need applicationContext?? may be redundant, to be deleted
    @Singleton
    @Provides
    Context provideContext() {
        return mGoldenNewsApplication.getApplicationContext();
    }
}
