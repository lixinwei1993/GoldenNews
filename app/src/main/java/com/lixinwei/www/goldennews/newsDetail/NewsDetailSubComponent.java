package com.lixinwei.www.goldennews.newsDetail;

import com.lixinwei.www.goldennews.util.PerFragment;

import dagger.Subcomponent;

/**
 * Created by welding on 2017/7/6.
 */
@PerFragment
@Subcomponent(modules = NewsDetailModule.class)
public interface NewsDetailSubComponent {

    void inject(NewsDetailActivity activity);

}
