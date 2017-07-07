package com.lixinwei.www.goldennews.DateNewsList;

import android.content.Context;

import com.lixinwei.www.goldennews.data.model.Story;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

/**
 * Created by welding on 2017/7/7.
 */

public class DateNewsListPresenter implements DateNewsListContract.Presenter {

    private DateNewsListContract.View mView;
    private CompositeDisposable mCompositeDisposable;

    @Inject
    Context mContext;
    @Inject
    DateNewsListObservableManager mDateNewsListObservableManager;

    @Inject
    DateNewsListPresenter() {
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void loadDateStories(String date) {
        mView.setLoadingIndicator(true);

        Disposable disposable = mDateNewsListObservableManager.loadDateStorie(date)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<List<Story>>() {
                    @Override
                    public void onNext(@NonNull List<Story> stories) {
                        mView.showDateStories(stories);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.showLoadErrorSnackbar();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        mCompositeDisposable.add(disposable);

        mView.setLoadingIndicator(false);

    }

    @Override
    public void bindView(DateNewsListContract.View view) {
        mView = view;
    }

    @Override
    public void unbindView() {
        //why dispose: stop Observable emmit items immediately!! reduce unnecessary workload
        if(mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();     //dispose main thread's subscription
        }

        mDateNewsListObservableManager.dispose();   //dispose ReplaySubject's subscription

        mView = null;   //dereference view to let fragment can be GC
    }


}
