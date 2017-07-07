package com.lixinwei.www.goldennews.newsDetail;

import android.content.Context;

import com.lixinwei.www.goldennews.data.Realm.RealmService;
import com.lixinwei.www.goldennews.data.domain.ZhihuService;
import com.lixinwei.www.goldennews.data.model.StoryDetail;

import java.util.Observable;

import javax.inject.Inject;

import io.reactivex.ObservableSource;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

/**
 * Created by welding on 2017/7/6.
 */

public class NewsDetailPresenter implements  NewsDetailContract.Presenter {
    private NewsDetailContract.View mView;
    private CompositeDisposable mCompositeDisposable;

    @Inject
    Context mContext;
    @Inject
    NewsDetailObservableManager mObservableManager;
    @Inject
    RealmService mRealmService;

    //TODO
    @Inject
    ZhihuService mZhihuService;

    @Inject
    public NewsDetailPresenter() {
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void loadStoryDetail(long id) {
        mView.setLoadingIndicator(true);

        Disposable disposable = mObservableManager.loadStoryDetail(id)
                .subscribeWith(new DisposableObserver<StoryDetail>() {  //TODO 这里尽管只发射一个，但是其仍是observable而不是single，不能用single类型的observer，否则会出错！！

                    @Override
                    public void onNext(@NonNull StoryDetail storyDetail) {
                        mView.showStoryDetail(storyDetail);
                        mRealmService.insertStoryRead(storyDetail.getId());
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.showLoadErrorSnackbar();
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    @Override
    public void bindView(NewsDetailContract.View view) {
        mView = view;
    }

    @Override
    public void unbindView() {
        mView = null;
        mObservableManager.dispose();
    }
}
