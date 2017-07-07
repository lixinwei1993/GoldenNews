package com.lixinwei.www.goldennews.likedlist;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lixinwei.www.goldennews.R;
import com.lixinwei.www.goldennews.data.model.StoryForNewsList;
import com.lixinwei.www.goldennews.data.model.StoryLikedForRealm;
import com.lixinwei.www.goldennews.newsDetail.NewsDetailActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by welding on 2017/7/3.
 */

public class LikedAdapter extends RecyclerView.Adapter<LikedAdapter.LikedListViewHolder> {
    private List<StoryLikedForRealm> mLikedStories = new ArrayList<>();//TODO 这种初始化的实机是？

    @Inject
    Context mContext;
    @Inject
    LikedListContract.Presenter mPresenter;
    @Inject
    public LikedAdapter() {

    }

    public LikedListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_liked_list, parent, false
        );

        LikedListViewHolder likedListViewHolder = new LikedListViewHolder(view);
        setupClickableViews(view, likedListViewHolder);

        return likedListViewHolder;
    }

    private void setupClickableViews(final View view, final LikedListViewHolder likedListViewHolder) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = likedListViewHolder.getAdapterPosition();
                long id = mLikedStories.get(position).getId();
                mPresenter.startDetailActivity(id);
            }
        });
    }

    @Override
    public void onBindViewHolder(LikedListViewHolder holder, int position) {
        StoryLikedForRealm storyLikedForRealm = mLikedStories.get(position);

        holder.bind(storyLikedForRealm);
    }

    @Override
    public int getItemCount() {
        return mLikedStories.size();
    }

    public void updateStoriesList(List<StoryLikedForRealm> stories) {
        mLikedStories.clear();
        mLikedStories.addAll(stories);
        notifyDataSetChanged();
    }

    public static class LikedListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.liked_item_image)
        ImageView mImageView;
        @BindView(R.id.liked_item_title)
        TextView mTitle;

        private Context mContext;

        public LikedListViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();   //TODO view.getContext 是哪一种context，声明周期怎样的？
        }


        public void bind(StoryLikedForRealm storyLikedForRealm) {
            mTitle.setText(storyLikedForRealm.getTitle());

            //Glide can automatically cache image in memory&disk, if you want to customize the cache pattern, read api
            Glide.with(mContext)
                    .load(storyLikedForRealm.getImage())
                    .into(mImageView);
        }
    }
}
