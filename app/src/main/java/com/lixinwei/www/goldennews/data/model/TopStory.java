
package com.lixinwei.www.goldennews.data.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class TopStory {

    @SerializedName("ga_prefix")
    private String mGaPrefix;
    @SerializedName("id")
    private Long mId;
    @SerializedName("image")
    private String mImage;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("type")
    private Long mType;

    public TopStory(Story story) {
        this.mGaPrefix = story.getGaPrefix();
        this.mId = story.getId();
        this.mImage = story.getImages().get(0);
        this.mTitle = story.getTitle();
        this.mType = story.getType();
    }

    public String getGaPrefix() {
        return mGaPrefix;
    }

    public void setGaPrefix(String gaPrefix) {
        mGaPrefix = gaPrefix;
    }

    public long getId() {
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

    public Long getType() {
        return mType;
    }

    public void setType(Long type) {
        mType = type;
    }

}
