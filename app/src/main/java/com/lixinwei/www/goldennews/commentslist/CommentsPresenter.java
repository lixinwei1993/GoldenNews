package com.lixinwei.www.goldennews.commentslist;

import android.content.Context;
import android.util.Log;

import com.lixinwei.www.goldennews.data.model.Comment;
import com.lixinwei.www.goldennews.data.model.ShortComments;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ShortComments>() {
                    @Override
                    public void onNext(@NonNull ShortComments shortComments) {
                        mView.showShortComments(shortComments.getComments());
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
    public void loadLongComments(long id) {}

    @Override
    public void bindView(CommentsContract.View view) {
        mView = view;
    }

    @Override
    public void unbindView() {
        //TODO 在这里取消注册的话会出现只能在第一次加载comment，后面不能加载comment（comment为空白的情况），待分析，还有好像（不太确定）新版的RxJava建议不要频繁注册与取消注册？？
        //why dispose: stop Observable emmit items immediately!! reduce unnecessary workload
        //if(mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
          //  mCompositeDisposable.dispose();     //dispose main thread's subscription
        //}

        mCommentsObservableManager.dispose();

        mView = null;
    }
}