package com.lixinwei.www.goldennews.data.domain;

import com.lixinwei.www.goldennews.data.model.DailyStories;
import com.lixinwei.www.goldennews.data.model.DateDailyStories;
import com.lixinwei.www.goldennews.data.model.LongComments;
import com.lixinwei.www.goldennews.data.model.ShortComments;
import com.lixinwei.www.goldennews.data.model.StoryDetail;
import com.lixinwei.www.goldennews.data.model.StoryExtra;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by welding on 2017/6/29.
 */

public interface ZhihuService {

    //https://news‐at.zhihu.com/api/4/news/latest
    //latest dailyStories
    @GET("news/latest")
    Observable<DailyStories> getDailyStories();

    @GET("news/before/{date}")
    Observable<DateDailyStories> getDailyBeforeList(@Path("date") String date);

    @GET("news/{id}")
    Observable<StoryDetail> getDetailInfo(@Path("id") int id);

    @GET("story-extra/{id}")
    Observable<StoryExtra> getDetailExtraInfo(@Path("id") int id);

    @GET("story/{id}/long-comments")
    Observable<LongComments> getLongCommentInfo(@Path("id") int id);

    @GET("story/{id}/short-comments")
    Observable<ShortComments> getShortCommentInfo(@Path("id") int id);
}
