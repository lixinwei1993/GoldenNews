package com.lixinwei.www.goldennews.newslist;

import com.lixinwei.www.goldennews.data.domain.ZhihuService;
import com.lixinwei.www.goldennews.data.model.DailyStories;
import com.lixinwei.www.goldennews.data.model.StoryExtra;
import com.lixinwei.www.goldennews.data.model.StoryForRealm;
import com.lixinwei.www.goldennews.data.model.TopStory;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.ReplaySubject;

/**
 * Created by welding on 2017/7/2.
 */
@Singleton
public class NewsListObservableManager {
    private ZhihuService mZhihuService;
    private Disposable mDisposable;
    private ReplaySubject<StoryForRealm> mReplaySubject;

    @Inject
    public NewsListObservableManager(ZhihuService zhihuService) {
        mZhihuService = zhihuService;
    }

    public Observable<StoryForRealm> loadDailyStories() {
        if (mDisposable == null || mDisposable.isDisposed()) //这个if句很重要，常规套路
        {

            mReplaySubject = ReplaySubject.create();

            mZhihuService.getDailyStories().subscribeOn(Schedulers.io())
                    .flatMap(new Function<DailyStories, ObservableSource<TopStory>>() {
                        @Override
                        public ObservableSource<TopStory> apply(@NonNull DailyStories dailyStories) throws Exception {
                            List<TopStory> topStories = dailyStories.getTopStories();
                            return Observable.fromIterable(topStories);
                        }
                    })
                    .flatMap(new Function<TopStory, ObservableSource<StoryForRealm>>() {
                        @Override
                        public ObservableSource<StoryForRealm> apply(@NonNull final TopStory topStory) throws Exception {
                            return mZhihuService.getStoryExtra(topStory.getId())
                                    .map(new Function<StoryExtra, StoryForRealm>() {
                                        @Override
                                        public StoryForRealm apply(@NonNull StoryExtra storyExtra) throws Exception {
                                            StoryForRealm storyForRealm = new StoryForRealm();
                                            storyForRealm.setComments(storyExtra.getComments());
                                            storyForRealm.setPopularity(storyExtra.getPopularity());
                                            storyForRealm.setImage(topStory.getImage());
                                            storyForRealm.setId(topStory.getId());
                                            storyForRealm.setTitle(topStory.getTitle());

                                            return storyForRealm;
                                        }
                                    });
                        }
                    })
                    .subscribe(mReplaySubject);
        }

        return mReplaySubject;
    }

    /**
     * used to unsubscribe the ReplaySubject to the Observable
     */
    public void dispose() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }
}
