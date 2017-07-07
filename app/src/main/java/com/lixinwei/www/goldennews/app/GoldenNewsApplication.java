package com.lixinwei.www.goldennews.app;

import android.app.Application;
import android.content.Context;


import com.lixinwei.www.goldennews.commentslist.CommentsModule;
import com.lixinwei.www.goldennews.commentslist.CommentsSubComponent;
import com.lixinwei.www.goldennews.likedlist.LikedListModule;
import com.lixinwei.www.goldennews.likedlist.LikedListSubComponent;
import com.lixinwei.www.goldennews.newsDetail.NewsDetailModule;
import com.lixinwei.www.goldennews.newsDetail.NewsDetailSubComponent;
import com.lixinwei.www.goldennews.newslist.NewsListModule;
import com.lixinwei.www.goldennews.newslist.NewsListSubComponent;

/**
 * Created by welding on 2017/6/29.
 */

public class GoldenNewsApplication extends Application {
    private ApplicationComponent mApplicationComponent;
    private NewsListSubComponent mNewsListSubComponent;
    private LikedListSubComponent mLikedListSubComponent;
    private CommentsSubComponent mCommentsSubComponent;
    private NewsDetailSubComponent mNewsDetailSubComponent;

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

    public CommentsSubComponent getCommentsSubComponent() {
        if(mCommentsSubComponent == null) {
            mCommentsSubComponent = mApplicationComponent.plus(new CommentsModule());
        }

        return mCommentsSubComponent;
    }

    public void releaseCommentsSubComponent() {
        mCommentsSubComponent = null;
    }

    public NewsDetailSubComponent getNewsDetailSubComponent() {
        if(mNewsDetailSubComponent == null) {
            mNewsDetailSubComponent = mApplicationComponent.plus(new NewsDetailModule());
        }

        return mNewsDetailSubComponent;
    }

    public void releaseNewsDetailSubComponent() {
        mNewsDetailSubComponent = null;
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
