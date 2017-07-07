package com.lixinwei.www.goldennews.DateNewsList;

import com.lixinwei.www.goldennews.util.PerFragment;

import dagger.Subcomponent;

/**
 * Created by welding on 2017/7/7.
 */
@PerFragment
@Subcomponent(modules = DateNewsListModule.class)
public interface DateNewsListSubComponent {

    void inject(DateNewsListFragment fragment);

}
