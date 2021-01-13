package com.jqielts.through.theworld.adapter;

import android.content.Context;
import android.os.Handler;

import com.jqielts.through.theworld.R;
import com.jqielts.through.theworld.adapter.helper.BaseAdapterHelper;
import com.jqielts.through.theworld.adapter.helper.EnhancedQuickAdapter;
import com.jqielts.through.theworld.model.home.offer.OfferTagModel;

import java.util.List;

/**
 * 商品列表 Adapter
 * Created by Liuhuacheng.
 * Created time 16/7/17.
 */
public class OfferListTagAdapter extends EnhancedQuickAdapter<OfferTagModel.OfferListTagBean> {
    private Handler handler;

    private boolean isChice[];


    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public OfferListTagAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
    }

    public OfferListTagAdapter(Context context, int layoutResId, List<OfferTagModel.OfferListTagBean> data) {
        super(context, layoutResId, data);
        isChice = new boolean[data.size()];
        for (int i=0 ; i<isChice.length ; i++)
            isChice[i] = false;
    }

    @Override
    protected void convert(final BaseAdapterHelper helper, OfferTagModel.OfferListTagBean item, boolean itemChanged) {

        String tag = item.getTag();
        String tagID = item.getTagID();
        helper.setText(R.id.offer_list_tag_item, tag)
        .setBackgroundRes(R.id.offer_list_tag_item, isChice[data.indexOf(item)] ? R.drawable.shape_frame_bg_color: R.drawable.shape_frame_white)
        .setTextColorRes(R.id.offer_list_tag_item, isChice[data.indexOf(item)] ? R.color.white: R.color.black);

    }

    public void chiceState(int post)
    {
        isChice[post]=isChice[post]==true?false:true;
        data.get(post).setChoice(isChice[post]);

        notifyDataSetChanged();
    }
}