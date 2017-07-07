
package com.lixinwei.www.goldennews.data.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Story {

    @SerializedName("ga_prefix")
    private String mGaPrefix;
    @SerializedName("id")
    private Long mId;
    @SerializedName("images")
    private List<String> mImages;
    @SerializedName("multipic")
    private Boolean mMultipic;
    @SerializedName("title")
    private String mTitle;
    @SerializedName("type")
    private Long mType;


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

    public List<String> getImages() {
        return mImages;
    }

    public void setImages(List<String> images) {
        mImages = images;
    }

    public Boolean getMultipic() {
        return mMultipic;
    }

    public void setMultipic(Boolean multipic) {
        mMultipic = multipic;
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
