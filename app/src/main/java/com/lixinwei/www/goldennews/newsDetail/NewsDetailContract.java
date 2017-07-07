package com.lixinwei.www.goldennews.newsDetail;

import com.lixinwei.www.goldennews.data.model.StoryDetail;

/**
 * Created by welding on 2017/7/6.
 */

public class NewsDetailContract {
    interface View {
        void setLoadingIndicator(boolean a);
        void showLoadErrorSnackbar();
        void showStoryDetail(StoryDetail storyDetail);
    }

    interface Presenter {
        void loadStoryDetail(long id);
        void bindView(View view);
        void unbindView();
    }
}
