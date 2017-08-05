package com.lixinwei.www.goldennews.newslist;

import com.lixinwei.www.goldennews.base.BasePresenter;
import com.lixinwei.www.goldennews.base.BaseView;
import com.lixinwei.www.goldennews.data.model.StoryForNewsList;

import java.util.List;

/**
 * Created by welding on 2017/6/29.
 */

public interface NewsListContract {
    interface View extends BaseView {
        void showTopStories(List<StoryForNewsList> storyList);
        void setLoadingIndicator(final boolean active);
        void showLikedSnackbar();
        void showLoadErrorSnackbar();
        void showNetworkErrorSnackbar();
        void startCommentsActivity(long id);
        void startDetailActivity(long id);
    }

    interface Presenter extends BasePresenter<View> {
        void loadDailyStories(boolean forceUpdate);
        void commentsButtonClicked(android.view.View view, StoryForNewsList story);

        void likeButtonClicked(StoryForNewsList story);

        void moreButtonClicked(android.view.View view, StoryForNewsList story);
        void imageSingleClicked(StoryForNewsList story);
        void bindView(View view);
        void unbindView();
    }
}
