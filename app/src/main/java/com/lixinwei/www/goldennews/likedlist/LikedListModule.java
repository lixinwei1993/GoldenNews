package com.lixinwei.www.goldennews.likedlist;

import com.lixinwei.www.goldennews.util.PerFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by welding on 2017/7/3.
 */
@Module
public class LikedListModule {
    @PerFragment
    @Provides
    LikedListContract.Presenter provideLikedListContractPresenter(LikedListPresenter presenter) {
        return presenter;
    }
}
