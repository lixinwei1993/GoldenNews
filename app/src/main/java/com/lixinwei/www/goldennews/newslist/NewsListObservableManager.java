package com.lixinwei.www.goldennews.newslist;

import com.lixinwei.www.goldennews.data.domain.ZhihuService;
import com.lixinwei.www.goldennews.data.model.DailyStories;
import com.lixinwei.www.goldennews.data.model.DateDailyStories;
import com.lixinwei.www.goldennews.data.model.Story;
import com.lixinwei.www.goldennews.data.model.StoryDetail;
import com.lixinwei.www.goldennews.data.model.StoryExtra;
import com.lixinwei.www.goldennews.data.model.StoryForNewsList;
import com.lixinwei.www.goldennews.data.model.TopStory;
import com.lixinwei.www.goldennews.util.PerFragment;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.ReplaySubject;

/**
 * Created by welding on 2017/7/2.
 */
//TODO 使用singleton有一个坏处就是整个App周期都不会重新进行该项网络请求，是否合理？？还是要弄懂PerFragment的原理或者后期进行优化
    //如对于获取某一日期的news时除了已有的判断条件还要加一个日期是否相同来决定要不要重新强制进行网络请求，具体以后再说，参考marvel
@PerFragment
public class NewsListObservableManager {
    private ZhihuService mZhihuService;
    private ReplaySubject<StoryForNewsList> mReplaySubject;
    private ReplaySubject<StoryForNewsList> mDateReplaySubject;

    @Inject
    public NewsListObservableManager(ZhihuService zhihuService) {
        mZhihuService = zhihuService;
    }

    public Observable<StoryForNewsList> loadDateDailyStories(String date) {
        if (mDateReplaySubject == null) {
            //TODO String dateString =

            mDateReplaySubject = ReplaySubject.create();
            mZhihuService.getDateDailyStories(date).subscribeOn(Schedulers.io())
                    .flatMap(new Function<DateDailyStories, ObservableSource<Story>>() {
                        @Override
                        public ObservableSource<Story> apply(@NonNull DateDailyStories dateDailyStories) throws Exception {
                            List<Story> stories = dateDailyStories.getStories();

                            return Observable.fromIterable(stories);
                        }
                    })
                    .flatMap(new Function<Story, ObservableSource<StoryForNewsList>>() {
                        @Override
                        public ObservableSource<StoryForNewsList> apply(@NonNull final Story story) throws Exception {
                            return Observable.zip(mZhihuService.getStoryExtra(story.getId()),
                                    mZhihuService.getStoryDetail(story.getId()),
                                    new BiFunction<StoryExtra, StoryDetail, StoryForNewsList>() {
                                        @Override
                                        public StoryForNewsList apply(@NonNull StoryExtra storyExtra, @NonNull StoryDetail storyDetail) throws Exception {
                                            StoryForNewsList storyForNewsList = new StoryForNewsList();
                                            storyForNewsList.setComments(storyExtra.getComments());
                                            storyForNewsList.setPopularity(storyExtra.getPopularity());
                                            storyForNewsList.setImage(storyDetail.getImage());
                                            storyForNewsList.setId(story.getId());
                                            storyForNewsList.setTitle(story.getTitle());

                                            return storyForNewsList;
                                        }
                                    });
                        }
                    })
                    .subscribe(mDateReplaySubject);

        }

        return mDateReplaySubject;
    }

    public Observable<StoryForNewsList> loadDailyStories(boolean forceUpdate) {

        if (forceUpdate || mReplaySubject == null)
        {

            mReplaySubject = ReplaySubject.create();

            //这里抛出exception的情况还可以再细化，自定义exception，参考GeekNews或marvel

            //TODO 这里搞这么复杂，其实直接用getDateStories就可以了，不用这么麻烦的！！

            mZhihuService.getDailyStories().subscribeOn(Schedulers.io())
                    .flatMap(new Function<DailyStories, ObservableSource<TopStory>>() {
                        @Override
                        public ObservableSource<TopStory> apply(@NonNull DailyStories dailyStories) throws Exception {
                            List<TopStory> topStories = dailyStories.getTopStories();

                            List<Story> stories = dailyStories.getStories();

                            for(Story story : stories) {
                                boolean alreadyExist = false;
                                for(TopStory topStory : topStories) {
                                    if(topStory.getId() == story.getId()) {
                                        alreadyExist = true;
                                        break;
                                    }
                                }
                                if( !alreadyExist ) topStories.add(new TopStory(story));
                            }

                            return Observable.fromIterable(topStories);
                        }
                    })
                    .flatMap(new Function<TopStory, ObservableSource<StoryForNewsList>>() {
                        @Override
                        public ObservableSource<StoryForNewsList> apply(@NonNull final TopStory topStory) throws Exception {
                            return  Observable.zip(mZhihuService.getStoryExtra(topStory.getId()),
                                    mZhihuService.getStoryDetail(topStory.getId()),
                                    new BiFunction<StoryExtra, StoryDetail, StoryForNewsList>() {
                                        @Override
                                        public StoryForNewsList apply(@NonNull StoryExtra storyExtra, @NonNull StoryDetail storyDetail) throws Exception {
                                            StoryForNewsList storyForNewsList = new StoryForNewsList();
                                            storyForNewsList.setComments(storyExtra.getComments());
                                            storyForNewsList.setPopularity(storyExtra.getPopularity());
                                            storyForNewsList.setImage(storyDetail.getImage());
                                            storyForNewsList.setId(topStory.getId());
                                            storyForNewsList.setTitle(topStory.getTitle());

                                            return storyForNewsList;
                                        }
                                    });
                        }
                    })
                    .subscribe(mReplaySubject);
        }

        return mReplaySubject;
    }

    /**
     * used to unsubscribe the ReplaySubject to the Observable,总感觉这里逻辑有问题，是不是应该手动将mReplay置null呢？但是已经设定了域
     * 总感觉这个方法可以不要
     */
    public void dispose() {
        mReplaySubject = null;
        mDateReplaySubject = null;
    }
}
