package com.lixinwei.www.goldennews.likedlist;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.lixinwei.www.goldennews.R;
import com.lixinwei.www.goldennews.data.Realm.RealmService;
import com.lixinwei.www.goldennews.data.model.StoryForNewsList;
import com.lixinwei.www.goldennews.data.model.StoryLikedForRealm;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by welding on 2017/7/3.
 */

public class LikedListPresenter implements LikedListContract.Presenter {
    private LikedListContract.View mView;
    private CompositeDisposable mCompositeDisposable;

    @Inject
    Context mContext;
    @Inject
    LikedListObservableManager mObservableManager;
    @Inject
    RealmService mRealmService;

    @Inject
    public LikedListPresenter() {
        mCompositeDisposable = new CompositeDisposable();
    }


    @Override
    public void loadLikedStories() {
        mView.setLoadingIndicator(true);

        Disposable disposable = mObservableManager.loadLikedStories()
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<StoryLikedForRealm>>() {
                    @Override
                    public void onSuccess(@NonNull List<StoryLikedForRealm> storyLikedForRealms) {
                        mView.showLikedStories(storyLikedForRealms);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.showLoadErrorSnackbar();
                    }
                });

        mCompositeDisposable.add(disposable);

        mView.setLoadingIndicator(false);
    }

    @Override
    public void deleteLikedStory(Long id) {
        mRealmService.deleteLikedStory(id);
    }

    @Override
    public void bindView(LikedListContract.View view) {
        mView = view;
    }

    @Override
    public void unbindView() {
        if(mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();     //dispose main thread's subscription
        }

        mObservableManager.dispose();   //dispose ReplaySubject's subscription

        mView = null;
    }

    @Override
    public void startDetailActivity(long id) {
        mView.startDetailActivity(id);
    }
}
