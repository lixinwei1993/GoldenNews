package com.lixinwei.www.goldennews.DateNewsList;

import com.lixinwei.www.goldennews.data.domain.ZhihuService;
import com.lixinwei.www.goldennews.data.model.DateDailyStories;
import com.lixinwei.www.goldennews.data.model.Story;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.ReplaySubject;

/**
 * Created by welding on 2017/7/7.
 */

public class DateNewsListObservableManager {
    @Inject
    ZhihuService mZhihuService;

    private ReplaySubject<List<Story>> mReplaySubject;
    private String mDate;

    @Inject
    public DateNewsListObservableManager() {

    }

    public Observable<List<Story>> loadDateStorie(String date) {
        if(mReplaySubject == null || !mDate.equals(date)) {
            mDate = date;
            mReplaySubject = ReplaySubject.create();

            mZhihuService.getDateDailyStories(mDate)
                    .subscribeOn(Schedulers.io())
                    .map(new Function<DateDailyStories, List<Story>>() {
                        @Override
                        public List<Story> apply(@NonNull DateDailyStories dateDailyStories) throws Exception {
                            return dateDailyStories.getStories();
                        }
                    })
                    .subscribe(mReplaySubject);
        }

        return mReplaySubject;
    }

    public void dispose() {
        mReplaySubject = null;
    }
}
