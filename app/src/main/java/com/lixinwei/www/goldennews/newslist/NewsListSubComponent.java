package com.lixinwei.www.goldennews.newslist;

import com.lixinwei.www.goldennews.ApplicationComponent;
import com.lixinwei.www.goldennews.util.PerActivity;

import dagger.Component;
import dagger.Subcomponent;

/**
 * Created by welding on 2017/6/29.
 */
@PerActivity
@Subcomponent( modules = NewsListModule.class)
public interface NewsListSubComponent {

    void inject(NewsListFragment fragment);

    //NewsListAdapter adapter();
}
