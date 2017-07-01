
package com.lixinwei.www.goldennews.data.model;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class StoryDetail {

    //HTML of news content
    //contain the images etc
    @SerializedName("body")
    private String mBody;
    //website used for mobile android webView
    @SerializedName("css")
    private List<String> mCss;
    //used for google analytics
    @SerializedName("ga_prefix")
    private String mGaPrefix;
    //news id
    @SerializedName("id")
    private Long mId;
    //
    @SerializedName("image")
    private String mImage;
    //"Yestone.com 版权图片库" used to show the source of image
    @SerializedName("image_source")
    private String mImageSource;
    //used for Android WebView
    @SerializedName("js")
    private List<Object> mJs;

    @SerializedName("share_url")
    private String mShareUrl;
    @SerializedName("title")
    private String mTitle;

    @SerializedName("type")
    private Long mType;

    public String getBody() {
        return mBody;
    }

    public void setBody(String body) {
        mBody = body;
    }

    public List<String> getCss() {
        return mCss;
    }

    public void setCss(List<String> css) {
        mCss = css;
    }

    public String getGaPrefix() {
        return mGaPrefix;
    }

    public void setGaPrefix(String gaPrefix) {
        mGaPrefix = gaPrefix;
    }

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

    public String getImageSource() {
        return mImageSource;
    }

    public void setImageSource(String imageSource) {
        mImageSource = imageSource;
    }

    public List<Object> getJs() {
        return mJs;
    }

    public void setJs(List<Object> js) {
        mJs = js;
    }

    public String getShareUrl() {
        return mShareUrl;
    }

    public void setShareUrl(String shareUrl) {
        mShareUrl = shareUrl;
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
