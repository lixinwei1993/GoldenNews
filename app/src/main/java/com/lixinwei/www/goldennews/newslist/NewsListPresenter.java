package com.lixinwei.www.goldennews.newslist;

import android.util.Log;

import com.lixinwei.www.goldennews.data.Realm.RealmService;
import com.lixinwei.www.goldennews.data.domain.ZhihuService;
import com.lixinwei.www.goldennews.data.model.DailyStories;
import com.lixinwei.www.goldennews.data.model.StoryExtra;
import com.lixinwei.www.goldennews.data.model.StoryForRealm;
import com.lixinwei.www.goldennews.data.model.TopStory;
import com.lixinwei.www.goldennews.util.PerFragment;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by welding on 2017/6/29.
 */
@PerFragment //how this scope work? link the instance with the same scoped component?
public class NewsListPresenter implements NewsListContract.Presenter {
    private final ZhihuService mZhihuService;           //model layer
    private final RealmService mRealmService;
    private NewsListContract.View mNewsListView;  //view layer
    private int mTopstoriesSize; //store the #topStory for using in buffer in loadDailyStories

    @Inject
    NewsListPresenter(ZhihuService zhihuService, RealmService realmService) {
        mZhihuService = zhihuService;
        mRealmService = realmService;
    }

    /**
     *      目前model layer返回的是知乎service，而viewlayer只有一个view，可以在这里边想着
     *      代码的实现，边实时更新activity，fragment（view）以及两个interface
     */

    public void loadDailyStories() {
        mZhihuService.getDailyStories().subscribeOn(Schedulers.io())
                .flatMap(new Function<DailyStories, ObservableSource<TopStory>>() {
                    @Override
                    public ObservableSource<TopStory> apply(@NonNull DailyStories dailyStories) throws Exception {
                        List<TopStory> topStories = dailyStories.getTopStories();
                        mTopstoriesSize = topStories.size();
                        Log.d("Main", "" + mTopstoriesSize);
                        return Observable.fromIterable(topStories);
                    }
                })
                .flatMap(new Function<TopStory, ObservableSource<StoryForRealm>>() {
                    @Override
                    public ObservableSource<StoryForRealm> apply(@NonNull TopStory topStory) throws Exception {
                        StoryForRealm storyForRealm = new StoryForRealm();
                        storyForRealm.setId(topStory.getId());
                        storyForRealm.setImage(topStory.getImage());
                        storyForRealm.setTitle(topStory.getTitle());
                        return Observable.just(storyForRealm);
                    }
                })
                .flatMap(new Function<StoryForRealm, ObservableSource<StoryForRealm>>() {
                    //TODO is there a better way to combine service data rather than use nested subscribe
                    @Override
                    public ObservableSource<StoryForRealm> apply(@NonNull final StoryForRealm storyForRealm) throws Exception {
                        mZhihuService.getStoryExtra(storyForRealm.getId()).subscribe(new Consumer<StoryExtra>() {
                            @Override
                            public void accept(@NonNull StoryExtra storyExtra) throws Exception {
                                storyForRealm.setPopularity(storyExtra.getPopularity());
                                storyForRealm.setComments(storyExtra.getComments());
                            }
                        });

                        return Observable.just(storyForRealm);
                    }
                })
                .doOnNext(new Consumer<StoryForRealm>() {
                    @Override
                    public void accept(@NonNull StoryForRealm storyForRealm) throws Exception {
                        mRealmService.insertStory(storyForRealm);
                    }
                })
                .buffer(5)  //TODO is there a better way to buffer all rather than specific count? combine with time limit??
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<StoryForRealm>>() {
                    @Override
                    public void accept(@NonNull List<StoryForRealm> stories) throws Exception {
                        mNewsListView.showTopStories(stories);
                        //TODO directly store stories in db is not a good solution, how to cache? the transaction of realm's thread?
                        //TODO cache implement by Http client interceptor
                        //mRealmService.insertStories(stories);
                    }
                });
    }

    @Override
    public void bindView(NewsListContract.View view) {
        mNewsListView = view;
    }

    @Override
    public void unbindView() {
        mNewsListView = null;
    }







}
