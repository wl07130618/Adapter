package com.jqielts.through.theworld.adapter.recyclerview.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.text.Editable;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.util.Linkify;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jqielts.through.theworld.R;
import com.jqielts.through.theworld.adapter.recyclerview.CommonAdapter;
import com.jqielts.through.theworld.application.MainApplication;
import com.jqielts.through.theworld.dynamic.widgets.CommentListView;
import com.jqielts.through.theworld.model.ArticleModel;
import com.jqielts.through.theworld.util.DensityUtil;
import com.jqielts.through.theworld.util.bitmap.GlideUtil;
import com.jqielts.through.theworld.view.custom.ProgressView;
import com.jqielts.through.theworld.view.recyclerview.GridItemDecoration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ViewHolder extends RecyclerView.ViewHolder
{
    private SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;

    public ViewHolder(Context context, View itemView)
    {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mViews = new SparseArray<View>();
    }


    public static ViewHolder createViewHolder(Context context, View itemView)
    {
        ViewHolder holder = new ViewHolder(context, itemView);
        return holder;
    }

    public static ViewHolder createViewHolder(Context context,
                                              ViewGroup parent, int layoutId)
    {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent,
                false);
        ViewHolder holder = new ViewHolder(context, itemView);
        return holder;
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId)
    {
        View view = mViews.get(viewId);
        if (view == null)
        {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }

    public View getConvertView()
    {
        return mConvertView;
    }




    /****以下为辅助方法*****/

    /**
     * 设置TextView的值
     *
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(int viewId, String text)
    {
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public ViewHolder setTextHtml(int viewId, String text)
    {
        TextView tv = getView(viewId);
        tv.setText(Html.fromHtml(text));
        return this;
    }

    public ViewHolder setTextSpan(int viewId, boolean isChange, String text, int colorChange, int startIndex, int endIndex, int flags){
        TextView tv = getView(viewId);
        Log.e("view_demo", "text == "+text);
        if(!TextUtils.isEmpty(text) && isChange){
            SpannableString spannableString = new SpannableString(text);
            spannableString.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(colorChange)), startIndex,endIndex, flags);
            tv.setText(spannableString);
        }else{
            tv.setText(text);
        }
        return this;
    }

    public ViewHolder setImageResource(int viewId, int resId)
    {
        ImageView view = getView(viewId);
        view.setImageResource(resId);
        return this;
    }

    public ViewHolder setImageBitmap(int viewId, Bitmap bitmap)
    {
        ImageView view = getView(viewId);
        view.setImageBitmap(bitmap);
        return this;
    }

    public ViewHolder setImageDrawable(int viewId, Drawable drawable)
    {
        ImageView view = getView(viewId);
        view.setImageDrawable(drawable);
        return this;
    }

    public ViewHolder setImageResource(Context context, int viewId, int drawable) {
        ImageView view = getView(viewId);
        GlideUtil.getInstance(context).setImageResourse(view, drawable);
        return this;
    }

    public ViewHolder setImageFile(Context context, int viewId, String path) {
        ImageView view = getView(viewId);
        GlideUtil.getInstance(context).setImageFile(view, path);
        return this;
    }

    public ViewHolder setImageUrl(Context context, int viewId, String imageUrl, int drawable) {
        ImageView view = getView(viewId);
        if (TextUtils.isEmpty(imageUrl)) {
            imageUrl = "";
            GlideUtil.getInstance(context).setImageResourse(view, drawable);
        } else {
            GlideUtil.getInstance(context).setImageUrl(view, imageUrl, drawable);
        }
        return this;
    }

    public ViewHolder setImageCircle(Context context, int viewId, String imageUrl, int drawable, int size) {
        ImageView view = getView(viewId);
        if (TextUtils.isEmpty(imageUrl)) {
            imageUrl = "";
            GlideUtil.getInstance(context).setImageCircle(view, drawable, size);
        } else {
            GlideUtil.getInstance(context).setImageCircle(view, imageUrl, drawable, size);
        }
        return this;
    }


    public ViewHolder setImageRound(Context context, int viewId, String imageUrl, int drawable, int width, int height) {
        ImageView view = getView(viewId);
        if (TextUtils.isEmpty(imageUrl)) {
            imageUrl = "";
            GlideUtil.getInstance(context).setImageRound(view, drawable, width, height);
        } else {
            GlideUtil.getInstance(context).setImageRound(view, imageUrl, drawable, width, height);
        }
        return this;
    }

    public ViewHolder setImageFrameLayout(Context context, int viewId, String imageUrl, int drawable, int height) {
        ImageView view = getView(viewId);
        FrameLayout.LayoutParams p = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height);
        view.setLayoutParams(p);
        if (TextUtils.isEmpty(imageUrl)) {
            imageUrl = "";
            GlideUtil.getInstance(context).setImageResourse(view, drawable);
        } else {
            GlideUtil.getInstance(context).setImageUrl(view, imageUrl, drawable);
        }
        return this;
    }


    public ViewHolder setImageFrameLayout(Context context, int viewId, String imageUrl, int drawable, int width, int height) {
        ImageView view = getView(viewId);
        FrameLayout.LayoutParams p = new FrameLayout.LayoutParams(width, height);
        view.setLayoutParams(p);
        if (TextUtils.isEmpty(imageUrl)) {
            imageUrl = "";
            GlideUtil.getInstance(context).setImageResourse(view, drawable);
        } else {
            GlideUtil.getInstance(context).setImageUrl(view, imageUrl, drawable);
        }
        return this;
    }

    public ViewHolder setImageLinearLayout(Context context, int viewId, String imageUrl, int drawable, int height) {
        ImageView view = getView(viewId);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, height);
        view.setLayoutParams(p);
        if (TextUtils.isEmpty(imageUrl)) {
            imageUrl = "";
            GlideUtil.getInstance(context).setImageResourse(view, drawable);
        } else {
            GlideUtil.getInstance(context).setImageUrl(view, imageUrl, drawable);
        }
        return this;
    }


    public ViewHolder setImageLinearLayout(Context context, int viewId, String imageUrl, int drawable, int width, int height) {
        ImageView view = getView(viewId);
        LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(width, height);
        view.setLayoutParams(p);
        if (TextUtils.isEmpty(imageUrl)) {
            imageUrl = "";
            GlideUtil.getInstance(context).setImageResourse(view, drawable);
        } else {
            GlideUtil.getInstance(context).setImageUrl(view, imageUrl, drawable);
        }
        return this;
    }


    public ViewHolder setImageRelativeLayout(Context context, int viewId, String imageUrl, int drawable, int height) {
        ImageView view = getView(viewId);
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, height);
        view.setLayoutParams(p);
        if (TextUtils.isEmpty(imageUrl)) {
            imageUrl = "";
            GlideUtil.getInstance(context).setImageResourse(view, drawable);
        } else {
            GlideUtil.getInstance(context).setImageUrl(view, imageUrl, drawable);
        }
        return this;
    }

    public ViewHolder setImageRelativeLayout(Context context, int viewId, String imageUrl, int drawable, int width, int height) {
        ImageView view = getView(viewId);
        RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(width, height);
        view.setLayoutParams(p);
        if (TextUtils.isEmpty(imageUrl)) {
            imageUrl = "";
            GlideUtil.getInstance(context).setImageResourse(view, drawable);
        } else {
            GlideUtil.getInstance(context).setImageUrl(view, imageUrl, drawable);
        }
        return this;
    }


    public ViewHolder setLinearLayoutParams(Context context, int viewId, int width, int height, int group) {

        switch (group){
            case 0:
                LinearLayout view = getView(viewId);
                LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(width, height);
                view.setLayoutParams(p);
                break;
            case 1:
                RelativeLayout view2 = getView(viewId);
                LinearLayout.LayoutParams p2 = new LinearLayout.LayoutParams(width, height);
                view2.setLayoutParams(p2);
                break;
            case 2:
                FrameLayout view3 = getView(viewId);
                LinearLayout.LayoutParams p3 = new LinearLayout.LayoutParams(width, height);
                view3.setLayoutParams(p3);
                break;
        }

        return this;
    }

    public ViewHolder setRelativeLayoutParams(Context context, int viewId, int width, int height, int group) {

        switch (group){
            case 0:
                LinearLayout view = getView(viewId);
                RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(width, height);
                view.setLayoutParams(p);
                break;
            case 1:
                RelativeLayout view2 = getView(viewId);
                RelativeLayout.LayoutParams p2 = new RelativeLayout.LayoutParams(width, height);
                view2.setLayoutParams(p2);
                break;
            case 2:
                FrameLayout view3 = getView(viewId);
                RelativeLayout.LayoutParams p3 = new RelativeLayout.LayoutParams(width, height);
                view3.setLayoutParams(p3);
                break;
        }

        return this;
    }

    public ViewHolder setFrameLayoutParams(Context context, int viewId, int width, int height, int group) {

        switch (group){
            case 0:
                LinearLayout view = getView(viewId);
                FrameLayout.LayoutParams p = new FrameLayout.LayoutParams(width, height);
                view.setLayoutParams(p);
                break;
            case 1:
                RelativeLayout view2 = getView(viewId);
                FrameLayout.LayoutParams p2 = new FrameLayout.LayoutParams(width, height);
                view2.setLayoutParams(p2);
                break;
            case 2:
                FrameLayout view3 = getView(viewId);
                FrameLayout.LayoutParams p3 = new FrameLayout.LayoutParams(width, height);
                view3.setLayoutParams(p3);
                break;
        }

        return this;
    }

    public ViewHolder setBackgroundColor(int viewId, int color)
    {
        View view = getView(viewId);
        view.setBackgroundColor(color);
        return this;
    }

    public ViewHolder setBackgroundRes(int viewId, int backgroundRes)
    {
        View view = getView(viewId);
        view.setBackgroundResource(backgroundRes);
        return this;
    }

    public ViewHolder setTextColor(int viewId, int textColor)
    {
        TextView view = getView(viewId);
        view.setTextColor(textColor);
        return this;
    }

    public ViewHolder setTextColorRes(int viewId, int textColorRes)
    {
        TextView view = getView(viewId);
        view.setTextColor(mContext.getResources().getColor(textColorRes));
        return this;
    }

    public ViewHolder setTextDrawableRes(int viewId, int drawableRes, int type)
    {
        TextView view = getView(viewId);
        Drawable draw = mContext.getResources().getDrawable(drawableRes);
        draw.setBounds( 0, 0, draw.getMinimumWidth(), draw.getMinimumHeight());

        switch (type){
            case 0:
                view.setCompoundDrawables(draw, null, null, null);
                break;
            case 1:
                view.setCompoundDrawables(null, draw, null, null);
                break;
            case 2:
                view.setCompoundDrawables(null, null, draw, null);
                break;
            case 3:
                view.setCompoundDrawables(null, null, null, draw);
                break;

        }
        return this;
    }

    public ViewHolder setchildListHorizontal(int viewId, String[] strings)
    {
        RecyclerView view = getView(viewId);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        view.setLayoutManager(linearLayoutManager);
        CommonAdapter adapter =  new CommonAdapter<String>(mContext, R.layout.home_item_professionals_countrys, Arrays.asList(strings)) {
            @Override
            protected void convert(ViewHolder holder, String item, final int position) {
                holder.setText(R.id.id_info, item.trim());
//                    holder.setVisible(R.id.view_root, position == 0 ? true : false);
//                    holder.setImageUrl(context, R.id.image, )
//                    holder.setImageResource(context, R.id.image, R.mipmap.icon_demo_counlstant);
            }};
        view.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return this;
    }

    public ViewHolder setchildListHorizontal(int viewId, CommonAdapter adapter)
    {
        RecyclerView view = getView(viewId);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        view.setLayoutManager(linearLayoutManager);
        view.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return this;
    }

    public ViewHolder setchildListVertical(int viewId, CommonAdapter adapter)
    {
        RecyclerView view = getView(viewId);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        view.setLayoutManager(linearLayoutManager);
        view.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return this;
    }

    public ViewHolder setchildListGridVertical(int viewId, CommonAdapter adapter)
    {
        RecyclerView view = getView(viewId);
        GridLayoutManager popularSchoolManager=new GridLayoutManager(mContext,8, GridLayoutManager.VERTICAL, false);
//        GridItemDecoration popularSchoolDecoration=new GridItemDecoration(DensityUtil.dip2px(context,context.getResources().getDimension(R.dimen.length_0)));
        view.setLayoutManager(popularSchoolManager);
//        view.addItemDecoration(popularSchoolDecoration);
        view.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        return this;
    }

    @SuppressLint("NewApi")
    public ViewHolder setAlpha(int viewId, float value)
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
        {
            getView(viewId).setAlpha(value);
        } else
        {
            // Pre-honeycomb hack to set Alpha value
            AlphaAnimation alpha = new AlphaAnimation(value, value);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            getView(viewId).startAnimation(alpha);
        }
        return this;
    }

    public ViewHolder setVisible(int viewId, boolean visible)
    {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
        return this;
    }

    public ViewHolder setInVisible(int viewId, boolean visible)
    {
        View view = getView(viewId);
        view.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
        return this;
    }

    public ViewHolder linkify(int viewId)
    {
        TextView view = getView(viewId);
        Linkify.addLinks(view, Linkify.ALL);
        return this;
    }

    public ViewHolder setTypeface(Typeface typeface, int... viewIds)
    {
        for (int viewId : viewIds)
        {
            TextView view = getView(viewId);
            view.setTypeface(typeface);
            view.setPaintFlags(view.getPaintFlags() | Paint.SUBPIXEL_TEXT_FLAG);
        }
        return this;
    }

    public ViewHolder setProgress(int viewId, int progress)
    {
        ProgressBar view = getView(viewId);
        view.setProgress(progress);
        return this;
    }

    public ViewHolder setProgress(int viewId, int progress, int max)
    {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        view.setProgress(progress);
        return this;
    }

    public ViewHolder setMax(int viewId, int max)
    {
        ProgressBar view = getView(viewId);
        view.setMax(max);
        return this;
    }

    public ViewHolder setRating(int viewId, float rating)
    {
        RatingBar view = getView(viewId);
        view.setRating(rating);
        return this;
    }

    public ViewHolder setRating(int viewId, float rating, int max)
    {
        RatingBar view = getView(viewId);
        view.setMax(max);
        view.setRating(rating);
        return this;
    }

    public ViewHolder setTag(int viewId, Object tag)
    {
        View view = getView(viewId);
        view.setTag(tag);
        return this;
    }

    public ViewHolder setTag(int viewId, int key, Object tag)
    {
        View view = getView(viewId);
        view.setTag(key, tag);
        return this;
    }

    public ViewHolder setChecked(int viewId, boolean checked)
    {
        Checkable view = (Checkable) getView(viewId);
        view.setChecked(checked);
        return this;
    }

    /**
     * 关于事件的
     */
    public ViewHolder setOnClickListener(int viewId,
                                         View.OnClickListener listener)
    {
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public ViewHolder setOnTouchListener(int viewId,
                                         View.OnTouchListener listener)
    {
        View view = getView(viewId);
        view.setOnTouchListener(listener);
        return this;
    }


    public ViewHolder addTextChangedListener(int viewId,
                                         TextWatcher listener)
    {
        EditText view = getView(viewId);
        view.addTextChangedListener(listener);
        return this;
    }

    public ViewHolder setOnLongClickListener(int viewId,
                                             View.OnLongClickListener listener)
    {
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

    /**
     * Sets the selected status of a ImageView.
     *
     * @param viewId  The view id.
     * @param checked The checked status;
     * @return The BaseAdapterHelper for chaining.
     */
    public ViewHolder setSelected(int viewId, boolean checked) {
        ImageView view = (ImageView) getView(viewId);
        view.setSelected(checked);
        return this;
    }

//    public ViewHolder setDatas(int viewId, List<ArticleModel.PostCommentBean.WordBean.SwordBean> mDatas) {
//        CommentListView view = (CommentListView) getView(viewId);
//        view.setDatas(mDatas);
//        return this;
//    }


    public ViewHolder setProgressText(int viewId, String progressText, String statusText, int progressIndex)
    {
        ProgressView tv = getView(viewId);
        tv.setProgressText(progressText);
        tv.setStatusText(statusText);
        tv.setProgressIndex(progressIndex);
        return this;
    }


}
