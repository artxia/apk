package com.pj567.movie.ui.adapter;

import android.graphics.Color;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.pj567.movie.R;
import com.pj567.movie.bean.VodInfo;

import java.util.ArrayList;

/**
 * @author pj567
 * @date :2020/12/22
 * @description:
 */
public class SeriesAdapter extends BaseQuickAdapter<VodInfo.VodSeries, BaseViewHolder> {
    public SeriesAdapter() {
        super(R.layout.item_series_layout, new ArrayList<>());
    }

    @Override
    protected void convert(BaseViewHolder helper, VodInfo.VodSeries item) {
        TextView tvSeries = helper.getView(R.id.tvSeries);
        if (item.selected) {
            tvSeries.setTextColor(mContext.getResources().getColor(R.color.color_02F8E1));
        } else {
            tvSeries.setTextColor(Color.WHITE);
        }
        helper.setText(R.id.tvSeries, item.name);
    }
}