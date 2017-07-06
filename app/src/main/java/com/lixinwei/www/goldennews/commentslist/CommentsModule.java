package com.lixinwei.www.goldennews.commentslist;

import com.lixinwei.www.goldennews.util.PerFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by welding on 2017/7/6.
 */
@Module
public class CommentsModule {
    @PerFragment
    @Provides
    CommentsContract.Presenter provideCommentsContractPresenter(CommentsPresenter presenter) {
        return presenter;
    }
}
