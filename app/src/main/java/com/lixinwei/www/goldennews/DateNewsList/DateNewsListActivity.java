package com.lixinwei.www.goldennews.DateNewsList;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;

import com.lixinwei.www.goldennews.R;
import com.lixinwei.www.goldennews.base.BaseActivity;
import com.lixinwei.www.goldennews.likedlist.LikedListActivity;
import com.lixinwei.www.goldennews.newslist.DatePickerFragment;
import com.lixinwei.www.goldennews.util.ActivityUtils;
import com.lixinwei.www.goldennews.util.Utils;

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
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout mContentRoot;

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
            if (savedInstanceState == null) {
                mContentRoot.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        mContentRoot.getViewTreeObserver().removeOnPreDrawListener(this);
                        startIntroAnimation();
                        return true;
                    }
                });
            }

        }
    }

    private void startIntroAnimation() {
        mContentRoot.setScaleY(0.1f);
        mContentRoot.setPivotY(-10);
        //llAddComment.setTranslationY(100);

        mContentRoot.animate()
                .scaleY(1)
                .setDuration(200)
                .setInterpolator(new AccelerateInterpolator())
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        ViewCompat.setElevation(mToolbar, Utils.dpToPx(8));
                    }
                })
                .start();
    }

    @Override
    public void onBackPressed() {
        mContentRoot.animate()
                .translationY(Utils.getScreenHeight(this))
                .setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        DateNewsListActivity.super.onBackPressed();
                        overridePendingTransition(0, 0);
                    }
                })
                .start();
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
