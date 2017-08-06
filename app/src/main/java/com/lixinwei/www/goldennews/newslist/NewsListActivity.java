package com.lixinwei.www.goldennews.newslist;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.lixinwei.www.goldennews.R;
import com.lixinwei.www.goldennews.base.BaseActivity;
import com.lixinwei.www.goldennews.likedlist.LikedListActivity;
import com.lixinwei.www.goldennews.util.ActivityUtils;

import butterknife.BindView;
import butterknife.ButterKnife;


public class NewsListActivity extends BaseActivity {
    private static final String DIALOG_DATE = "DialogDate";

    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.nav_view)
    NavigationView mNavigationView;
    @BindView(R.id.fab_pick_date)
    FloatingActionButton mFloatingActionButton;

    ActionBarDrawerToggle mDrawerToggle; //used to Animate the Hamburger Icon

    public static Intent newIntent(Context context) {
        return new Intent(context, NewsListActivity.class);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_list);

        ButterKnife.bind(this);

        //set up the toolbar,总结项目时基本原件的使用流程也要总结
        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        //set up the navigation drawer
        mDrawerLayout.setStatusBarBackground(R.color.colorAccent);  //TODO not working
        if (mNavigationView != null) {
            setupDrawerContent();    //set up listener
        }

        //used to Animate the Hamburger Icon
        mDrawerToggle =
                new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open,  R.string.drawer_close);
        mDrawerLayout.addDrawerListener(mDrawerToggle);

        NewsListFragment newsListFragment =
                (NewsListFragment) getSupportFragmentManager().findFragmentById(R.id.container_news_list);

        if (newsListFragment == null) {
            newsListFragment = NewsListFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(),
                newsListFragment, R.id.container_news_list);
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
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
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
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent() {
        mNavigationView.setItemIconTintList(null); //TODO 为了让item的图片显示原色，而不是根据状态显示颜色

        Menu menu = mNavigationView.getMenu();
        MenuItem nightSwitchItem = menu.findItem(R.id.night_mode_switch);
        View nightAction = MenuItemCompat.getActionView(nightSwitchItem);
        SwitchCompat nightSwitch = nightAction.findViewById(R.id.switch_compat);
        nightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    Log.i("MAIN", "HELLO");

            }
        });

        MenuItem notificationSwitchItem = menu.findItem(R.id.notification_switch);
        View notificationAction = MenuItemCompat.getActionView(notificationSwitchItem);
        SwitchCompat notificationSwitch = notificationAction.findViewById(R.id.switch_compat);
        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b)
                    Log.i("MAIN", "HIIIII");

            }
        });



        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.action_about:

                                mDrawerLayout.closeDrawers();
                                break;
                            case R.id.action_settings:

                                mDrawerLayout.closeDrawers();
                                break;
                        }


                        return true;
                    }
                }
        );
    }

}
