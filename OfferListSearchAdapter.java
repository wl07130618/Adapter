package com.jqielts.through.theworld.adapter;

import android.content.Context;
import android.os.Handler;

import com.jqielts.through.theworld.R;
import com.jqielts.through.theworld.adapter.helper.BaseAdapterHelper;
import com.jqielts.through.theworld.adapter.helper.EnhancedQuickAdapter;
import com.jqielts.through.theworld.model.home.offer.OfferSearchModel;
import com.jqielts.through.theworld.util.LogUtils;

import java.util.List;

/**
 * 商品列表 Adapter
 * Created by Liuhuacheng.
 * Created time 16/7/17.
 */
public class OfferListSearchAdapter extends EnhancedQuickAdapter<OfferSearchModel.OfferListSearchBean> {
    private Handler handler;

    private boolean isChice[];


    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public OfferListSearchAdapter(Context context, int layoutResId) {
        super(context, layoutResId);
    }

    public OfferListSearchAdapter(Context context, int layoutResId, List<OfferSearchModel.OfferListSearchBean> data) {
        super(context, layoutResId, data);
        isChice = new boolean[data.size()];
        for (int i=0 ; i<isChice.length ; i++)
            isChice[i] = false;
    }

    @Override
    protected void convert(final BaseAdapterHelper helper, OfferSearchModel.OfferListSearchBean item, boolean itemChanged) {

        String tag = item.getTag();
        String tagID = item.getTagID();
        helper.setText(R.id.offer_list_tag_item, tag);

        if (isChice[data.indexOf(item)]){
            helper.setText(R.id.offer_list_tag_item, tagID);
//            helper.setTextColor(R.id.offer_list_tag_item, R.color.white);
        }
        else{
            helper.setText(R.id.offer_list_tag_item, tag);
//            helper.setTextColor(R.id.offer_list_tag_item, R.color.black);
        }

    }

    public void chiceState(int post)
    {
        isChice[post]=isChice[post]==true?false:true;

        notifyDataSetChanged();
    }
}