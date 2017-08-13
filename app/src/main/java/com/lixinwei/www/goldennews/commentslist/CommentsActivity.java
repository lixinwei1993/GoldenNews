package com.lixinwei.www.goldennews.commentslist;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;

import com.lixinwei.www.goldennews.R;
import com.lixinwei.www.goldennews.base.BaseActivity;
import com.lixinwei.www.goldennews.util.ActivityUtils;
import com.lixinwei.www.goldennews.util.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by welding on 2017/7/6.
 */

public class CommentsActivity extends BaseActivity {
    private static final String EXTRA_ID =
            "com.lixinwei.www.goldennews.commentslist.id";

    private long mId;

    @BindView(R.id.content_root)
    CoordinatorLayout mContentRoot;
    @BindView(R.id.toolbar_comments)
    Toolbar mToolbar;

    public static Intent newIntent(Context context, long id) {
        Intent intent = new Intent(context, CommentsActivity.class);
        intent.putExtra(EXTRA_ID, id);

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_navigation);
        ab.setDisplayHomeAsUpEnabled(true);

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

        CommentsFragment commentsFragment
                = (CommentsFragment) getSupportFragmentManager().findFragmentById(R.id.container_comments);

        if(commentsFragment == null) {
            commentsFragment = CommentsFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                    commentsFragment, R.id.container_comments);
        }
        mId = getIntent().getLongExtra(EXTRA_ID, 0);    //TODO 面试// getIntent总是返回启动该activity的intent，即使是configuration change也是如此
        commentsFragment.setId(mId);
    }

    @Override
    public void onBackPressed() {
        mContentRoot.animate()
                .translationY(Utils.getScreenHeight(this))
                .setDuration(200)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        //CommentsActivity.super.onBackPressed();
                        //应该使用finish，而不适用onBackPressed！！
                        //TODO Reference:https://stackoverflow.com/a/32390821
                        finish();
                        overridePendingTransition(0, 0);
                    }
                })
                .start();
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
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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
