package com.lhp.fastvlayout.model;

import com.alibaba.android.vlayout.LayoutHelper;
import com.lhp.fastvlayout.listener.ItemListener;
import com.lhp.fastvlayout.listener.ScrollListener;

import java.util.List;

public class BaseModel<T> {

    private String type;//模型类型 banner,navigation,onePlusN,onePlusNEx,scrollFix,
    private float aspect;//宽高比
    private String container;//包含内容
    private List<T> data;
    private LayoutHelper layoutHelper;
    private int layoutId;
    private ItemListener<T> itemListener;
    private ScrollListener scrollListener;

    public BaseModel(String type) {
        this.type = type;
    }

    public ScrollListener getScrollListener() {
        return scrollListener;
    }

    public void setScrollListener(ScrollListener scrollListener) {
        this.scrollListener = scrollListener;
    }

    public ItemListener<T> getItemListener() {
        return itemListener;
    }

    public void setItemListener(ItemListener<T> itemListener) {
        this.itemListener = itemListener;
    }

    public int getLayoutId() {
        return layoutId;
    }

    public void setLayoutId(int layoutId) {
        this.layoutId = layoutId;
    }

    public LayoutHelper getLayoutHelper() {
        return layoutHelper;
    }

    public void setLayoutHelper(LayoutHelper layoutHelper) {
        this.layoutHelper = layoutHelper;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getAspect() {
        return aspect;
    }

    public void setAspect(float aspect) {
        this.aspect = aspect;
    }

    public String getContainer() {
        return container;
    }

    public void setContainer(String container) {
        this.container = container;
    }
}
