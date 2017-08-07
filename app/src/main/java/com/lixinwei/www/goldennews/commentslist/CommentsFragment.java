package com.lixinwei.www.goldennews.commentslist;

import android.content.Context;
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
import com.lixinwei.www.goldennews.data.model.Comment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by welding on 2017/7/6.
 */

public class CommentsFragment extends BaseFragment implements CommentsContract.View {

    @BindView(R.id.recycler_view_comments)
    RecyclerView mRecyclerView;
    @BindView(R.id.refresh_layout_comments)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Inject
    CommentsAdapter mCommentsAdapter;
    @Inject
    CommentsContract.Presenter mPresenter;
    @Inject
    Context mContext;

    private long mId;

    public static CommentsFragment newInstance() {
        return new CommentsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comments, container, false);

        GoldenNewsApplication.getGoldenNewsApplication(getActivity()).getCommentsSubComponent()
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
                mPresenter.loadShortComments(mId);
            }
        });

        initRecyclerView();
        mPresenter.bindView(this);

        mPresenter.loadShortComments(mId);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        //TODO 为了动画效果在不同网络环境下的统一，把数据请求放在onResume中进行，但是这样的话在低网速情况下数据显示延迟的问题，因此因寻求更好的解决办法
        //mPresenter.loadShortComments(mId);
    }

    public void setId(long id) {
        mId = id;
    }

    private void initRecyclerView() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mCommentsAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mPresenter.unbindView();
        GoldenNewsApplication.getGoldenNewsApplication(getActivity()).releaseCommentsSubComponent();
    }

    @Override
    public void showShortComments(List<Comment> comments) {
        mCommentsAdapter.updateComments(comments);
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