package com.lixinwei.www.goldennews.likedlist;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lixinwei.www.goldennews.R;
import com.lixinwei.www.goldennews.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by welding on 2017/7/3.
 */

public class LikedListFragment extends BaseFragment {

    @BindView(R.id.recycler_view_liked)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout_liked)
    SwipeRefreshLayout mSwipeRefreshLayout;

    public static LikedListFragment newInstance() {
        return new LikedListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_liked_list, container, false);


        ButterKnife.bind(this, view);

        //customize the progress indicator color
        mSwipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(getActivity(), R.color.colorPrimary),
                ContextCompat.getColor(getActivity(), R.color.colorAccent),
                ContextCompat.getColor(getActivity(), R.color.colorPrimaryDark)
        );

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //TODO mNewsListPresenter.loadLikedStories();
            }
        });



        /*initRecyclerView();
        mNewsListPresenter.bindView(this);
        mNewsListPresenter.loadDailyStories();*/

        //setHasOptionsMenu(true);

        return view;
    }
}
