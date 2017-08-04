package com.lixinwei.www.goldennews.commentslist;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.lixinwei.www.goldennews.R;
import com.lixinwei.www.goldennews.base.BaseActivity;
import com.lixinwei.www.goldennews.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by welding on 2017/7/6.
 */

public class CommentsActivity extends BaseActivity {
    private static final String EXTRA_ID =
            "com.lixinwei.www.goldennews.commentslist.id";

    private long mId;

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
