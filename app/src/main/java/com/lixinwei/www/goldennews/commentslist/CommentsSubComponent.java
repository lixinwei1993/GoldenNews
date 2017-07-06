package com.lixinwei.www.goldennews.commentslist;

import com.lixinwei.www.goldennews.util.PerFragment;

import dagger.Subcomponent;

/**
 * Created by welding on 2017/7/6.
 */
@PerFragment
@Subcomponent(modules = CommentsModule.class)
public interface CommentsSubComponent {

    void inject(CommentsFragment fragment);

}
