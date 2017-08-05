package com.lixinwei.www.goldennews.newslist;

import android.content.Context;
import android.view.View;

import com.lixinwei.www.goldennews.data.Realm.RealmService;
import com.lixinwei.www.goldennews.data.model.StoryForNewsList;
import com.lixinwei.www.goldennews.newsDetail.NewsDetailActivity;
import com.lixinwei.www.goldennews.util.PerFragment;
import com.lixinwei.www.goldennews.util.Utils;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;

/**
 * Created by welding on 2017/6/29.
 */
@PerFragment //how this scope work? link the instance with the same scoped component?
public class NewsListPresenter implements NewsListContract.Presenter {
    private NewsListContract.View mNewsListView;  //view layer
    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();;

    @Inject
    Context mContext;
    @Inject
    NewsListObservableManager mNewsListObservableManager;
    @Inject
    RealmService mRealmService;

    /*TODO 面试
    此处必须将constructor设为inject，然后在provide时通过参数inject的形式，而不能在provide函数内使用new method。否则上面的inject field无法inject
    因为inject的触发必须是通过dagger途径，正常的使用new格式建立该类的object Dagger并不会自动触发这些inject
    现在可以理解为什么要在component中建立一个void inject函数了，通过将作为参数传递给Dagger，触发Dagger的inject
    因为activity和fragment等都是有系统建立的，并不是dagger inject的，因此需要给dagger一个触发时机，这就是为什么要
    显式的建立一次component的原因。
    总的来说能用@inject注解constructor的形式就用该形式，通过module中provide只能作为一种补充吧，因为provide内部包装的函数是普通的函数，并没有用到dagger2，因此
    不会完成内部@inject的整个依赖图形的形成*/

    @Inject
    NewsListPresenter() {
    }

    public void loadDailyStories(boolean forceUpdate) {
        if(forceUpdate) {
            mNewsListView.setLoadingIndicator(true);
        }

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
                .toList()
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
        mNewsListView.startCommentsActivity(story.getId());
    }

    @Override
    public void likeButtonClicked(StoryForNewsList story) {
        story.setLiked(true);
        mNewsListView.showLikedSnackbar();
        mRealmService.insertLikedStory(story);
    }

    @Override
    public void shareButtonClicked(View view, StoryForNewsList story) {
        //TODO more button implementation
        mNewsListView.shareNews(story);
    }

    @Override
    public void imageSingleClicked(StoryForNewsList story) {
        mRealmService.insertStoryRead(story.getId());
        mNewsListView.startDetailActivity(story.getId());
    }



}
