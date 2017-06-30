package com.lixinwei.www.goldennews.newslist;

import com.lixinwei.www.goldennews.data.domain.ZhihuService;
import com.lixinwei.www.goldennews.data.model.DailyStories;
import com.lixinwei.www.goldennews.data.model.TopStory;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by welding on 2017/6/29.
 */

public class NewsListPresenter implements NewsListContract.Presenter {
    private final ZhihuService mZhihuService;           //model layer
    private final NewsListContract.View mNewsListView;  //view layer

    @Inject
    NewsListPresenter(ZhihuService zhihuService, NewsListContract.View view) {
        mZhihuService = zhihuService;
        mNewsListView = view;
    }

    /**
     *      目前model layer返回的是知乎service，而viewlayer只有一个view，可以在这里边想着
     *      代码的实现，边实时更新activity，fragment（view）以及两个interface
     */

    @Inject
    void setupListeners() {
        mNewsListView.setPresenter(this);
    }

    public void loadDailyStories() {
        mZhihuService.getDailyStories().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<DailyStories>() {
                    @Override
                    public void accept(@NonNull DailyStories dailyStories) throws Exception {
                        List<TopStory> topStories = dailyStories.getTopStories();
                        mNewsListView.showTopStories(topStories);
                    }
                });
    }







}
