package com.lixinwei.www.goldennews;

import android.app.Application;
import android.content.Context;

import com.lixinwei.www.goldennews.newslist.NewsListContract;
import com.lixinwei.www.goldennews.newslist.NewsListModule;
import com.lixinwei.www.goldennews.newslist.NewsListSubComponent;

/**
 * Created by welding on 2017/6/29.
 */

public class GoldenNewsApplication extends Application {
    private ApplicationComponent mApplicationComponent;

    public static GoldenNewsApplication getGoldenNewsApplication(Context context) {
        return (GoldenNewsApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponent();
    }

    private void initAppComponent() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public NewsListSubComponent getNewsListSubComponent() {
        return mApplicationComponent.plus(new NewsListModule());
    }
}
