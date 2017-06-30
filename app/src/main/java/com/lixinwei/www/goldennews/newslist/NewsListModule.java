package com.lixinwei.www.goldennews.newslist;

import com.lixinwei.www.goldennews.data.domain.ZhihuService;
import com.lixinwei.www.goldennews.util.PerFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Created by welding on 2017/6/29.
 */
@Module
public class NewsListModule {

    @PerFragment
    @Provides
    NewsListContract.Presenter provideNewsListContractPresenter(NewsListPresenter presenter) {
        return presenter;
    }
}
