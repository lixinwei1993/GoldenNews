package com.lixinwei.www.goldennews.app;

import android.app.Application;
import android.content.Context;


import com.lixinwei.www.goldennews.DateNewsList.DateNewsListModule;
import com.lixinwei.www.goldennews.DateNewsList.DateNewsListSubComponent;
import com.lixinwei.www.goldennews.commentslist.CommentsModule;
import com.lixinwei.www.goldennews.commentslist.CommentsSubComponent;
import com.lixinwei.www.goldennews.likedlist.LikedListModule;
import com.lixinwei.www.goldennews.likedlist.LikedListSubComponent;
import com.lixinwei.www.goldennews.newsDetail.NewsDetailModule;
import com.lixinwei.www.goldennews.newsDetail.NewsDetailSubComponent;
import com.lixinwei.www.goldennews.newslist.NewsListModule;
import com.lixinwei.www.goldennews.newslist.NewsListSubComponent;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;

/**
 * Created by welding on 2017/6/29.
 */

public class GoldenNewsApplication extends Application {
    private static final long PICASSO_DISK_CACHE_SIZE = 1024 * 1024 * 50;

    private ApplicationComponent mApplicationComponent;
    private NewsListSubComponent mNewsListSubComponent;
    private LikedListSubComponent mLikedListSubComponent;
    private CommentsSubComponent mCommentsSubComponent;
    private NewsDetailSubComponent mNewsDetailSubComponent;
    private DateNewsListSubComponent mDateNewsListSubComponent;

    public static GoldenNewsApplication getGoldenNewsApplication(Context context) {
        return (GoldenNewsApplication) context.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initAppComponent();

        //TODO 面试点：加大picasso的磁盘缓存大小，以便在没有网的时候能够把NEWSLIST页面即主页面的所有大图都显示出来
        Downloader downloader = new OkHttpDownloader(getApplicationContext(),
                PICASSO_DISK_CACHE_SIZE);
        Picasso picasso = new Picasso.Builder(getApplicationContext())
                .downloader(downloader).build();
    }

    private void initAppComponent() {
        mApplicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return mApplicationComponent;
    }

    public DateNewsListSubComponent getDateNewsListSubComponent() {
        if(mDateNewsListSubComponent == null) {
            mDateNewsListSubComponent = mApplicationComponent.plus(new DateNewsListModule());
        }

        return mDateNewsListSubComponent;
    }

    public void releaseDateNewsListSubComponent() {
        mDateNewsListSubComponent = null;
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
