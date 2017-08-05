package com.lixinwei.www.goldennews.DateNewsList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.lixinwei.www.goldennews.R;
import com.lixinwei.www.goldennews.base.BaseActivity;
import com.lixinwei.www.goldennews.likedlist.LikedListActivity;
import com.lixinwei.www.goldennews.newslist.DatePickerFragment;
import com.lixinwei.www.goldennews.util.ActivityUtils;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by welding on 2017/7/7.
 */

public class DateNewsListActivity extends BaseActivity {
    private static final String EXTRA_DATE = "com.lixinwei.www.goldennews.DateNewsList.date";
    private static final String DIALOG_DATE = "DialogDate";

    @BindView(R.id.toolbar_date)
    Toolbar mToolbar;
    @BindView(R.id.fab_pick_date)
    FloatingActionButton mFloatingActionButton;

    DateNewsListFragment mDateNewsListFragment;

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

        mDateNewsListFragment =
                (DateNewsListFragment) getSupportFragmentManager().findFragmentById(R.id.container_date);

        //TODO
        if(mDateNewsListFragment == null) {
            String date = getIntent().getStringExtra(EXTRA_DATE);
            mDateNewsListFragment = DateNewsListFragment.newInstance(date);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    mDateNewsListFragment, R.id.container_date);
        }

        mFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getSupportFragmentManager();
                DatePickerFragment dialog = new DatePickerFragment();
                dialog.show(manager, DIALOG_DATE);
            }
        });

        //TODO
        if (savedInstanceState != null) {

        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        String date = intent.getStringExtra(EXTRA_DATE);

        mDateNewsListFragment.dateChanged(date);
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
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
