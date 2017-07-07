package com.lixinwei.www.goldennews.commentslist;

import android.content.Context;
import android.util.Log;

import com.lixinwei.www.goldennews.data.model.Comment;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by welding on 2017/7/6.
 */

public class CommentsPresenter implements CommentsContract.Presenter {
    private CommentsContract.View mView;
    private CompositeDisposable mCompositeDisposable;

    @Inject
    Context mContext;
    @Inject
    CommentsObservableManager mCommentsObservableManager;

    @Inject
    public CommentsPresenter() {
        mCompositeDisposable = new CompositeDisposable();
    }

    @Override
    public void loadShortComments(long id) {
        mView.setLoadingIndicator(true);



        Disposable disposable = mCommentsObservableManager.loadShortComments(id)
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Comment>>() {

                    @Override
                    public void onSuccess(@NonNull List<Comment> comments) {
                        mView.showShortComments(comments);
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
    public void loadLongComments(long id) {}

    /*@Override
    public void loadLongComments(long id) {
        mView.setLoadingIndicator(true);
        Disposable disposable = mCommentsObservableManager.loadLongComments(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<Comment>>() {
                    @Override
                    public void onSuccess(@NonNull List<Comment> comments) {
                        mView.showLongComments(comments);
                    }
                    @Override
                    public void onError(@NonNull Throwable e) {
                        mView.showLoadErrorSnackbar();
                    }
                });
    }*/

    @Override
    public void bindView(CommentsContract.View view) {
        mView = view;
    }

    @Override
    public void unbindView() {
        //why dispose: stop Observable emmit items immediately!! reduce unnecessary workload
        if(mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();     //dispose main thread's subscription
        }

        mView = null;
    }
}