package com.lixinwei.www.goldennews.likedlist;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.lixinwei.www.goldennews.R;
import com.lixinwei.www.goldennews.base.BaseActivity;
import com.lixinwei.www.goldennews.util.ActivityUtils;

import butterknife.BindView;

/**
 * Created by welding on 2017/7/3.
 */

public class LikedListActivity extends BaseActivity {
    @BindView(R.id.toolbar_liked)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_liked_list);

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
}
