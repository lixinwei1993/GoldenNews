package com.lixinwei.www.goldennews.newslist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lixinwei.www.goldennews.R;
import com.lixinwei.www.goldennews.data.model.StoryForNewsList;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by welding on 2017/6/30.
 */

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsListViewHolder> {
    private List<StoryForNewsList> mStories = new ArrayList<>();

    public static final String ACTION_LIKE_BUTTON_CLICKED = "action_like_button_button";
    public static final String ACTION_LIKE_IMAGE_CLICKED = "action_like_image_button";

    @Inject
    Context mContext;
    @Inject
    NewsListContract.Presenter mNewsListPresenter;

    @Inject
    public NewsListAdapter() {

    }

    @Override
    public NewsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;

        //这个parent指的是什么？getContext又指的是什么，LayoutInflater又有哪些地方可以用呢？
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news_list, parent, false);
        NewsListViewHolder newsListViewHolder = new NewsListViewHolder(view);
        setupClickableViews(view, newsListViewHolder);

        return newsListViewHolder;
    }

    private void setupClickableViews(final View view, final NewsListViewHolder newsListViewHolder) {
        newsListViewHolder.mButtonComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = newsListViewHolder.getAdapterPosition();
                mNewsListPresenter.commentsButtonClicked(view, mStories.get(position));
            }
        });

        newsListViewHolder.mButtonLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = newsListViewHolder.getAdapterPosition();
                StoryForNewsList story = mStories.get(position);
                if(!story.isLiked())
                {
                    mNewsListPresenter.likeButtonClicked(story);
                }
                notifyItemChanged(position, ACTION_LIKE_BUTTON_CLICKED);

            }
        });

        newsListViewHolder.mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = newsListViewHolder.getAdapterPosition();
                StoryForNewsList story = mStories.get(position);
                if(!story.isLiked())
                {
                    mNewsListPresenter.likeButtonClicked(story);
                }
                notifyItemChanged(position, ACTION_LIKE_IMAGE_CLICKED);
            }
        });

        newsListViewHolder.mButtonMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = newsListViewHolder.getAdapterPosition();
                mNewsListPresenter.moreButtonClicked(view, mStories.get(position));
            }
        });
    }


    @Override
    public void onBindViewHolder(NewsListViewHolder holder, int position) {
        StoryForNewsList storyForNewsList = mStories.get(position);

        holder.bind(storyForNewsList);
    }

    @Override
    public int getItemCount() {
        return mStories.size();
    }

    public void updateStoriesList(List<StoryForNewsList> stories) {
        mStories.clear();
        mStories.addAll(stories);
        notifyDataSetChanged();
    }

    public static class NewsListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.story_card_image)
        ImageView mImage;
        @BindView(R.id.story_card_title)
        TextView mTitle;
        @BindView(R.id.button_comments)
        ImageButton mButtonComments;
        @BindView(R.id.button_like)
        ImageButton mButtonLike;
        @BindView(R.id.button_more)
        ImageButton mButtonMore;
        @BindView(R.id.like_background)
        View mLikeBackground;
        @BindView(R.id.like_heart)
        ImageView mHeartLikeImageView;
        @BindView(R.id.tsLikesCounter)
        TextSwitcher mTextSwitcher;
        @BindView(R.id.story_card_image_layout)
        FrameLayout mFrameLayout;

        private Context mContext;

        public NewsListViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
        }

        public void bind(StoryForNewsList storyForNewsList) {
            mTitle.setText(storyForNewsList.getTitle());
            mTitle.setTextColor(storyForNewsList.isRead() ? mContext.getResources().getColor(R.color.titleRead) : mContext.getResources().getColor(R.color.titleUnread));
            mButtonLike.setImageResource(storyForNewsList.isLiked() ? R.drawable.ic_heart_red : R.drawable.ic_heart_outline_grey);
            mTextSwitcher.setCurrentText(storyForNewsList.getPopularity() + "likes");

            //Glide can automatically cache image in memory&disk, if you want to customize the cache pattern, read api
            Glide.with(mContext)
                    .load(storyForNewsList.getImage())
                    .into(mImage);
        }

    }
}