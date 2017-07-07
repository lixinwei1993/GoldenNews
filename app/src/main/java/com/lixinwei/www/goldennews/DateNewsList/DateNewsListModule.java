package com.lixinwei.www.goldennews.DateNewsList;

import com.lixinwei.www.goldennews.util.PerFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by welding on 2017/7/7.
 */
@Module
public class DateNewsListModule {
    @PerFragment
    @Provides
    DateNewsListContract.Presenter provideDateNewsListContractPresenter(DateNewsListPresenter presenter) {
        return presenter;
    }
}
