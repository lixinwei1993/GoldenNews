package com.lixinwei.www.goldennews.data.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by welding on 2017/7/3.
 */

public class StoryLikedForRealm extends RealmObject {
    @PrimaryKey
    private Long mId;
    private String mImage;
    private String mTitle;
    private Long mLikedime;

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

    public Long getLikedime() {
        return mLikedime;
    }

    public void setLikedime(Long likedime) {
        mLikedime = likedime;
    }
}
