package com.lixinwei.www.goldennews.newslist;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lixinwei.www.goldennews.GoldenNewsApplication;
import com.lixinwei.www.goldennews.R;
import com.lixinwei.www.goldennews.data.model.TopStory;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by welding on 2017/6/29.
 */

public class NewsListFragment extends Fragment implements NewsListContract.View {

    @BindView(R.id.recycler_view_news_list)
    RecyclerView mRecyclerView;

    //TODO
    @Inject
    NewsListAdapter mNewsListAdapter;

    //set by presenter by public void setPresenter
    NewsListContract.Presenter mNewsListPresenter;
    public void setPresenter(@NonNull NewsListContract.Presenter presenter) {
        mNewsListPresenter = presenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_list, container, false);

        DaggerNewsListComponent.builder()
                .applicationComponent(((GoldenNewsApplication) getActivity().getApplication()).getApplicationComponent())
                .newsListModule(new NewsListModule(this))
                .build()
                .inject(this);

        ButterKnife.bind(this, view);
        initRecyclerView();
        mNewsListPresenter.loadDailyStories();
        return view;
    }

    private void initRecyclerView() {
        mNewsListAdapter = new NewsListAdapter();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mNewsListAdapter);
    }


    @Override
    public void showTopStories(List<TopStory> topStoryList) {
        mNewsListAdapter.updateTopStoriesList(topStoryList);
    }

    public static NewsListFragment newInstance() {
        return new NewsListFragment();
    }




}
