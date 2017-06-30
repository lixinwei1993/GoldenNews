
package com.lixinwei.www.goldennews.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Story {

    @SerializedName("images")
    @Expose
    private List<String> mImages = null;

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

    public List<String> getImages() {
        return mImages;
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
