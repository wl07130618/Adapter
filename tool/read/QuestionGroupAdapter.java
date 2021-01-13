package com.jqielts.through.theworld.adapter.recyclerview.custom.tool.read;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jqielts.through.theworld.R;
import com.jqielts.through.theworld.adapter.recyclerview.custom.BaseRecycleViewAdapter;
import com.jqielts.through.theworld.base.BaseActivity;
import com.jqielts.through.theworld.config.Config;
import com.jqielts.through.theworld.fragment.tool.read.QuestionFragment;
import com.jqielts.through.theworld.model.tool.evaluation.EvaluationModel;
import com.jqielts.through.theworld.preferences.Preferences;
import com.jqielts.through.theworld.util.bitmap.ImageUtil;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * Created by Administrator on 2017/4/11.
 */

public class QuestionGroupAdapter extends BaseRecycleViewAdapter {


    private static final int STATE_IDLE = 0;
    private static final int STATE_ACTIVED = 1;
    private static final int STATE_DEACTIVED = 2;
    private int videoState = STATE_IDLE;

    public static final int TYPE_HEAD = 0x0000;
    public static final int TYPE_INFOR = 0x0001;
    public static final int TYPE_DETAIL = 0x0002;
    public static final int TYPE_OFFER = 0x0003;

    protected static final int OFFER_ITEM_DETAIL = 3;

    protected static final int OFFER_ITEM_SHARE = 4;


    protected Preferences preferences;

    private Handler handler;

    long mLasttime = 0;
    int curPlayIndex=-1;

    private Context context;

    protected int mLayoutId;
    protected LayoutInflater mInflater;

    private boolean isStart = false;
    public static final int HEADVIEW_SIZE = 0;


    private BaseActivity activity;
    private QuestionFragment fragment;

    private boolean isResume = false;

    private int type = 0;

    public QuestionGroupAdapter(Context context, QuestionFragment fragment,int type){
        this.context = context;
        activity = (BaseActivity) context;
        this.fragment = fragment;
        this.type = type;
        preferences = Preferences.getInstance(context);

    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return datas.size()+HEADVIEW_SIZE;//有head需要加1
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        View headView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tool_evaluation_read_article_question_group_item, parent, false);
        viewHolder = new QuestionGroupHolder(headView, activity, fragment, type);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final int circlePosition = position - HEADVIEW_SIZE;

        QuestionGroupHolder holder = (QuestionGroupHolder) viewHolder;
        final EvaluationModel.SubQuestionStem bean = (EvaluationModel.SubQuestionStem) datas.get(circlePosition);

        String content = bean.getQuestionLeadDescription();

        String str = "<meta content=\"width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no\" name=\"viewport\" />"+content;
        holder.wv_show.loadDataWithBaseURL(Config.HOST, ImageUtil.adjustHTMLImage(str), "text/html", "UTF-8", Config.HOST);

        holder.text_content.setVisibility(View.VISIBLE);
        holder.text_content.setText(Html.fromHtml(str));
        holder.wv_show.setVisibility(View.GONE);

//        if(!isResume){
//            isResume = true;
//        }
        holder.adapter.getDatas().clear();
        holder.adapter.getDatas().addAll(bean.getSubQuestion2());
//        holder.adapter.notifyDataSetChanged();
//        holder.text_content
    }


    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
    }

    public class HeaderViewHolder extends RecyclerView.ViewHolder{

        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    public void setmHandler(Handler handler){
        this.handler = handler;
    }


}
