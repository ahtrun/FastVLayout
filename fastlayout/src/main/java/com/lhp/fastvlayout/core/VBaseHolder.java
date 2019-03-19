package com.lhp.fastvlayout.core;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.lhp.fastvlayout.listener.ItemListener;
import com.lhp.fastvlayout.listener.ScrollListener;
import com.lhp.fastvlayout.model.BaseModel;

import butterknife.ButterKnife;

/**
 * Created By lhp
 * on 2019/3/14
 */
public class VBaseHolder<T> extends RecyclerView.ViewHolder  {
    /**
     * 点击事件监听
     */
    public ItemListener mListener;
    /**
     * 滑动事件监听
     */
    public ScrollListener mScrollListener;
    public Context mContext;
    public T mData;
    public int position;
    /**
     * 决定样式和布局的model
     */
    public BaseModel<T> model;

    public void setModel(BaseModel<T> model) {
        this.model = model;
    }

    public VBaseHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        init();
    }

    public void init() {

    }

    public void setContext(Context context) {
        mContext = context;
    }

    public void setListener(ItemListener listener) {
        mListener = listener;
    }

    public void setmScrollListener(ScrollListener mScrollListener) {
        this.mScrollListener = mScrollListener;
    }

    public void setData(int ps, T mData) {
        this.mData = mData;
        position = ps;

    }

}
