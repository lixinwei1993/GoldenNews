package com.lixinwei.www.goldennews.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.util.Log;

import com.lixinwei.www.goldennews.R;
import com.lixinwei.www.goldennews.app.GoldenNewsApplication;
import com.lixinwei.www.goldennews.base.BaseActivity;
import com.lixinwei.www.goldennews.data.model.StoryForNewsList;
import com.lixinwei.www.goldennews.newslist.NewsListActivity;
import com.lixinwei.www.goldennews.newslist.NewsListObservableManager;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by welding on 2017/8/5.
 */

//TODO 面试点：splash无白色闪屏，秒开启！在activity的theme里直接将图片设为window的背景，这样在形成window时即可直接生成背景图片
//而不用等到setContentView即view绘制时才形成我们所需要的图片。

public class SplashActivity extends BaseActivity {
    @Inject
    NewsListObservableManager mNewsListObservableManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        GoldenNewsApplication.getGoldenNewsApplication(this).getNewsListSubComponent()
                .inject(this);

        mNewsListObservableManager.loadDailyStories(true).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<StoryForNewsList>() {
                    @Override
                    public void onNext(@NonNull StoryForNewsList storyForNewsList) {

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Intent intent = NewsListActivity.newIntent(SplashActivity.this);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onComplete() {
                        //TODO 面试点2：利用replaySubject，在splash中即进行一次网络请求的真更新，
                        // 且在网络请求的过程中利用对所有新闻要用到的图片进行一次网络请求充分利用picasso的缓存特性，加快存取速度
                        Intent intent = NewsListActivity.newIntent(SplashActivity.this);
                        startActivity(intent);
                        finish();
                    }
                });
    }
}
