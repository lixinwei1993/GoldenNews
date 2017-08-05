package com.lixinwei.www.goldennews.DateNewsList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lixinwei.www.goldennews.R;
import com.lixinwei.www.goldennews.app.GoldenNewsApplication;
import com.lixinwei.www.goldennews.base.BaseFragment;
import com.lixinwei.www.goldennews.commentslist.CommentsActivity;
import com.lixinwei.www.goldennews.data.model.Story;
import com.lixinwei.www.goldennews.data.model.StoryForNewsList;
import com.lixinwei.www.goldennews.data.model.StoryLikedForRealm;
import com.lixinwei.www.goldennews.newsDetail.NewsDetailActivity;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by welding on 2017/7/7.
 */

public class DateNewsListFragment extends BaseFragment implements DateNewsListContract.View {

    @BindView(R.id.recycler_view_date)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout_date)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Inject
    DateNewsListAdapter mDateNewsListAdapter;
    @Inject
    DateNewsListContract.Presenter mPresenter;
    @Inject
    Context mContext;

    private String mDate;

    public void setDate(String date) {
        mDate = date;
    }

    public void dateChanged(String date) {
        mDate = date;
        mPresenter.loadDateStories(mDate);
    }

    public static DateNewsListFragment newInstance(String date) {
        DateNewsListFragment fragment = new DateNewsListFragment();
        fragment.setDate(date);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_date_news_list, container, false);

        GoldenNewsApplication.getGoldenNewsApplication(getActivity()).getDateNewsListSubComponent()
                .inject(this);

        ButterKnife.bind(this, view);

        //customize the progress indicator color
        mSwipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );

        if(savedInstanceState != null) {
            mDate = savedInstanceState.getString("DATE");
        }

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.loadDateStories(mDate);
            }
        });

        initRecyclerView();
        mPresenter.bindView(this);
        mPresenter.loadDateStories(mDate);

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("DATE", mDate);
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mDateNewsListAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        GoldenNewsApplication.getGoldenNewsApplication(getActivity()).releaseDateNewsListSubComponent();

    }



    public void setLoadingIndicator(final boolean b) {
        if (getView() == null) return;

        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(b);
            }
        });
    }

    @Override
    public void showDateStories(List<Story> stories) {
        mDateNewsListAdapter.updateStoriesList(stories);
    }

    @Override
    public void showLoadErrorSnackbar() {
        Snackbar.make(mRecyclerView, "Load Error", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showLikedSnackbar() {
        Snackbar.make(mRecyclerView, "Saved", Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void startDetailActivity(long id) {
        Intent intent = NewsDetailActivity.newIntent(getActivity(), id);
        startActivity(intent);
    }

    @Override
    public void startCommentsActivity(long id) {
        Intent intent = CommentsActivity.newIntent(getActivity(), id);
        startActivity(intent);
    }

    @Override
    public void shareNews(Story story) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, story.getTitle());
        i.putExtra(Intent.EXTRA_TEXT, "https://news-at.zhihu.com/api/4/news/" + story.getId());
        startActivity(Intent.createChooser(i, getResources().getString(R.string.chooser_title)));
    }

}
