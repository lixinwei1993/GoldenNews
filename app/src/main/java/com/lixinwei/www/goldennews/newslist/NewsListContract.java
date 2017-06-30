package com.lixinwei.www.goldennews.newslist;

import android.support.annotation.NonNull;

import com.lixinwei.www.goldennews.data.model.TopStory;

import java.util.List;

/**
 * Created by welding on 2017/6/29.
 */

public interface NewsListContract {
    interface View {
        void showTopStories(List<TopStory> topStoryList);

    }

    interface Presenter {
        void loadDailyStories();
    }
}
