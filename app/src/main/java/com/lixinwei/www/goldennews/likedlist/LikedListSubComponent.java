package com.lixinwei.www.goldennews.likedlist;

import com.lixinwei.www.goldennews.util.PerFragment;

import dagger.Subcomponent;

/**
 * Created by welding on 2017/7/5.
 */
@PerFragment
@Subcomponent(modules = LikedListModule.class)
public interface LikedListSubComponent {

    void inject(LikedListFragment fragment);

}
