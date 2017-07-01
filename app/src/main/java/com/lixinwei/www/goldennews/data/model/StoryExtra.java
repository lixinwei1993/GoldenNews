
package com.lixinwei.www.goldennews.data.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class StoryExtra {

    @SerializedName("comments")
    private Long mComments;
    @SerializedName("long_comments")
    private Long mLongComments;
    @SerializedName("popularity")
    private Long mPopularity;
    @SerializedName("short_comments")
    private Long mShortComments;

    public Long getComments() {
        return mComments;
    }

    public void setComments(Long comments) {
        mComments = comments;
    }

    public Long getLongComments() {
        return mLongComments;
    }

    public void setLongComments(Long longComments) {
        mLongComments = longComments;
    }

    public Long getPopularity() {
        return mPopularity;
    }

    public void setPopularity(Long popularity) {
        mPopularity = popularity;
    }

    public Long getShortComments() {
        return mShortComments;
    }

    public void setShortComments(Long shortComments) {
        mShortComments = shortComments;
    }

}
