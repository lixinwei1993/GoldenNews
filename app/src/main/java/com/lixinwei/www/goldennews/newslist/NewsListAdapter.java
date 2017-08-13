package com.lixinwei.www.goldennews.newslist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextSwitcher;
import android.widget.TextView;

import com.lixinwei.www.goldennews.R;
import com.lixinwei.www.goldennews.data.model.StoryForNewsList;
import com.lixinwei.www.goldennews.util.Utils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

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

    private int lastAnimatedPosition = -1;
    private static final int ANIMATED_ITEMS_COUNT = 4;

    private boolean mIsAllowAnimation = true;

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

    private void runEnterAnimation(View view, int position) {
        if (position >= ANIMATED_ITEMS_COUNT - 1) {
            return;
        }

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(Utils.getScreenHeight(mContext));
            view.animate()
                    .translationY(0)
                    .setInterpolator(new DecelerateInterpolator(3.f))
                    .setDuration(700)
                    .start();
        }
    }

    private void setupClickableViews(final View view, final NewsListViewHolder newsListViewHolder) {

        GestureDetector.SimpleOnGestureListener gestureDetectorListener = new GestureDetector.SimpleOnGestureListener(){

            @Override
            public boolean onSingleTapConfirmed(MotionEvent e) {
                int position = newsListViewHolder.getAdapterPosition();
                mNewsListPresenter.imageSingleClicked(mStories.get(position));
                return super.onSingleTapUp(e);
            }

            @Override
            public boolean onDoubleTap(MotionEvent e) {
                int position = newsListViewHolder.getAdapterPosition();
                StoryForNewsList story = mStories.get(position);
                if(!story.isLiked())
                {
                    mNewsListPresenter.likeButtonClicked(story);
                }
                notifyItemChanged(position, ACTION_LIKE_IMAGE_CLICKED);

                return super.onDoubleTap(e);
            }
        };

        final GestureDetector gestureDetector = new GestureDetector(mContext, gestureDetectorListener);

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

        newsListViewHolder.mImage.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                gestureDetector.onTouchEvent(motionEvent);

                // 一定要返回true，不然获取不到完整的事件
                return true;
            }


        });

        newsListViewHolder.mButtonShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = newsListViewHolder.getAdapterPosition();
                mNewsListPresenter.shareButtonClicked(view, mStories.get(position));
            }
        });


    }



    @Override
    public void onBindViewHolder(NewsListViewHolder holder, int position) {
        //runEnterAnimation(holder.itemView, position);

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
        //TODO 解释notifyItemRangeInserted表示在某个位置插入了几个item，对于真个datasetchange的变化不能使用该方法，会出现数组越界等错误
        //参考：https://stackoverflow.com/a/44004438
        if(mIsAllowAnimation)
        {
            mIsAllowAnimation = false;
            notifyItemRangeInserted(0, stories.size());
        }
        else
            notifyDataSetChanged();


    }

    public static class NewsListViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.story_card_image)
        ImageView mImage;
        @BindView(R.id.story_card_title)
        TextView mTitle;
        @BindView(R.id.button_comments)
        ImageButton mButtonComments;
        @BindView(R.id.button_like)
        ImageButton mButtonLike;
        @BindView(R.id.button_share)
        ImageButton mButtonShare;
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

        public void bind(final StoryForNewsList storyForNewsList) {
            mTitle.setText(storyForNewsList.getTitle());
            mTitle.setTextColor(storyForNewsList.isRead() ? mContext.getResources().getColor(R.color.textColorSecondary) : mContext.getResources().getColor(R.color.textColorPrimary));
            mButtonLike.setImageResource(storyForNewsList.isLiked() ? R.drawable.ic_heart_red : R.drawable.ic_heart_outline_grey);
            mTextSwitcher.setCurrentText(storyForNewsList.getPopularity() + "赞");

            /*Picasso.with(mNewsListFragment.getActivity())
                    .load(storyForNewsList.getImage())
                    .into(mImage);*/

            //TODO: 解决picasso使用硬盘缓存的问题，在即使有缓存的时候picasso会出现跳过硬盘缓存的情况，因此
            // TODO 这里要使用一定的手段让picasso强制使用硬盘缓存reference：https://stackoverflow.com/a/30686992
            Picasso.with(mContext)
                    .load(storyForNewsList.getImage())
                    .networkPolicy(NetworkPolicy.OFFLINE)
                    .into(mImage, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            //Try again online if cache failed
                            Picasso.with(mContext)
                                    .load(storyForNewsList.getImage())
                                    .error(R.mipmap.ic_launcher)
                                    .into(mImage, new Callback() {
                                        @Override
                                        public void onSuccess() {

                                        }

                                        @Override
                                        public void onError() {
                                            Log.v("Picasso","Could not fetch image");
                                        }
                                    });
                        }
                    });


        }


    }
}