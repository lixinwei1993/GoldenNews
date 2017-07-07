package com.lixinwei.www.goldennews.DateNewsList;

import com.lixinwei.www.goldennews.data.model.Story;

import java.util.List;

/**
 * Created by welding on 2017/7/7.
 */

public class DateNewsListContract {
    interface View{
        void setLoadingIndicator(boolean b);
        void showDateStories(List<Story> stories);
        void showLoadErrorSnackbar();
        void startDetailActivity(long id);
    }

    interface Presenter{
        void loadDateStories(String date);
        void bindView(View view);
        void unbindView();
        void startDetailActivity(long id);
    }
}
