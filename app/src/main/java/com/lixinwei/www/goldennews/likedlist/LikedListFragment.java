package com.lixinwei.www.goldennews.likedlist;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
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
import com.lixinwei.www.goldennews.commentslist.CommentsActivity;
import com.lixinwei.www.goldennews.data.model.StoryForNewsList;
import com.lixinwei.www.goldennews.data.model.StoryLikedForRealm;
import com.lixinwei.www.goldennews.newsDetail.NewsDetailActivity;
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

    private Paint p = new Paint();


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

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {

                Bitmap icon;
                if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE){

                    View itemView = viewHolder.itemView;
                    float height = (float) itemView.getBottom() - (float) itemView.getTop();
                    float width = height / 3;


                    if(dX < 0){
                        p.setColor(Color.parseColor("#D32F2F"));
                        RectF background = new RectF((float) itemView.getRight() + dX, (float) itemView.getTop(),(float) itemView.getRight(), (float) itemView.getBottom());
                        c.drawRect(background,p);
                        icon = BitmapFactory.decodeResource(getResources(), R.drawable.ic_delete_white);
                        RectF icon_dest = new RectF((float) itemView.getRight() - 2*width ,(float) itemView.getTop() + width,(float) itemView.getRight() - width,(float)itemView.getBottom() - width);
                        c.drawBitmap(icon,null,icon_dest,p);
                    }
                }
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
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
    public void shareNews(StoryLikedForRealm story) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, story.getTitle());
        i.putExtra(Intent.EXTRA_TEXT, "https://news-at.zhihu.com/api/4/news/" + story.getId());
        startActivity(Intent.createChooser(i, getResources().getString(R.string.chooser_title)));
    }

}
