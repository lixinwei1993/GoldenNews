package com.lixinwei.www.goldennews.newslist;

import com.lixinwei.www.goldennews.data.domain.ZhihuService;

import dagger.Module;
import dagger.Provides;

/**
 * Created by welding on 2017/6/29.
 */
@Module
public class NewsListModule {
    private final NewsListContract.View mView;

    public NewsListModule(NewsListContract.View view) {
        mView = view;
    }

    @Provides
    NewsListContract.View provideNewsListContractView() {
        return mView;
    }

    @Provides
    NewsListPresenter provideNewsListContractPresenter(ZhihuService zhihuService, NewsListContract.View view) {
        return new NewsListPresenter(zhihuService, view);
    }

    @Provides
    NewsListAdapter provideNewsListAdapter() {
        return new NewsListAdapter();
    }

}
