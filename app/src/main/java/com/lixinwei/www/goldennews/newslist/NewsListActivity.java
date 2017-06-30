package com.lixinwei.www.goldennews.newslist;

import android.os.Bundle;

import com.lixinwei.www.goldennews.R;
import com.lixinwei.www.goldennews.base.BaseActivity;
import com.lixinwei.www.goldennews.util.ActivityUtils;


public class NewsListActivity extends BaseActivity {


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

    }

}
