package com.lhp.fastvlayout.model;

import com.lhp.fastvlayout.listener.ItemDataBind;

/**
 * Created By lhp
 * on 2019/3/19
 */
public class StickyModel extends BaseModel {
    private boolean mStickyStart=true;//false=吸低，true=吸顶
    private int mOffset = 0;//距离顶部或者底部的偏移量
    private ItemDataBind itemDataBind;

    public ItemDataBind getItemDataBind() {
        return itemDataBind;
    }

    public void setItemDataBind(ItemDataBind itemDataBind) {
        this.itemDataBind = itemDataBind;
    }

    public StickyModel(String type) {
        super(type);
    }

    public boolean ismStickyStart() {
        return mStickyStart;
    }

    public void setmStickyStart(boolean mStickyStart) {
        this.mStickyStart = mStickyStart;
    }

    public int getmOffset() {
        return mOffset;
    }

    public void setmOffset(int mOffset) {
        this.mOffset = mOffset;
    }
}
