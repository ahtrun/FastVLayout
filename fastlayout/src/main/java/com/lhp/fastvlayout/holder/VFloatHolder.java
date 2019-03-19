package com.lhp.fastvlayout.holder;

import android.view.View;

import com.lhp.fastvlayout.core.VBaseHolder;
import com.lhp.fastvlayout.model.FloatModel;

/**
 * Created By lhp
 * on 2019/3/19
 */
public class VFloatHolder extends VBaseHolder {

    public VFloatHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(int ps, final Object mData) {
        super.setData(ps, mData);
        FloatModel floatModel= (FloatModel) model;
        if (floatModel.getWidth()>0&&floatModel.getHeight()>0){
            itemView.getLayoutParams().width=floatModel.getWidth();
            itemView.getLayoutParams().height=floatModel.getHeight();
        }
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener!=null){
                    mListener.onItemClick(v,position,mData);
                }
            }
        });
    }
}
