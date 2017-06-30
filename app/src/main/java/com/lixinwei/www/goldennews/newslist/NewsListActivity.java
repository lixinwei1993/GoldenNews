package com.lixinwei.www.goldennews.newslist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lixinwei.www.goldennews.GoldenNewsApplication;
import com.lixinwei.www.goldennews.R;
import com.lixinwei.www.goldennews.util.ActivityUtils;

import javax.inject.Inject;

public class NewsListActivity extends AppCompatActivity {

    @Inject NewsListPresenter mNewsListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        NewsListFragment newsListFragment =
                (NewsListFragment) getSupportFragmentManager().findFragmentById(R.id.container_news_list);
        if (newsListFragment == null) {
            newsListFragment = NewsListFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
            newsListFragment, R.id.container_news_list);
        }

        DaggerNewsListComponent.builder()
                .applicationComponent(((GoldenNewsApplication) getApplication()).getApplicationComponent())
                .newsListModule(new NewsListModule(newsListFragment))
                .build()
                .inject(this);
    }



}
