package com.lixinwei.www.goldennews.commentslist;

import android.util.Log;

import com.lixinwei.www.goldennews.data.domain.ZhihuService;
import com.lixinwei.www.goldennews.data.model.Comment;
import com.lixinwei.www.goldennews.data.model.LongComments;
import com.lixinwei.www.goldennews.data.model.ShortComments;
import com.lixinwei.www.goldennews.data.model.StoryForNewsList;
import com.lixinwei.www.goldennews.util.PerFragment;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.ReplaySubject;

/**
 * Created by welding on 2017/7/6.
 */
@PerFragment
public class CommentsObservableManager {
    private ZhihuService mZhihuService;
    private ReplaySubject<ShortComments> mShortCommentsReplaySubject;

    @Inject
    public CommentsObservableManager(ZhihuService zhihuService) {
        mZhihuService = zhihuService;
    }

    public Observable<ShortComments> loadShortComments(long id) {

        if(mShortCommentsReplaySubject == null) {
            Log.i("Main", "comment");

            mShortCommentsReplaySubject = ReplaySubject.create();
            mZhihuService.getShortComments(id).subscribeOn(Schedulers.io())
                    .subscribe(mShortCommentsReplaySubject);
        }

        return mShortCommentsReplaySubject;
    }


    public Observable<Comment> loadLongComments(long id) {

        //TODO 使用map直接转为list<Comment>时会出错，而这样使用flatMap却不会出错，自己的理解还是不够
        return  mZhihuService.getLongComments(id).subscribeOn(Schedulers.io())
                .flatMap(new Function<LongComments, ObservableSource<Comment>>() {
                    @Override
                    public ObservableSource<Comment> apply(@NonNull LongComments longComments) throws Exception {
                        return Observable.fromIterable(longComments.getComments());
                    }
                });
    }

    public void dispose() {
        mShortCommentsReplaySubject = null;
    }

}