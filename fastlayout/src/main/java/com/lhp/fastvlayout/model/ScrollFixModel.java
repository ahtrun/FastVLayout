package com.lhp.fastvlayout.model;

public class ScrollFixModel extends BaseModel {
//    /**
//     * SHOW_ALWAYS：与FixLayoutHelper的行为一致，固定在某个位置；
//     * SHOW_ON_ENTER：默认不显示视图，当页面滚动到这个视图的位置的时候，才显示；
//     * SHOW_ON_LEAVE：默认不显示视图，当页面滚出这个视图的位置的时候显示；
//     */
//    public static final int SHOW_ALWAYS = 0;
//    public static final int SHOW_ON_ENTER = 1;
//    public static final int SHOW_ON_LEAVE = 2;
//    /**
//     * TOP_LEFT：基准位置是左上角，x是视图左边相对父容器的左边距偏移量，y是视图顶边相对父容器的上边距偏移量；
//     * TOP_RIGHT：基准位置是右上角，x是视图右边相对父容器的右边距偏移量，y是视图顶边相对父容器的上边距偏移量；
//     * BOTTOM_LEFT：基准位置是左下角，x是视图左边相对父容器的左边距偏移量，y是视图底边相对父容器的下边距偏移量；
//     * BOTTOM_RIGHT：基准位置是右下角，x是视图右边相对父容器的右边距偏移量，y是视图底边相对父容器的下边距偏移量；
//     */
//    public static final int TOP_LEFT = 0;
//    public static final int TOP_RIGHT = 1;
//    public static final int BOTTOM_LEFT = 2;
//    public static final int BOTTOM_RIGHT = 3;

    private int showType;//显示方式
    private int alignType;//显示位置
    private int X;//X轴上的偏移量，例如在左上角时，x就是与左边的间距
    private int Y;//Y轴上的偏移量，例如在左上角时，y就是与顶部的间距
    private int width;
    private int height;

    public ScrollFixModel(String type) {
        super(type);
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

    public int getShowType() {
        return showType;
    }

    public void setShowType(int showType) {
        this.showType = showType;
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
}
