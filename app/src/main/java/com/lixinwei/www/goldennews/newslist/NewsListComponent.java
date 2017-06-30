package com.lixinwei.www.goldennews.newslist;

import com.lixinwei.www.goldennews.ApplicationComponent;
import com.lixinwei.www.goldennews.util.PerActivity;

import dagger.Component;

/**
 * Created by welding on 2017/6/29.
 */
@PerActivity
@Component( modules = NewsListModule.class,
            dependencies = ApplicationComponent.class)
public interface NewsListComponent {

    void inject(NewsListFragment fragment);

    //NewsListAdapter adapter();
}
