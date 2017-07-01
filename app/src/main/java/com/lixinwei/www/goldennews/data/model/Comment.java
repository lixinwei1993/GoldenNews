
package com.lixinwei.www.goldennews.data.model;

import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class Comment {

    @SerializedName("author")
    private String mAuthor;
    @SerializedName("avatar")
    private String mAvatar;
    @SerializedName("content")
    private String mContent;
    @SerializedName("id")
    private Long mId;
    @SerializedName("likes")
    private Long mLikes;
    @SerializedName("reply_to")
    private ReplyTo mReplyTo;
    @SerializedName("time")
    private Long mTime;

    public String getAuthor() {
        return mAuthor;
    }

    public void setAuthor(String author) {
        mAuthor = author;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        mAvatar = avatar;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public Long getId() {
        return mId;
    }

    public void setId(Long id) {
        mId = id;
    }

    public Long getLikes() {
        return mLikes;
    }

    public void setLikes(Long likes) {
        mLikes = likes;
    }

    public ReplyTo getReplyTo() {
        return mReplyTo;
    }

    public void setReplyTo(ReplyTo replyTo) {
        mReplyTo = replyTo;
    }

    public Long getTime() {
        return mTime;
    }

    public void setTime(Long time) {
        mTime = time;
    }

}
