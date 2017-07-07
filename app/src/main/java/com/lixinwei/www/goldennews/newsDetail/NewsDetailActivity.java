package com.lixinwei.www.goldennews.newsDetail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lixinwei.www.goldennews.R;
import com.lixinwei.www.goldennews.app.GoldenNewsApplication;
import com.lixinwei.www.goldennews.base.BaseActivity;
import com.lixinwei.www.goldennews.data.model.StoryDetail;
import com.lixinwei.www.goldennews.util.HtmlUtil;
import com.lixinwei.www.goldennews.util.Utils;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import net.opacapp.multilinecollapsingtoolbar.CollapsingToolbarLayout;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by welding on 2017/7/6.
 */

public class NewsDetailActivity extends BaseActivity implements NewsDetailContract.View {
    private static final String EXTRA_NEWS_CONTENT_ID =
            "com.lixinwei.www.goldennews.newsDetail.id";

    private long mId;

    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout mCollapsingToolbarLayout;
    @BindView(R.id.detail_bar_image)
    ImageView mBarImageView;
    @BindView(R.id.detail_bar_copyright)
    TextView mCopyRight;
    @BindView(R.id.view_toolbar)
    Toolbar mToolbar;
    @BindView(R.id.nsv_scroller)
    NestedScrollView mNestedScrollView;
    @BindView(R.id.news_detail_webview)
    WebView mWebView;

    @Inject
    NewsDetailContract.Presenter mPresenter;

    public static Intent newIntent(Context context, long id) {
        Intent intent = new Intent(context, NewsDetailActivity.class);
        intent.putExtra(EXTRA_NEWS_CONTENT_ID, id);

        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        ButterKnife.bind(this);

        setSupportActionBar(mToolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        mId = getIntent().getLongExtra(EXTRA_NEWS_CONTENT_ID, 0);

        initWebView();

        GoldenNewsApplication.getGoldenNewsApplication(this).getNewsDetailSubComponent()
                .inject(this);

        mPresenter.bindView(this);

        mPresenter.loadStoryDetail(mId);

    }


    private void initWebView() {
        WebSettings settings = mWebView.getSettings();
        /*if (mPresenter.getNoImageState()) {
            settings.setBlockNetworkImage(true);
        }*/
        //if (mPresenter.getAutoCacheState())
        if (true) {
            settings.setAppCacheEnabled(true);
            settings.setDomStorageEnabled(true);
            settings.setDatabaseEnabled(true);
            if (Utils.isConnected(this)) {
                settings.setCacheMode(WebSettings.LOAD_DEFAULT);
            } else {
                settings.setCacheMode(WebSettings.LOAD_CACHE_ONLY);
            }
        }
        settings.setJavaScriptEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        settings.setSupportZoom(true);
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unbindView();

        GoldenNewsApplication.getGoldenNewsApplication(this).releaseNewsDetailSubComponent();
    }

    @Override
    public void setLoadingIndicator(boolean a) {

    }

    @Override
    public void showLoadErrorSnackbar() {

    }

    @Override
    public void showStoryDetail(StoryDetail storyDetail) {
        Log.i("Main", "Hello");

        Glide.with(this)
                .load(storyDetail.getImage())
                .into(mBarImageView);

        mCollapsingToolbarLayout.setTitle(storyDetail.getTitle());
        mCopyRight.setText(storyDetail.getImageSource());

        String htmlData = HtmlUtil.createHtmlData(storyDetail.getBody(), storyDetail.getCss(), storyDetail.getJs());
        mWebView.loadData(htmlData, HtmlUtil.MIME_TYPE, HtmlUtil.ENCODING);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) { //TODO 目的是？？
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
