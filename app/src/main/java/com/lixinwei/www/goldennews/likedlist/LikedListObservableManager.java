package com.lixinwei.www.goldennews.likedlist;

import com.lixinwei.www.goldennews.data.Realm.RealmService;
import com.lixinwei.www.goldennews.data.model.StoryLikedForRealm;
import com.lixinwei.www.goldennews.util.PerFragment;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.ReplaySubject;

/**
 * Created by welding on 2017/7/5.
 */
@PerFragment
public class LikedListObservableManager {

    @Inject
    RealmService mRealmService;

    private CompositeDisposable mDisposable; //没有必要要，好像subject对observable返回为空，具体以后再管这里先不处理
    private ReplaySubject<StoryLikedForRealm> mReplaySubject;

    @Inject
    public LikedListObservableManager() {

    }

    //TODO 下面这个函数整体上的设计时有问题的，每次都重建了replaysubject。因为mDisposable始终为空
    public Observable<StoryLikedForRealm> loadLikedStories() {
        if(mReplaySubject == null) {
            mReplaySubject = ReplaySubject.create();

            Observable.fromIterable(mRealmService.getLikedList())
                .observeOn(Schedulers.io())
                .subscribe(mReplaySubject);


        }

        return mReplaySubject;
    }

    public void dispose() {
        mReplaySubject = null;
    }

}
