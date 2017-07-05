package com.lixinwei.www.goldennews.newslist;

import android.content.Context;
import android.view.View;

import com.lixinwei.www.goldennews.data.Realm.RealmService;
import com.lixinwei.www.goldennews.data.model.StoryForNewsList;
import com.lixinwei.www.goldennews.util.PerFragment;
import com.lixinwei.www.goldennews.util.Utils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.subscribers.ResourceSubscriber;

/**
 * Created by welding on 2017/6/29.
 */
@PerFragment //how this scope work? link the instance with the same scoped component?
public class NewsListPresenter implements NewsListContract.Presenter {
    private NewsListContract.View mNewsListView;  //view layer
    private CompositeDisposable mCompositeDisposable;

    @Inject
    Context mContext;
    @Inject
    NewsListObservableManager mNewsListObservableManager;
    @Inject
    RealmService mRealmService;

    @Inject
    NewsListPresenter() {
        mCompositeDisposable = new CompositeDisposable();
    }

    /**
     *      目前model layer返回的是知乎service，而viewlayer只有一个view，可以在这里边想着
     *      代码的实现，边实时更新activity，fragment（view）以及两个interface
     */

    //TODO 实现replaySubject，以避免频繁索取Observable或进行网络请求
    public void loadDailyStories(boolean forceUpdate) {
        mNewsListView.setLoadingIndicator(true);

        Disposable disposable = mNewsListObservableManager.loadDailyStories(forceUpdate)
                .map(new Function<StoryForNewsList, StoryForNewsList>() {
                    @Override
                    public StoryForNewsList apply(@NonNull StoryForNewsList storyForNewsList) throws Exception {
                        Long id = storyForNewsList.getId();
                        storyForNewsList.setLiked(mRealmService.queryLikedStory(id));
                        storyForNewsList.setRead(mRealmService.queryStoryRead(id));

                        return storyForNewsList;
                    }
                })
                .toList()   //convert common observable to single
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<StoryForNewsList>>() {
                    @Override
                    public void onSuccess(@NonNull List<StoryForNewsList> storyForNewsLists) {
                        mNewsListView.showTopStories(storyForNewsLists);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        mNewsListView.showLoadErrorSnackbar();
                    }
                });


        mCompositeDisposable.add(disposable);

        if(forceUpdate) {
            if(!Utils.isConnected(mContext)) mNewsListView.showNetworkErrorSnackbar();
        }


        mNewsListView.setLoadingIndicator(false);
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

        mNewsListView = null;   //dereference view to let fragment can be GC
    }

    @Override
    public void commentsButtonClicked(View view, StoryForNewsList story) {
        //TODO comments screen
    }

    @Override
    public void likeButtonClicked(StoryForNewsList story) {
        story.setLiked(true);
        mNewsListView.showLikedSnackbar();
        mRealmService.insertLikedStory(story);
    }

    @Override
    public void moreButtonClicked(View view, StoryForNewsList story) {
        //TODO more button implementation
    }

}
