package com.lixinwei.www.goldennews.likedlist;

import com.lixinwei.www.goldennews.base.BasePresenter;
import com.lixinwei.www.goldennews.base.BaseView;
import com.lixinwei.www.goldennews.data.model.StoryForNewsList;
import com.lixinwei.www.goldennews.data.model.StoryLikedForRealm;

import java.util.List;

/**
 * Created by welding on 2017/7/3.
 */

public class LikedListContract {
    interface View extends BaseView {
        void showLikedStories(List<StoryLikedForRealm> storyList);
        void setLoadingIndicator(final boolean active);
        void showLoadErrorSnackbar();
        void startDetailActivity(long id);
        void shareNews(StoryLikedForRealm story);
        void startCommentsActivity(long id);
    }

    interface Presenter extends BasePresenter<View> {
        void loadLikedStories();
        void deleteLikedStory(Long id);
        void bindView(View view);
        void unbindView();
        void startDetailActivity(long id);
        void shareItemClicked(StoryLikedForRealm story);
        void commentsItemClicked(long id);
    }
}
