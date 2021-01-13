package com.jqielts.through.theworld.adapter.recyclerview.custom.tool.read;

import android.content.Context;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.jqielts.through.theworld.R;
import com.jqielts.through.theworld.adapter.recyclerview.custom.BaseRecycleViewAdapter;
import com.jqielts.through.theworld.adapter.recyclerview.custom.tool.gpa.GpaCalculatorHolder;
import com.jqielts.through.theworld.base.BaseActivity;
import com.jqielts.through.theworld.fragment.tool.read.QuestionFragment;
import com.jqielts.through.theworld.model.tool.GpaCalculatorModel;
import com.jqielts.through.theworld.model.tool.evaluation.EvaluationModel;
import com.jqielts.through.theworld.preferences.Preferences;
import com.jqielts.through.theworld.util.LogUtils;

/**
 * Created by Administrator on 2017/4/11.
 */

public class AnswerEditAdapter extends BaseRecycleViewAdapter {


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

    private int type = 0;



    public AnswerEditAdapter(Context context, QuestionFragment fragment, int type){
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
        View headView = null;
        if (this.type == 0){
            headView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tool_evaluation_read_edit_item, parent, false);
        }else{
            headView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tool_evaluation_listener_edit_item, parent, false);
        }
        viewHolder = new AnswerEditHolder(headView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final int circlePosition = position - HEADVIEW_SIZE;

        final AnswerEditHolder holder = (AnswerEditHolder) viewHolder;
        final EvaluationModel.AnswerItem bean = (EvaluationModel.AnswerItem) datas.get(circlePosition);

        final String key = bean.getOptionKey();
        final String inputScore = bean.getInputScore();

        final String discipline = bean.getOptionDescription();

        holder.item_title.setText(key+".");

        holder.item_content.setVisibility(TextUtils.isEmpty(discipline) ? View.GONE : View.VISIBLE);

        holder.item_content.setText(discipline);

        if (holder.item_discipline.getTag() != null && holder.item_discipline.getTag() instanceof TextWatcher) {
            holder.item_discipline.removeTextChangedListener((TextWatcher) holder.item_discipline.getTag());
        }

        holder.item_discipline.setText(!TextUtils.isEmpty(inputScore) ? inputScore : "");

        TextWatcher disciplineWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                bean.setInputScore(s.toString());
            }
        };
        holder.item_discipline.addTextChangedListener(disciplineWatcher);
        holder.item_discipline.setTag(disciplineWatcher);


        holder.item_discipline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.item_discipline.setFocusable(true);
                holder.item_discipline.setFocusableInTouchMode(true);
                holder.item_discipline.requestFocus();
                holder.item_discipline.findFocus();
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(holder.item_discipline, InputMethodManager.SHOW_FORCED);// 显示输入法
            }
        });

        holder.item_discipline.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Rect r = new Rect();
                        holder.item_discipline.getWindowVisibleDisplayFrame(r);
                        int screenHeight =  holder.item_discipline.getRootView()
                                .getHeight();
                        int heightDifference = screenHeight - (r.bottom);
                        if (heightDifference > 200) {
                            //软键盘显示
                            holder.item_discipline.setFocusable(true);
                        } else {
                            //软键盘隐藏
                            holder.item_discipline.clearFocus();
                        }
                    }
                });

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
