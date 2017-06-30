
package com.lixinwei.www.goldennews.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class DailyStories {

    @SerializedName("date")
    @Expose
    private Date mDate = null;

    @SerializedName("stories")
    @Expose
    private List<Story> mStories = null;

    @SerializedName("top_stories")
    @Expose
    private List<TopStory> mTopStories = null;

    public Date getDate() {
        return mDate;
    }


    public List<Story> getStories() {
        return mStories;
    }


    public List<TopStory> getTopStories() {
        return mTopStories;
    }


}
