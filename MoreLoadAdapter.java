package com.jqielts.through.theworld.adapter.recyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.jqielts.through.theworld.adapter.recyclerview.base.ItemViewDelegate;
import com.jqielts.through.theworld.adapter.recyclerview.base.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/4/12.
 */

public abstract class MoreLoadAdapter<T> extends MultiItemTypeAdapter<T> {

    protected Context mContext;
    protected int mLayoutId;
    protected List<T> mDatas;
    protected LayoutInflater mInflater;

    private final RecyclerView mRecyclerView;
    private boolean isLoading;
    private int totalItemCount;
    private int lastVisibleItemPosition;
    //当前滚动的position下面最小的items的临界值
    private int visibleThreshold = 5;

    public MoreLoadAdapter(final Context context, final int layoutId, List<T> datas, RecyclerView recyclerView) {
        super(context, datas);
        mRecyclerView = recyclerView;

        if (mRecyclerView.getLayoutManager() instanceof LinearLayoutManager) {
            final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            //mRecyclerView添加滑动事件监听
            mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
                    totalItemCount = linearLayoutManager.getItemCount();
                    lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition();
//                    Log.d("test", "totalItemCount =" + totalItemCount + "-----" + "lastVisibleItemPosition =" + lastVisibleItemPosition);
                    if (!isLoading && totalItemCount <= (lastVisibleItemPosition + visibleThreshold) && totalItemCount%10 == 0) {
                        //此时是刷新状态
                        if (mMoreDataListener != null)
                            mMoreDataListener.loadMoreData();
                        isLoading = true;
                    }

//                    if (!isLoading && totalItemCount <= (lastVisibleItemPosition + visibleThreshold)) {
//                        //此时是刷新状态
//                        if (mMoreDataListener != null)
//                            mMoreDataListener.loadMoreData();
//                        isLoading = true;
//                    }
                }
            });
        }

        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        mDatas = datas;

        addItemViewDelegate(new ItemViewDelegate<T>()
        {
            @Override
            public int getItemViewLayoutId()
            {
                return layoutId;
            }

            @Override
            public boolean isForViewType( T item, int position)
            {
                return true;
            }

            @Override
            public void convert(ViewHolder holder, T t, int position)
            {
                MoreLoadAdapter.this.convert(holder, t, position);
            }
        });
    }

    protected abstract void convert(ViewHolder holder, T t, int position);


    public void setLoaded() {
        isLoading = false;
    }

    private LoadMoreDataListener mMoreDataListener;

    //加载更多监听方法
    public void setOnMoreDataLoadListener(LoadMoreDataListener onMoreDataLoadListener) {
        mMoreDataListener = onMoreDataLoadListener;
    }

    private RecyclerOnItemClickListener mOnitemClickListener;

    //点击事件监听方法
    public void setOnItemClickListener(RecyclerOnItemClickListener onItemClickListener) {
        mOnitemClickListener = onItemClickListener;
    }

    public interface RecyclerOnItemClickListener {
        public abstract void onClick(View view);
    }

    public interface LoadMoreDataListener {
        public abstract void loadMoreData();
    }
}
