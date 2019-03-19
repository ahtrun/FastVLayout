package com.lhp.fastvlayout.model;

/**
 * Created By lhp
 * on 2019/3/19
 */
public class FloatModel extends BaseModel {

    private int alignType;//显示位置
    private int X;//X轴上的偏移量，例如在左上角时，x就是与左边的间距
    private int Y;//Y轴上的偏移量，例如在左上角时，y就是与顶部的间距
    private int width;
    private int height;

    public FloatModel(String type) {
        super(type);
    }

    public int getAlignType() {
        return alignType;
    }

    public void setAlignType(int alignType) {
        this.alignType = alignType;
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
