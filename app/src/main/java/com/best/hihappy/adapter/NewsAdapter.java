package com.best.hihappy.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.best.hihappy.R;
import com.best.hihappy.bean.NewsBean;
import com.best.hihappy.mvp.view.activity.News_WebView;
import com.bumptech.glide.Glide;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSessionConfig;

import java.util.List;

/**
 * Created by FuKaiqiang on 2017-09-10.
 */

public class NewsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<NewsBean.ResultBean.DataBean> mNewsBeanList;
    private Context mContext;
    private int mLastPosition;
    private static final int IMAGE_ONLY_ONE = 1;
    private static final int IMAGE_TWOORTHREE = 2;

    public NewsAdapter(List<NewsBean.ResultBean.DataBean> newsBeanList, Context context) {
        mNewsBeanList = newsBeanList;
        mContext = context;
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

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        if (viewType == IMAGE_TWOORTHREE) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_imagethree_item, viewGroup, false);
            final ImageThreeViewHolder viewHolder = new ImageThreeViewHolder(view);
            viewHolder.mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = viewHolder.getAdapterPosition();
                    String url = mNewsBeanList.get(position).getUrl();
                    SonicSessionConfig sessionConfig = new SonicSessionConfig.Builder().build();
                    boolean preloadSuccess = SonicEngine.getInstance().preCreateSession(url, sessionConfig);
                    Intent intent = new Intent(mContext, News_WebView.class);
                    intent.putExtra("url", url);
                    mContext.startActivity(intent);
                }
            });
            return viewHolder;
        } else {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.news_imageonly_item, viewGroup, false);
            final ImageOnlyOneViewHolder viewHolder = new ImageOnlyOneViewHolder(view);
            viewHolder.mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = viewHolder.getAdapterPosition();
                    String url = mNewsBeanList.get(position).getUrl();
                    SonicSessionConfig sessionConfig = new SonicSessionConfig.Builder().build();
                    boolean preloadSuccess = SonicEngine.getInstance().preCreateSession(url, sessionConfig);
                    Intent intent = new Intent(mContext, News_WebView.class);
                    intent.putExtra("url", url);
                    mContext.startActivity(intent);
                }
            });
            return viewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        if (viewHolder instanceof ImageThreeViewHolder) {
            ((ImageThreeViewHolder) viewHolder).mTv_title.setText(mNewsBeanList.get(position).getTitle());
            ((ImageThreeViewHolder) viewHolder).mTv_company.setText(mNewsBeanList.get(position).getAuthor_name());
            ((ImageThreeViewHolder) viewHolder).mTv_time.setText(mNewsBeanList.get(position).getDate());

            Glide.with(mContext).load(mNewsBeanList.get(position).getThumbnail_pic_s()).into(((ImageThreeViewHolder) viewHolder).mIv_image01);
            Glide.with(mContext).load(mNewsBeanList.get(position).getThumbnail_pic_s02()).into(((ImageThreeViewHolder) viewHolder).mIv_image02);
            Glide.with(mContext).load(mNewsBeanList.get(position).getThumbnail_pic_s03()).into(((ImageThreeViewHolder) viewHolder).mIv_image03);
        } else if (viewHolder instanceof ImageOnlyOneViewHolder) {
            ((ImageOnlyOneViewHolder) viewHolder).mTv_title.setText(mNewsBeanList.get(position).getTitle());
            ((ImageOnlyOneViewHolder) viewHolder).mTv_company.setText(mNewsBeanList.get(position).getAuthor_name());
            ((ImageOnlyOneViewHolder) viewHolder).mTv_time.setText(mNewsBeanList.get(position).getDate());

            Glide.with(mContext).load(mNewsBeanList.get(position).getThumbnail_pic_s()).into(((ImageOnlyOneViewHolder) viewHolder).mIv_image01);

        }
        if (viewHolder.getAdapterPosition() > mLastPosition) {
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(viewHolder.itemView, "scaleX", 0.5f, 1f);
            scaleX.start();
            mLastPosition = viewHolder.getLayoutPosition();
        }
    }


    @Override
    public int getItemCount() {
        return mNewsBeanList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mNewsBeanList.get(position).getThumbnail_pic_s03() == null) {
            return IMAGE_ONLY_ONE;
        } else {
            return IMAGE_TWOORTHREE;
        }
    }
}
