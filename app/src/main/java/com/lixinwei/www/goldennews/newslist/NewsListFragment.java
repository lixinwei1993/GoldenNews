package com.lixinwei.www.goldennews.newslist;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.lixinwei.www.goldennews.GoldenNewsApplication;
import com.lixinwei.www.goldennews.R;
import com.lixinwei.www.goldennews.base.BaseFragment;
import com.lixinwei.www.goldennews.data.model.StoryForNewsList;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by welding on 2017/6/29.
 */

public class NewsListFragment extends BaseFragment implements NewsListContract.View {

    @BindView(R.id.recycler_view_news_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout)
    SwipeRefreshLayout mSwipeRefreshLayout;


    CoordinatorLayout mCoordinatorLayout;


    @Inject
    NewsListAdapter mNewsListAdapter;

    @Inject
    NewsListContract.Presenter mNewsListPresenter;

    //TODO the differences between the getActivity() as context and the application context??
    @Inject
    Context mContext;

    public static NewsListFragment newInstance() {
        return new NewsListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);

        GoldenNewsApplication.getGoldenNewsApplication(getActivity()).getNewsListSubComponent()
                .inject(this);

        ButterKnife.bind(this, view);

        //TODO更好的设计presenter时该句不必要亦或者是ButterKnife能不能实现这个功能呢
        mCoordinatorLayout = getActivity().findViewById(R.id.coordinatorLayout);

        //customize the progress indicator color
        mSwipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mNewsListPresenter.loadDailyStories();
            }
        });



        initRecyclerView();
        mNewsListPresenter.bindView(this);
        mNewsListPresenter.loadDailyStories();

        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_news_list_menu, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            //TODO
        }
        return true;
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        if (getView() == null) return;

        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(active);
            }
        });
    }

    @Override
    public void showLikedSnackbar() {
        Snackbar.make(mCoordinatorLayout, "Saved!", Snackbar.LENGTH_SHORT).show();
    }

    private void initRecyclerView() {
        //mNewsListAdapter = new NewsListAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mNewsListAdapter);
        mRecyclerView.setItemAnimator(new NewsItemAnimator());
    }

    @Override
    public void showTopStories(List<StoryForNewsList> storyList) {
        mNewsListAdapter.updateStoriesList(storyList);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mNewsListPresenter.unbindView();
        GoldenNewsApplication.getGoldenNewsApplication(getActivity()).releaseNewsListSubComponent();
    }




}
