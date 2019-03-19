package com.lhp.fastvlayout.model;


/**
 * Created By lhp
 * on 2019/3/15
 */
public class TextTitleModel extends BaseModel {
    private float textSize;
    private int[] paddings=new int[0];
    private int textColor;

    public TextTitleModel(String type) {
        super(type);
    }

    public float getTextSize() {
        return textSize;
    }

    public void setTextSize(float textSize) {
        this.textSize = textSize;
    }

    public int[] getPaddings() {
        return paddings;
    }

    public void setPaddings(int[] paddings) {
        this.paddings = paddings;
    }

    public int getTextColor() {
        return textColor;
    }

    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }
}
