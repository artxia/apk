package com.pj567.movie.ui.adapter;

import android.graphics.Color;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pj567.movie.R;
import com.pj567.movie.bean.LiveChannel;

import java.util.ArrayList;

/**
 * @author pj567
 * @date :2021/1/12
 * @description:
 */
public class LiveChannelAdapter extends BaseQuickAdapter<LiveChannel, BaseViewHolder> {
    public LiveChannelAdapter() {
        super(R.layout.item_live_channel_layout, new ArrayList<>());
    }

    @Override
    protected void convert(BaseViewHolder helper, LiveChannel item) {
        TextView tvChannelNum = helper.getView(R.id.tvChannelNum);
        TextView tvChannel = helper.getView(R.id.tvChannel);
        tvChannelNum.setText(String.format("%s", item.getChannelNum()));
        tvChannel.setText(String.format("%s", item.getChannelName()));
        if (item.selected) {
            tvChannelNum.setTextColor(mContext.getResources().getColor(R.color.color_02F8E1));
            tvChannel.setTextColor(mContext.getResources().getColor(R.color.color_02F8E1));
        } else {
            tvChannelNum.setTextColor(Color.WHITE);
            tvChannel.setTextColor(Color.WHITE);
        }
    }
}