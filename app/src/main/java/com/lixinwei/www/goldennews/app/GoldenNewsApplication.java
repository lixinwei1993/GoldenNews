package com.lixinwei.www.goldennews.app;

import android.app.Application;
import android.content.Context;


import com.lixinwei.www.goldennews.likedlist.LikedListModule;
import com.lixinwei.www.goldennews.likedlist.LikedListSubComponent;
import com.lixinwei.www.goldennews.newslist.NewsListModule;
import com.lixinwei.www.goldennews.newslist.NewsListSubComponent;

/**
 * Created by welding on 2017/6/29.
 */

public class GoldenNewsApplication extends Application {
    private ApplicationComponent mApplicationComponent;
    private NewsListSubComponent mNewsListSubComponent;
    private LikedListSubComponent mLikedListSubComponent;

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

    public LikedListSubComponent getLikedListSubComponent() {
        if(mLikedListSubComponent == null) {
            mLikedListSubComponent = mApplicationComponent.plus(new LikedListModule());
        }

        return mLikedListSubComponent;
    }

    public void releaseLikedListSubComponent() {
        mLikedListSubComponent = null;
    }

    public NewsListSubComponent getNewsListSubComponent() {
        if(mNewsListSubComponent == null) {
            mNewsListSubComponent = mApplicationComponent.plus(new NewsListModule());
        }

        return mNewsListSubComponent;
    }

    public void releaseNewsListSubComponent() {
        mNewsListSubComponent = null;
    }
}
