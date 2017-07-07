package com.lixinwei.www.goldennews.DateNewsList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.lixinwei.www.goldennews.R;
import com.lixinwei.www.goldennews.base.BaseActivity;
import com.lixinwei.www.goldennews.likedlist.LikedListActivity;
import com.lixinwei.www.goldennews.util.ActivityUtils;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by welding on 2017/7/7.
 */

public class DateNewsListActivity extends BaseActivity {
    private static final String EXTRA_DATE = "com.lixinwei.www.goldennews.DateNewsList.date";
    @BindView(R.id.toolbar_date)
    Toolbar mToolbar;

    public static Intent newIntent(Context context, String date) {
        Intent intent = new Intent(context, DateNewsListActivity.class);
        intent.putExtra(EXTRA_DATE, date);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_news_list);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_navigation);
        ab.setDisplayHomeAsUpEnabled(true);

        DateNewsListFragment likedListFragment =
                (DateNewsListFragment) getSupportFragmentManager().findFragmentById(R.id.container_date);

        //TODO
        if(likedListFragment == null) {
            String date = getIntent().getStringExtra(EXTRA_DATE);
            likedListFragment = DateNewsListFragment.newInstance(date);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    likedListFragment, R.id.container_date);
        }

        //TODO
        if (savedInstanceState != null) {

        }

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        //TODO
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //666 Open the navigation drawer when the home icon is selected from the toolbar.
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
