package com.lixinwei.www.goldennews.newslist;

import com.lixinwei.www.goldennews.data.model.StoryForRealm;
import com.lixinwei.www.goldennews.util.PerFragment;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by welding on 2017/6/29.
 */
@PerFragment //how this scope work? link the instance with the same scoped component?
public class NewsListPresenter implements NewsListContract.Presenter {
    private NewsListContract.View mNewsListView;  //view layer
    private CompositeDisposable mCompositeDisposable;

    @Inject
    NewsListObservableManager mNewsListObservableManager;

    @Inject
    NewsListPresenter() {
        mCompositeDisposable = new CompositeDisposable();
    }

    /**
     *      目前model layer返回的是知乎service，而viewlayer只有一个view，可以在这里边想着
     *      代码的实现，边实时更新activity，fragment（view）以及两个interface
     */

    //TODO 实现replaySubject，以避免频繁索取Observable或进行网络请求
    public void loadDailyStories() {
        Disposable disposable = mNewsListObservableManager.loadDailyStories()
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<StoryForRealm>>() {
                    //TODO 对上述operator等upstream发出的exception（使用subscriber的onError功能，不是简单的使用consumer）
                    @Override
                    public void accept(@NonNull List<StoryForRealm> stories) throws Exception {
                        mNewsListView.showTopStories(stories);
                        //TODO how to cache? the transaction of realm's thread?
                    }
                });

        mCompositeDisposable.add(disposable);
    }

    @Override
    public void bindView(NewsListContract.View view) {
        mNewsListView = view;
    }

    @Override
    public void unbindView() {
        //why dispose: stop Observable emmit items immediately!! reduce unnecessary workload
        if(mCompositeDisposable != null && !mCompositeDisposable.isDisposed()) {
            mCompositeDisposable.dispose();     //dispose main thread's subscription
        }

        mNewsListObservableManager.dispose();   //dispose ReplaySubject's subscription

        mNewsListView = null;   //deference view to let fragment can be GC
    }

}
