package com.lixinwei.www.goldennews.newsDetail;

import com.lixinwei.www.goldennews.util.PerFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by welding on 2017/7/6.
 */
@Module
public class NewsDetailModule {
    @PerFragment
    @Provides
    NewsDetailContract.Presenter provideNewsDetailContractPresenter(NewsDetailPresenter presenter) {
        return presenter;
    }
}
