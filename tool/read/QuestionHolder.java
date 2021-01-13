package com.jqielts.through.theworld.adapter.recyclerview.custom.tool.read;


import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.GridLayoutManager;

import android.view.View;
import android.widget.TextView;

import com.jqielts.through.theworld.R;
import com.jqielts.through.theworld.adapter.recyclerview.CommonAdapter;
import com.jqielts.through.theworld.view.recyclerview.GridItemDecoration;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * Created by Administrator on 2017/5/2.
 */

public class QuestionHolder extends RecyclerView.ViewHolder {

    public WebView wv_show;
    public RecyclerView recyclerView;
    private WebSettings webSettings;

    public AnswerEditAdapter editAdapter;
    public CommonAdapter adapter;

    public LinearLayoutManager linearManager;
    public GridLayoutManager gridManager;
    public GridItemDecoration gridDecoration;

    public TextView text_content;

    public QuestionHolder(View itemView, Context context) {
        super(itemView);

        wv_show = (WebView) itemView.findViewById(R.id.wv_show);
        recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);
        text_content = (TextView) itemView.findViewById(R.id.text_content);
//        recyclerView.setOnTouchListener((v, event) -> {
//            mRvCanScrollVertically = true;
//            return false;
//        });
        WebSettings webSettings = wv_show.getSettings();
//        XWalkSettings webSettings = wv_show.getSettings();

        wv_show.setInitialScale(100);//比例缩放
        //设置缓存模式
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        int screenDensity = getResources().getDisplayMetrics().densityDpi ;
        WebSettings.ZoomDensity zoomDensity = WebSettings.ZoomDensity.MEDIUM ;

        //设置Web视图
        wv_show.setWebViewClient(new WebViewClient());

        webSettings.setUseWideViewPort(true);//适应手机屏幕
        webSettings.setLoadWithOverviewMode(true);//适应手机屏幕

        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setBuiltInZoomControls(true);




    }
}
