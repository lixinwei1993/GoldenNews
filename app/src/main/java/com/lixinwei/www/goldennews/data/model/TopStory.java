
package com.lixinwei.www.goldennews.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TopStory {

    @SerializedName("image")
    @Expose
    private String mImage;
    @SerializedName("type")
    @Expose
    private Integer mType;
    @SerializedName("id")
    @Expose
    private Integer mId;
    @SerializedName("ga_prefix")
    @Expose
    private String mGaPrefix;
    @SerializedName("title")
    @Expose
    private String mTitle;

    public String getImage() {
        return mImage;
    }

    public Integer getType() {
        return mType;
    }

    public Integer getId() {
        return mId;
    }

    public String getGaPrefix() {
        return mGaPrefix;
    }


    public String getTitle() {
        return mTitle;
    }


}
