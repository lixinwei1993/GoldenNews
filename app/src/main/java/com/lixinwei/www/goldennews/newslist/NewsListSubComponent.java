package com.lixinwei.www.goldennews.newslist;

import com.lixinwei.www.goldennews.util.PerFragment;

import dagger.Subcomponent;

/**
 * Created by welding on 2017/6/29.
 */
@PerFragment
@Subcomponent( modules = NewsListModule.class)
public interface NewsListSubComponent {

    void inject(NewsListFragment fragment);
}
