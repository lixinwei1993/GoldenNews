package com.lixinwei.www.goldennews.newsDetail;

import com.lixinwei.www.goldennews.data.domain.ZhihuService;
import com.lixinwei.www.goldennews.data.model.StoryDetail;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.ReplaySubject;

/**
 * Created by welding on 2017/7/6.
 */

public class NewsDetailObservableManager {
    private ZhihuService mZhihuService;
    private ReplaySubject<StoryDetail> mReplaySubject;

    @Inject
    public NewsDetailObservableManager(ZhihuService zhihuService) {
        mZhihuService = zhihuService;
    }

    public Observable<StoryDetail> loadStoryDetail(long id) {

        if(mReplaySubject == null){
            mReplaySubject = ReplaySubject.create();
            mZhihuService.getStoryDetail(id).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread()).subscribe(mReplaySubject);
        }
        return mReplaySubject;
    }

    public void dispose() {
        mReplaySubject = null;
    }
}
