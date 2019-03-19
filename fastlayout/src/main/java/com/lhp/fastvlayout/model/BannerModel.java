package com.lhp.fastvlayout.model;

public class BannerModel<T> extends BaseModel<T>  {

    private boolean autoScroll;
    private String leftAndRightPadding;//左右padding 单位dp
    private String bottomPadding;//底部到指示器之间的高度 单位dp
    private String indicatorLocation;//指示器位置
    private int indicatorLeftMargin;//指示器间隔
    private int mFocusImageId;//选中的指示器drawable
    private int mUnfocusImageId;//未选中的指示器drawable
    private float radis;//图片的角度数

    public BannerModel(String type) {
        super(type);
    }

    public float getRadis() {
        return radis;
    }

    public void setRadis(float radis) {
        this.radis = radis;
    }

    public int getIndicatorLeftMargin() {
        return indicatorLeftMargin;
    }

    public void setIndicatorLeftMargin(int indicatorLeftMargin) {
        this.indicatorLeftMargin = indicatorLeftMargin;
    }

    public int getmFocusImageId() {
        return mFocusImageId;
    }

    public void setmFocusImageId(int mFocusImageId) {
        this.mFocusImageId = mFocusImageId;
    }

    public int getmUnfocusImageId() {
        return mUnfocusImageId;
    }

    public void setmUnfocusImageId(int mUnfocusImageId) {
        this.mUnfocusImageId = mUnfocusImageId;
    }

    public String getIndicatorLocation() {
        return indicatorLocation;
    }

    public void setIndicatorLocation(String indicatorLocation) {
        this.indicatorLocation = indicatorLocation;
    }

    public boolean isAutoScroll() {
        return autoScroll;
    }

    public void setAutoScroll(boolean autoScroll) {
        this.autoScroll = autoScroll;
    }

    public String getLeftAndRightPadding() {
        return leftAndRightPadding;
    }

    public void setLeftAndRightPadding(String leftAndRightPadding) {
        this.leftAndRightPadding = leftAndRightPadding;
    }

    public String getBottomPadding() {
        return bottomPadding;
    }

    public void setBottomPadding(String bottomPadding) {
        this.bottomPadding = bottomPadding;
    }
}
