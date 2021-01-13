package com.jqielts.through.theworld.adapter.recyclerview.custom.tool.read;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.jqielts.through.theworld.R;
import com.jqielts.through.theworld.activity.abroad.alive.DiaryLibraryActivity;
import com.jqielts.through.theworld.adapter.recyclerview.CommonAdapter;
import com.jqielts.through.theworld.adapter.recyclerview.base.ViewHolder;
import com.jqielts.through.theworld.adapter.recyclerview.custom.BaseRecycleViewAdapter;
import com.jqielts.through.theworld.base.BaseActivity;
import com.jqielts.through.theworld.config.Config;
import com.jqielts.through.theworld.fragment.tool.read.QuestionFragment;
import com.jqielts.through.theworld.model.tool.evaluation.EvaluationModel;
import com.jqielts.through.theworld.preferences.Preferences;
import com.jqielts.through.theworld.util.DensityUtil;
import com.jqielts.through.theworld.util.LogUtils;
import com.jqielts.through.theworld.util.bitmap.ImageUtil;
import com.jqielts.through.theworld.view.recyclerview.GridItemDecoration;

import java.util.List;

/**
 * Created by Administrator on 2017/4/11.
 */

public class QuestionAdapter extends BaseRecycleViewAdapter {


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
    private int type;

    public QuestionAdapter(Context context, QuestionFragment fragment, int type){
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
        View headView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tool_evaluation_read_article_question_item, parent, false);
        viewHolder = new QuestionHolder(headView, context);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        final int circlePosition = position - HEADVIEW_SIZE;
//        LogUtils.showLog("AnswerEditAdapter 4", "position == "+position);

        final QuestionHolder holder = (QuestionHolder) viewHolder;
        final EvaluationModel.QuestionItem bean = (EvaluationModel.QuestionItem) datas.get(circlePosition);

        String content = bean.getQuestionStemDescription();

        String isRichTextStr = bean.getIsRichText();

        int isRichText = Integer.parseInt(isRichTextStr);


        String typeStr = bean.getQuestionType();

        int type = Integer.parseInt(typeStr);

        final String isMultiselect = bean.getIsMultiselect();


        String str = "<meta content=\"width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no\" name=\"viewport\" />"+content;

        if (isRichText == 0){
            holder.text_content.setVisibility(View.VISIBLE);
            holder.text_content.setText(Html.fromHtml(str));
            holder.wv_show.setVisibility(View.GONE);
        }else{
            holder.text_content.setText(Html.fromHtml(str));

            holder.wv_show.loadDataWithBaseURL(Config.HOST, ImageUtil.adjustHTMLImage(str), "text/html", "UTF-8", Config.HOST);
            holder.wv_show.setVisibility(View.VISIBLE);
            holder.text_content.setVisibility(View.GONE);

        }

        switch (type){
            case 0:
                holder.linearManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                };
                holder.recyclerView.setHasFixedSize(true);
                holder.recyclerView.setItemAnimator(new DefaultItemAnimator());
                holder.recyclerView.setLayoutManager(holder.linearManager);

                final List<EvaluationModel.AnswerItem> answers = bean.getOptionList();

                holder.adapter = new CommonAdapter<EvaluationModel.AnswerItem>(context, R.layout.tool_evaluation_read_select_item, answers) {
                    @Override
                    protected void convert(ViewHolder holder2, final EvaluationModel.AnswerItem item, final int position) {
                        String tagUrl = item.getOptionDescription();
                        final String key = item.getOptionKey();
                        boolean itemSelect = item.isSelect();


//                LogUtils.showLog(TAG, "position : "+position+" , answers["+index+"] : "+answers[index]);
                        holder2.setText(R.id.image_answer, key+". ")
                                .setText(R.id.text_answer, tagUrl)
                                .setBackgroundRes(R.id.id_info, itemSelect ? R.drawable.tool_evaluation_read_frame_background_select : R.drawable.tool_vocabulary_exercise_frame_background_normal)
//                                .setTextColorRes(R.id.image_answer, itemSelect ? R.color.white : R.color.tool_vocabulary_exercise_item_number)
//                                .setTextColorRes(R.id.text_answer, itemSelect ? R.color.white : R.color.tool_vocabulary_exercise_item_answer)
                                .setOnClickListener(R.id.id_info, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
//                                        hideKeyboard();
                                        if (!isMultiselect.equals("1")){
                                            for (EvaluationModel.AnswerItem bean : answers){
                                                bean.setSelect(false);
                                                bean.setInputScore("");
                                            }
                                            item.setSelect(true);
                                        }else{
                                            item.setSelect(!item.isSelect());
                                        }
                                        item.setInputScore(key);
                                        hideKeyboard();
                                        notifyDataSetChanged();

                                    }
                                });
                    }
                };
                holder.recyclerView.setAdapter(holder.adapter);
                break;
            case 1:
                if (this.type == 0){
                    if (bean.getOptionList().size() > 1 ){
                        holder.gridManager=new GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false){
                            @Override
                            public boolean canScrollVertically() {
                                return false;
                            }
                        };

//                        holder.gridManager.setStackFromEnd(true);//关键recyclerView.setLayoutManager(mManager);
                    }else{
                        holder.gridManager=new GridLayoutManager(context, 1, GridLayoutManager.VERTICAL, false){
                            @Override
                            public boolean canScrollVertically() {
                                return false;
                            }
                        };
//                        holder.gridManager.setStackFromEnd(true);//关键recyclerView.setLayoutManager(mManager);
                    }
                    holder.recyclerView.setLayoutManager(holder.gridManager);
                    holder.gridDecoration=new GridItemDecoration(DensityUtil.dip2px(context,context.getResources().getDimension(R.dimen.length_0)));
                    holder.recyclerView.addItemDecoration(holder.gridDecoration);
                }else{
                    holder.linearManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false) {
                        @Override
                        public boolean canScrollVertically() {
                            return false;
                        }
                    };
                    holder.recyclerView.setHasFixedSize(true);
                    holder.recyclerView.setItemAnimator(new DefaultItemAnimator());
                    holder.recyclerView.setLayoutManager(holder.linearManager);

                }

                holder.editAdapter = new AnswerEditAdapter(context, fragment, this.type);
                holder.recyclerView.setAdapter(holder.editAdapter);

                holder.editAdapter.getDatas().clear();
                holder.editAdapter.getDatas().addAll(bean.getOptionList());
//                holder.editAdapter.notifyDataSetChanged();
                break;
            case 2:
                holder.linearManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false) {
                    @Override
                    public boolean canScrollVertically() {
                        return false;
                    }
                };
                holder.recyclerView.setHasFixedSize(true);
                holder.recyclerView.setItemAnimator(new DefaultItemAnimator());
                holder.recyclerView.setLayoutManager(holder.linearManager);

                final List<EvaluationModel.AnswerItem> answers2 = bean.getOptionList();

                holder.adapter = new CommonAdapter<EvaluationModel.AnswerItem>(context, R.layout.tool_evaluation_read_opinion_item, answers2) {
                    @Override
                    protected void convert(ViewHolder holder2, final EvaluationModel.AnswerItem item, final int position) {
                        String tagUrl = item.getOptionDescription();
                        final String key = item.getOptionKey();
                        boolean itemSelect = item.isSelect();


//                LogUtils.showLog(TAG, "position : "+position+" , answers["+index+"] : "+answers[index]);
                        holder2.setText(R.id.text_answer, tagUrl)
                                .setBackgroundRes(R.id.id_info, itemSelect ? R.drawable.tool_evaluation_read_frame_background_select : R.drawable.tool_vocabulary_exercise_frame_background_normal)
//                                .setTextColorRes(R.id.text_answer, itemSelect ? R.color.tool_vocabulary_exercise_item_answer : R.color.tool_vocabulary_exercise_item_answer)
                                .setOnClickListener(R.id.id_info, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
//                                        hideKeyboard();
                                        for (EvaluationModel.AnswerItem bean : answers2){
                                            bean.setSelect(false);
                                            bean.setInputScore("");
                                        }
                                        item.setSelect(true);
                                        item.setInputScore(key);
                                        hideKeyboard();
                                        notifyDataSetChanged();

                                    }
                                });
                    }
                };
                holder.recyclerView.setAdapter(holder.adapter);
                break;
        }






//        holder.text.setText(Html.fromHtml(str));
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

    /**
     * 隐藏软键盘
     */
    protected void hideKeyboard() {
        InputMethodManager inputManager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        if (activity.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (activity.getCurrentFocus() != null)
                inputManager.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


}
