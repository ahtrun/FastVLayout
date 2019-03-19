package com.lhp.fastvlayout.core;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.lhp.fastvlayout.listener.ItemListener;
import com.lhp.fastvlayout.listener.ScrollListener;
import com.lhp.fastvlayout.model.BaseModel;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.List;

/**
 * Created By lhp
 * on 2019/3/14
 */
public class VBaseAdapter<T> extends DelegateAdapter.Adapter<VBaseHolder<T>> {
    //上下文
    private Context mContext;
    //布局文件资源ID
    private int mResLayout;
    private VirtualLayoutManager.LayoutParams mLayoutParams;
    //数据源
    private List<T> mDatas;
    //布局管理器
    private LayoutHelper mLayoutHelper;
    //继承VBaseHolder的Holder
    private Class<? extends VBaseHolder> mClazz;
    //回调监听
    private ItemListener mListener;
    //样式model
    private BaseModel<T> model;
    //滑动监听
    private ScrollListener mScrollListener;

    public VBaseAdapter(Context context) {
        mContext = context;
    }

    /**
     * <br/> 方法名称:VBaseAdapter
     * <br/> 方法详述:构造函数
     * <br/> 参数:<同上申明>
     */
    public VBaseAdapter(Context context,BaseModel<T> model, int mResLayout, Class<? extends VBaseHolder> mClazz,
                        LayoutHelper layoutHelper, ItemListener listener) {
        if (mClazz == null) {
            throw new RuntimeException("clazz is null,please check your params !");
        }
        if (mResLayout == 0) {
            throw new RuntimeException("res is null,please check your params !");
        }
        this.model=model;
        this.mContext = context;
        this.mResLayout = mResLayout;
        this.mLayoutHelper = layoutHelper;
        this.mClazz = mClazz;
        this.mListener = listener;
        this.mDatas = model.getData();
        //this.mLayoutParams = new VirtualLayoutManager.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
        // ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * <br/> 方法名称: VBaseAdapter
     * <br/> 方法详述: 设置数据源
     * <br/> 参数: mDatas，数据源
     * <br/> 返回值:  VBaseAdapter
     */
    public VBaseAdapter setData(BaseModel<T> model) {
        this.model=model;
        this.mDatas = model.getData();
        return this;
    }

    /**
     * <br/> 方法名称: setItem
     * <br/> 方法详述: 设置单个数据源
     * <br/> 参数: mItem，单个数据源
     * <br/> 返回值:  VBaseAdapter
     */
    public VBaseAdapter setItem(T mItem) {
        this.mDatas.add(mItem);
        return this;
    }

    /**
     * <br/> 方法名称: setLayout
     * <br/> 方法详述: 设置布局资源ID
     * <br/> 参数: mResLayout, 布局资源ID
     * <br/> 返回值:  VBaseAdapter
     */
    public VBaseAdapter setLayout(int mResLayout) {
        if (mResLayout == 0) {
            throw new RuntimeException("res is null,please check your params !");
        }
        this.mResLayout = mResLayout;
        return this;
    }

    /**
     * <br/> 方法名称: setLayoutHelper
     * <br/> 方法详述: 设置布局管理器
     * <br/> 参数: layoutHelper，管理器
     * <br/> 返回值:  VBaseAdapter
     */
    public VBaseAdapter setLayoutHelper(LayoutHelper layoutHelper) {
        this.mLayoutHelper = layoutHelper;
        return this;
    }

    /**
     * <br/> 方法名称: setHolder
     * <br/> 方法详述: 设置holder
     * <br/> 参数: mClazz,集成VBaseHolder的holder
     * <br/> 返回值:  VBaseAdapter
     */
    public VBaseAdapter setHolder(Class<? extends VBaseHolder> mClazz) {
        if (mClazz == null) {
            throw new RuntimeException("clazz is null,please check your params !");
        }
        this.mClazz = mClazz;
        return this;
    }

    /**
     * <br/> 方法名称: setListener
     * <br/> 方法详述: 传入监听，方便回调
     * <br/> 参数: listener
     * <br/> 返回值:  VBaseAdapter
     */
    public VBaseAdapter setListener(ItemListener listener) {
        this.mListener = listener;
        return this;
    }

    /**
     * <br/> 方法名称: setScrollListener
     * <br/> 方法详述: 传入监听，方便回调
     * <br/> 参数: mScrollListener
     * <br/> 返回值:  VBaseAdapter
     */
    public VBaseAdapter setScrollListener(ScrollListener mScrollListener) {
        this.mScrollListener = mScrollListener;
        return this;
    }

    /**
     * <br/> 方法名称: onCreateLayoutHelper
     * <br/> 方法详述: 继承elegateAdapter.Adapter后重写方法，告知elegateAdapter.Adapter使用何种布局管理器
     * <br/> 参数:
     * <br/> 返回值:  VBaseAdapter
     */
    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }


    public HashMap<Integer, Object> tags = new HashMap<>();

    /**
     * <br/> 方法名称: setTag
     * <br/> 方法详述: 设置mObject
     * <br/> 参数: mObject
     * <br/> 返回值:  VBaseAdapter
     */
    public VBaseAdapter setTag(int tag, Object mObject) {
        if (mObject != null) {
            tags.put(tag, mObject);
        }
        return this;
    }

    /**
     * <br/> 方法名称: onCreateViewHolder
     * <br/> 方法详述: 解析布局文件，返回传入holder的构造器
     */
    @Override
    public VBaseHolder<T> onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(mResLayout,parent,false);
//        Constants.inflate(parent.getContext(), parent, mResLayout);
        if (tags != null && tags.size() > 0) {
            for (int tag : tags.keySet()) {
                view.setTag(tag, tags.get(tag));
            }
        }
        try {
            Constructor<? extends VBaseHolder> mClazzConstructor = mClazz.getConstructor(View.class);
            if (mClazzConstructor != null) {
                return mClazzConstructor.newInstance(view);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * <br/> 方法名称: onBindViewHolder
     * <br/> 方法详述: 绑定数据
     * <br/> 参数:
     * <br/> 返回值:  VBaseAdapter
     */

    @Override
    public void onBindViewHolder(VBaseHolder holder, int position) {
        holder.setModel(model);
        holder.setListener(mListener);
        holder.setmScrollListener(mScrollListener);
        holder.setContext(mContext);
        holder.setData(position, mDatas.get(position));

    }


    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
