package com.lixinwei.www.goldennews.DateNewsList;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.lixinwei.www.goldennews.R;
import com.lixinwei.www.goldennews.data.model.Story;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by welding on 2017/7/7.
 */

public class DateNewsListAdapter extends RecyclerView.Adapter<DateNewsListAdapter.DateNewsListViewHolder> {
    private List<Story> mStoryList = new ArrayList<>();

    @Inject
    Context mContext;
    @Inject
    DateNewsListContract.Presenter mPresenter;
    @Inject
    public DateNewsListAdapter() {

    }

    private int lastAnimatedPosition = -1;
    private static final int ANIMATED_ITEMS_COUNT = 10;

    public DateNewsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_liked_list, parent, false
        );

        DateNewsListViewHolder dateNewsListViewHolder = new DateNewsListViewHolder(view);
        setupClickableViews(view, dateNewsListViewHolder);

        return dateNewsListViewHolder;
    }

    private void setupClickableViews(final View view, final DateNewsListViewHolder viewHolder) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getAdapterPosition();
                long id = mStoryList.get(position).getId();
                mPresenter.startDetailActivity(id);
            }
        });

        viewHolder.mImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context wrapper = new ContextThemeWrapper(mContext, R.style.popupMenuStyle);
                PopupMenu popupMenu = new PopupMenu(wrapper, view);
                popupMenu.inflate(R.menu.menu_popup_date_news);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    int position;

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch(item.getItemId()) {
                            case R.id.item_share:
                                position = viewHolder.getAdapterPosition();
                                Story story = mStoryList.get(position);
                                mPresenter.shareItemClicked(story);
                                break;
                            case R.id.item_comments:
                                position = viewHolder.getAdapterPosition();
                                mPresenter.commentsItemClicked(mStoryList.get(position).getId());
                                break;
                            case R.id.item_like:
                                position = viewHolder.getAdapterPosition();
                                Story story2 = mStoryList.get(position);
                                mPresenter.likeItemClicked(story2);
                                break;
                        }

                        return false;
                    }
                });
                popupMenu.show();
            }
        });

    }

    @Override
    public void onBindViewHolder(DateNewsListViewHolder holder, int position) {
        runEnterAnimation(holder.itemView, position);
        Story story = mStoryList.get(position);

        holder.bind(story);
    }

    private void runEnterAnimation(View view, int position) {
        if (position >= ANIMATED_ITEMS_COUNT - 1) {
            return;
        }

        if (position > lastAnimatedPosition) {
            lastAnimatedPosition = position;
            view.setTranslationY(100);
            view.setAlpha(0.f);
            view.animate()
                    .translationY(0).alpha(1.f)
                    .setStartDelay(20 * (position))
                    .setInterpolator(new DecelerateInterpolator(2.f))
                    .setDuration(300)
                    .setListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            // animationsLocked = true;
                        }
                    })
                    .start();
        }
    }

    @Override
    public int getItemCount() {
        return mStoryList.size();
    }

    public void updateStoriesList(List<Story> stories) {
        mStoryList.clear();
        notifyDataSetChanged();
        mStoryList.addAll(stories);
        //TODO 重要知识点，只有使用notifyItemRangeInserted或notifyItemInserted()才会触发animator中的动画，使用notifyDatasetChange是不会触发的，这点要注意
        notifyItemRangeInserted(0, stories.size());
    }

    public static class DateNewsListViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.liked_item_image)
        ImageView mImageView;
        @BindView(R.id.liked_item_title)
        TextView mTitle;
        @BindView(R.id.button_more)
        ImageButton mImageButton;

        private Context mContext;

        public DateNewsListViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();   //TODO view.getContext 是哪一种context，声明周期怎样的？
        }


        public void bind(Story story) {
            mTitle.setText(story.getTitle());

            Picasso.with(mContext)
                    .load(story.getImages().get(0))
                    .into(mImageView);
        }
    }
}
