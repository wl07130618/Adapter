package com.jqielts.through.theworld.adapter.recyclerview.custom.tool.gpa;

import android.content.Context;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jqielts.through.theworld.R;
import com.jqielts.through.theworld.adapter.recyclerview.custom.BaseRecycleViewAdapter;
import com.jqielts.through.theworld.base.BaseActivity;
import com.jqielts.through.theworld.model.tool.GpaCalculatorModel;
import com.jqielts.through.theworld.preferences.Preferences;

/**
 * Created by Administrator on 2017/4/11.
 */

public class GpaCalculatorAdapter extends BaseRecycleViewAdapter {


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

    public GpaCalculatorAdapter(Context context){
        this.context = context;
        activity = (BaseActivity) context;
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
        View headView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tool_gpa_calculator_item, parent, false);
        viewHolder = new GpaCalculatorHolder(headView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final int circlePosition = position - HEADVIEW_SIZE;

        GpaCalculatorHolder holder = (GpaCalculatorHolder) viewHolder;
        final GpaCalculatorModel.GpaCalculatorBean bean = (GpaCalculatorModel.GpaCalculatorBean) datas.get(circlePosition);

        final String discipline = bean.getScore();
        String results = bean.getCredit();

        holder.item_title.setText("科目"+(circlePosition+1));

        if (holder.item_results.getTag() != null && holder.item_results.getTag() instanceof TextWatcher) {
            holder.item_results.removeTextChangedListener((TextWatcher) holder.item_results.getTag());
        }

        holder.item_results.setText(!TextUtils.isEmpty(results) ? results : "");

        TextWatcher resultsWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                bean.setCredit(s.toString());
            }
        };
        holder.item_results.addTextChangedListener(resultsWatcher);
        holder.item_results.setTag(resultsWatcher);


        if (holder.item_discipline.getTag() != null && holder.item_discipline.getTag() instanceof TextWatcher) {
            holder.item_discipline.removeTextChangedListener((TextWatcher) holder.item_discipline.getTag());
        }

        holder.item_discipline.setText(!TextUtils.isEmpty(discipline) ? discipline : "");

        TextWatcher disciplineWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                bean.setScore(s.toString());
            }
        };
        holder.item_discipline.addTextChangedListener(disciplineWatcher);
        holder.item_discipline.setTag(disciplineWatcher);

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
