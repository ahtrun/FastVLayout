package com.lhp.fastvlayout.model;

public class OnePlusNModel extends BaseModel {
    private int itemCount;
    private float[] mColWeights;
    private float mRowWeight;
    private int[] margins=new int[0];

    public OnePlusNModel(String type) {
        super(type);
    }

    public int[] getMargins() {
        return margins;
    }

    public void setMargins(int[] margins) {
        this.margins = margins;
    }

    public int getItemCount() {
        return itemCount;
    }

    public void setItemCount(int itemCount) {
        this.itemCount = itemCount;
    }

    public float[] getmColWeights() {
        return mColWeights;
    }

    public void setmColWeights(float[] mColWeights) {
        this.mColWeights = mColWeights;
    }

    public float getmRowWeight() {
        return mRowWeight;
    }

    public void setmRowWeight(float mRowWeight) {
        this.mRowWeight = mRowWeight;
    }
}
