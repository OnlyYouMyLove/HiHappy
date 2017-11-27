package com.best.hihappy.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.best.hihappy.R;

import java.util.List;

/**
 * Created by FuKaiqiang on 2017-11-26.
 */

public class LiveFragmentAdapter extends RecyclerView.Adapter<LiveFragmentAdapter.ViewHolder> {

    private List<String> mList;

    public LiveFragmentAdapter(List<String> list) {
        mList = list;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.tv_test);
        }
    }


    @Override
    public LiveFragmentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.live_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LiveFragmentAdapter.ViewHolder holder, int position) {
        holder.mTextView.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}
