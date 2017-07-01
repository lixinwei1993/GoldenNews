package com.lixinwei.www.goldennews.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by welding on 2017/7/1.
 */

public class StoryForRealm extends RealmObject {

    //from DailyStories or DateDailyStories
    @PrimaryKey
    private Long mId;

    private String mImage;
    private String mTitle;

    //from StoryDetail
    private String mBody;
    private String mImageSource;

    //from StoryExtra
    //number of comments: #long + #short
    private Long mComments;
    //number of like
    private Long mPopularity;

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

    public String getBody() {
        return mBody;
    }

    public void setBody(String body) {
        mBody = body;
    }


    public String getImageSource() {
        return mImageSource;
    }

    public void setImageSource(String imageSource) {
        mImageSource = imageSource;
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
}
