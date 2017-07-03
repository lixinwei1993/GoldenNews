package com.lixinwei.www.goldennews.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by welding on 2017/7/1.
 */

public class StoryForNewsList {

    //from DailyStories or DateDailyStories
    @PrimaryKey
    private Long mId;
    private String mImage;
    private String mTitle;
    private Long mComments;
    private Long mPopularity;
    private boolean mLiked;
    private boolean mRead;

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }


    public Long getComments() {
        return mComments;
    }

    public void setComments(Long comments) {
        mComments = comments;
    }

    public Long getPopularity() {
        return mPopularity;
    }

    public void setPopularity(Long popularity) {
        mPopularity = popularity;
    }

    public boolean isLiked() {
        return mLiked;
    }

    public void setLiked(boolean liked) {
        mLiked = liked;
    }

    public boolean isRead() {
        return mRead;
    }

    public void setRead(boolean read) {
        mRead = read;
    }
}
