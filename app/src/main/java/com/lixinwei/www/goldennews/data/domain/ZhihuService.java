package com.lixinwei.www.goldennews.data.domain;

import com.lixinwei.www.goldennews.data.model.DailyStories;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by welding on 2017/6/29.
 */

public interface ZhihuService {

    //https://news‐at.zhihu.com/api/4/news/latest
    @GET("4/news/latest")
    Observable<DailyStories> getDailyStories();
}
