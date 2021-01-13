package com.jqielts.through.theworld.adapter.recyclerview.custom.tool.read;


import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.hyphenate.helpdesk.model.Content;
import com.jqielts.through.theworld.R;
import com.jqielts.through.theworld.fragment.tool.read.QuestionFragment;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Administrator on 2017/5/2.
 */

public class QuestionGroupHolder extends RecyclerView.ViewHolder {

    public WebView wv_show;
    public RecyclerView recyclerView;
    private WebSettings webSettings;

    public QuestionAdapter adapter;
    private LinearLayoutManager manager;

    public TextView text_content;

    private QuestionFragment fragment;

    private int viewType;


    public QuestionGroupHolder(View itemView, Activity context, QuestionFragment fragment, int type) {
        super(itemView);

        wv_show = (WebView) itemView.findViewById(R.id.wv_show);
        recyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView);

        text_content = (TextView) itemView.findViewById(R.id.text_content);

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

        manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
//        manager.setStackFromEnd(true);//关键recyclerView.setLayoutManager(mManager);

        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(manager);

        adapter = new QuestionAdapter(context, fragment, type);
        recyclerView.setAdapter(adapter);


    }
}
