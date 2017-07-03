package com.lixinwei.www.goldennews.newslist;

import com.lixinwei.www.goldennews.base.BasePresenter;
import com.lixinwei.www.goldennews.base.BaseView;
import com.lixinwei.www.goldennews.data.model.StoryForRealm;
import com.lixinwei.www.goldennews.data.model.TopStory;

import java.util.List;

/**
 * Created by welding on 2017/6/29.
 */

public interface NewsListContract {
    interface View extends BaseView {
        void showTopStories(List<StoryForRealm> storyList);
        void setLoadingIndicator(final boolean active);
    }

    interface Presenter extends BasePresenter<View> {
        void loadDailyStories();
        void bindView(View view);
        void unbindView();
    }
}
