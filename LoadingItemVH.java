package com.jqielts.through.theworld.adapter.recyclerview;

import android.content.Context;
import android.view.View;
import android.widget.ProgressBar;

import com.jqielts.through.theworld.R;
import com.jqielts.through.theworld.adapter.recyclerview.base.ViewHolder;

/**
 * Created by Administrator on 2017/4/12.
 */

public class LoadingItemVH extends ViewHolder {

    private final ProgressBar pb;

    public LoadingItemVH(Context context, View itemView) {
        super(context, itemView);
        pb = (ProgressBar) itemView.findViewById(R.id.pb);
    }

    public ProgressBar getPb() {
        return pb;
    }
}
