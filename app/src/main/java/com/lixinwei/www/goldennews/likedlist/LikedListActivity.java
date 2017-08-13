package com.lixinwei.www.goldennews.likedlist;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.lixinwei.www.goldennews.R;
import com.lixinwei.www.goldennews.base.BaseActivity;
import com.lixinwei.www.goldennews.commentslist.CommentsActivity;
import com.lixinwei.www.goldennews.util.ActivityUtils;
import com.lixinwei.www.goldennews.util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by welding on 2017/7/3.
 */

public class LikedListActivity extends BaseActivity {
    @BindView(R.id.toolbar_liked)
    Toolbar mToolbar;
    @BindView(R.id.coordinatorLayout)
    CoordinatorLayout mContentRoot;

    public static Intent newIntent(Context context) {
        return new Intent(context, LikedListActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_list);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_navigation);
        ab.setDisplayHomeAsUpEnabled(true);

        LikedListFragment likedListFragment =
                (LikedListFragment) getSupportFragmentManager().findFragmentById(R.id.container_liked);

        if(likedListFragment == null) {
            likedListFragment = LikedListFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    likedListFragment, R.id.container_liked);
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

    @Override
    public void onBackPressed() {
        mContentRoot.animate()
                .translationY(Utils.getScreenHeight(this))
                .setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        finish();
                        overridePendingTransition(0, 0);
                    }
                })
                .start();
    }
}
