package com.lixinwei.www.goldennews.likedlist;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lixinwei.www.goldennews.R;
import com.lixinwei.www.goldennews.app.GoldenNewsApplication;
import com.lixinwei.www.goldennews.base.BaseFragment;
import com.lixinwei.www.goldennews.data.model.StoryForNewsList;
import com.lixinwei.www.goldennews.data.model.StoryLikedForRealm;
import com.lixinwei.www.goldennews.newslist.NewsItemAnimator;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by welding on 2017/7/3.
 */

public class LikedListFragment extends BaseFragment implements LikedListContract.View {

    private List<StoryLikedForRealm> mStories;

    @BindView(R.id.recycler_view_liked)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout_liked)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Inject
    LikedAdapter mLikedListAdapter;
    @Inject
    LikedListContract.Presenter mLikedListPresenter;
    @Inject
    Context mContext;

    public static LikedListFragment newInstance() {
        return new LikedListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_liked_list, container, false);

        GoldenNewsApplication.getGoldenNewsApplication(getActivity()).getLikedListSubComponent()
                .inject(this);

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
                mLikedListPresenter.loadLikedStories();
            }
        });


        initRecyclerView();
        mLikedListPresenter.bindView(this);
        mLikedListPresenter.loadLikedStories();

        //setHasOptionsMenu(true);

        return view;
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mLikedListAdapter);
        //mRecyclerView.setItemAnimator(new NewsItemAnimator());

        ItemTouchHelper.SimpleCallback swipeHelper = new ItemTouchHelper.SimpleCallback(0 ,ItemTouchHelper.START) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if(mStories != null) {
                    int position = viewHolder.getAdapterPosition();
                    mLikedListPresenter.deleteLikedStory(mStories.get(position).getId());
                    mStories.remove(position);
                    mLikedListAdapter.updateStoriesList(mStories);
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeHelper);
        itemTouchHelper.attachToRecyclerView(mRecyclerView);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        GoldenNewsApplication.getGoldenNewsApplication(getActivity()).releaseLikedListSubComponent();
    }

    @Override
    public void showLikedStories(List<StoryLikedForRealm> storyList) {
        mStories = storyList;
        mLikedListAdapter.updateStoriesList(mStories);
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
    public void showLoadErrorSnackbar() {
        Snackbar.make(mRecyclerView, "Load Error", Snackbar.LENGTH_SHORT).show();
    }

}
