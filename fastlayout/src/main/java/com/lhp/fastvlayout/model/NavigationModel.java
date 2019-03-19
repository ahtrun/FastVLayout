package com.lhp.fastvlayout.model;

public class NavigationModel extends BaseModel {
    private int spanCount;//一行的item数量
    private boolean isOnlyImage;//是的只显示图片
    private float textSize;
    private int textColor;
    private boolean AutoExpand;//自动填充

    public NavigationModel(String type) {
        super(type);
    }

    public boolean isAutoExpand() {
        return AutoExpand;
    }

    public void setAutoExpand(boolean autoExpand) {
        AutoExpand = autoExpand;
    }

    public int getSpanCount() {
        return spanCount;
    }

    public void setSpanCount(int spanCount) {
        this.spanCount = spanCount;
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }
    //    public boolean isDotVisable() {
//        return isDotVisable;
//    }
//
//    public void setDotVisable(boolean dotVisable) {
//        isDotVisable = dotVisable;
//    }

    public boolean isOnlyImage() {
        return isOnlyImage;
    }

    public void setOnlyImage(boolean onlyImage) {
        isOnlyImage = onlyImage;
    }
}
