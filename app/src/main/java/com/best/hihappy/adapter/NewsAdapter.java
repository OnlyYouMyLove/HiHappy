package com.best.hihappy.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.best.hihappy.R;
import com.best.hihappy.bean.NewsBean;
import com.best.hihappy.mvp.view.activity.News_WebView;
import com.bumptech.glide.Glide;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSessionConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by FuKaiqiang on 2017-09-10.
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<NewsBean.ResultBean.DataBean> mList;
    private Context mContext;
    private int mLastPosition;
    private static final int IMAGE_ONLY_ONE = 1;
    private static final int IMAGE_TWOORTHREE = 2;
    private int FOOTTYPE = 3;
    private boolean hasMore = true;
    private boolean fadeTips = false;
    private Handler mHandler = new Handler(Looper.getMainLooper());
    private static final String TAG = "NewsAdapter";

    public NewsAdapter(List<NewsBean.ResultBean.DataBean> newsBeanList, Context context, boolean hasMore) {
        mList = newsBeanList;
        mContext = context;
        this.hasMore = hasMore;
    }

    static class ImageThreeViewHolder extends RecyclerView.ViewHolder {

        private TextView mTv_title;
        private ImageView mIv_image01;
        private ImageView mIv_image02;
        private ImageView mIv_image03;
        private TextView mTv_company;
        private TextView mTv_time;
        private View mItemView;

        public ImageThreeViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            mTv_title = itemView.findViewById(R.id.tv_title);
            mIv_image01 = itemView.findViewById(R.id.iv_image01);
            mIv_image02 = itemView.findViewById(R.id.iv_image02);
            mIv_image03 = itemView.findViewById(R.id.iv_image03);
            mTv_company = itemView.findViewById(R.id.tv_company);
            mTv_time = itemView.findViewById(R.id.tv_time);
        }
    }

    static class ImageOnlyOneViewHolder extends RecyclerView.ViewHolder {

        private View mItemView;
        private TextView mTv_title;
        private TextView mTv_company;
        private TextView mTv_time;
        private ImageView mIv_image01;

        public ImageOnlyOneViewHolder(View itemView) {
            super(itemView);
            mItemView = itemView;
            mTv_title = itemView.findViewById(R.id.tv_title);
            mTv_company = itemView.findViewById(R.id.tv_company);
            mTv_time = itemView.findViewById(R.id.tv_time);
            mIv_image01 = itemView.findViewById(R.id.iv_image01);
        }
    }

    static class FootHolder extends RecyclerView.ViewHolder {
        private TextView tips;
        private LinearLayout mLinearLayout;

        public FootHolder(View itemView) {
            super(itemView);
            tips = itemView.findViewById(R.id.tips);
            mLinearLayout = itemView.findViewById(R.id.ll_footview);
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == IMAGE_TWOORTHREE) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_imagethree_item, viewGroup, false);
            final ImageThreeViewHolder viewHolder = new ImageThreeViewHolder(view);
            viewHolder.mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = viewHolder.getAdapterPosition();
                    String url = mList.get(position).getUrl();
                    String imageUrl = mList.get(position).getThumbnail_pic_s();
                    String category = mList.get(position).getCategory();
                    String title = mList.get(position).getTitle();
                    SonicSessionConfig sessionConfig = new SonicSessionConfig.Builder().build();
                    boolean preloadSuccess = SonicEngine.getInstance().preCreateSession(url, sessionConfig);
                    Intent intent = new Intent(mContext, News_WebView.class);
                    intent.putExtra("url", url);
                    intent.putExtra("imageUrl", imageUrl);
                    intent.putExtra("category", category);
                    intent.putExtra("title", title);
                    mContext.startActivity(intent);
                }
            });
            return viewHolder;
        } else if (viewType == IMAGE_ONLY_ONE) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_imageonly_item, viewGroup, false);
            final ImageOnlyOneViewHolder viewHolder = new ImageOnlyOneViewHolder(view);
            viewHolder.mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = viewHolder.getAdapterPosition();
                    String url = mList.get(position).getUrl();
                    String imageUrl = mList.get(position).getThumbnail_pic_s();
                    String category = mList.get(position).getCategory();
                    String title = mList.get(position).getTitle();
                    SonicSessionConfig sessionConfig = new SonicSessionConfig.Builder().build();
                    boolean preloadSuccess = SonicEngine.getInstance().preCreateSession(url, sessionConfig);
                    Intent intent = new Intent(mContext, News_WebView.class);
                    intent.putExtra("url", url);
                    intent.putExtra("imageUrl", imageUrl);
                    intent.putExtra("category", category);
                    intent.putExtra("title", title);
                    mContext.startActivity(intent);
                }
            });
            return viewHolder;
        } else {
            return new FootHolder(LayoutInflater.from(mContext).inflate(R.layout.news_footview_item, null));
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) {

        if (viewHolder instanceof ImageThreeViewHolder) {
            ((ImageThreeViewHolder) viewHolder).mTv_title.setText(mList.get(position).getTitle());
            ((ImageThreeViewHolder) viewHolder).mTv_company.setText(mList.get(position).getAuthor_name());
            ((ImageThreeViewHolder) viewHolder).mTv_time.setText(mList.get(position).getDate());

            Glide.with(mContext).load(mList.get(position).getThumbnail_pic_s()).into(((ImageThreeViewHolder) viewHolder).mIv_image01);
            Glide.with(mContext).load(mList.get(position).getThumbnail_pic_s02()).into(((ImageThreeViewHolder) viewHolder).mIv_image02);
            Glide.with(mContext).load(mList.get(position).getThumbnail_pic_s03()).into(((ImageThreeViewHolder) viewHolder).mIv_image03);
        } else if (viewHolder instanceof ImageOnlyOneViewHolder) {
            ((ImageOnlyOneViewHolder) viewHolder).mTv_title.setText(mList.get(position).getTitle());
            ((ImageOnlyOneViewHolder) viewHolder).mTv_company.setText(mList.get(position).getAuthor_name());
            ((ImageOnlyOneViewHolder) viewHolder).mTv_time.setText(mList.get(position).getDate());

            Glide.with(mContext).load(mList.get(position).getThumbnail_pic_s()).into(((ImageOnlyOneViewHolder) viewHolder).mIv_image01);

        } else if (viewHolder instanceof FootHolder) {
            ((FootHolder) viewHolder).mLinearLayout.setVisibility(View.VISIBLE);
            if (hasMore == true) {
                fadeTips = false;
                if (mList.size() > 0) {
                    ((FootHolder) viewHolder).mLinearLayout.setVisibility(View.VISIBLE);
                    ((FootHolder) viewHolder).tips.setText("正在加载更多...");
                }
            } else {
                if (mList.size() > 0) {
                    ((FootHolder) viewHolder).mLinearLayout.setVisibility(View.VISIBLE);
                    ((FootHolder) viewHolder).tips.setText("没有更多数据了");
                    mHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            ((FootHolder) viewHolder).mLinearLayout.setVisibility(View.GONE);
                            fadeTips = true;
                            hasMore = true;
                        }
                    }, 1000);
                }
            }
        }
        if (viewHolder.getAdapterPosition() > mLastPosition) {
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(viewHolder.itemView, "scaleX", 0.5f, 1f);
            scaleX.start();
            mLastPosition = viewHolder.getLayoutPosition();
        }
    }


    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {

        if (position == getItemCount() - 1) {
            return FOOTTYPE;
        } else {
            if (mList.get(position).getThumbnail_pic_s03() != null) {
                return IMAGE_TWOORTHREE;
            } else {
                return IMAGE_ONLY_ONE;
            }
        }
    }

    public void updateList(int loadrefreshOrLoadmore, List<NewsBean.ResultBean.DataBean> newDatas, boolean hasMore) {
        if (newDatas != null) {
            if (loadrefreshOrLoadmore == 1) {
                mList.addAll(0, newDatas);
            } else {
                mList.addAll(newDatas);
            }
        }
        this.hasMore = hasMore;
        notifyDataSetChanged();
    }

    public int getRealLastPosition() {
        return mList.size();
    }

    public boolean isFadeTips() {
        return fadeTips;
    }

    public void resetDatas() {
        mList = new ArrayList<>();
    }
}
