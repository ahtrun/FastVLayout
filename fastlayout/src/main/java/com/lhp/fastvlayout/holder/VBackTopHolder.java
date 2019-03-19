package com.lhp.fastvlayout.holder;

import android.view.View;

import com.lhp.fastvlayout.core.VBaseHolder;
import com.lhp.fastvlayout.model.ScrollFixModel;

/**
 * Created By lhp
 * on 2019/3/15
 */
public class VBackTopHolder extends VBaseHolder {
    public VBackTopHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setData(int ps, final Object mData) {
        super.setData(ps, mData);
        ScrollFixModel scrollFixModel= (ScrollFixModel) model;
        if (scrollFixModel.getWidth()>0&&scrollFixModel.getHeight()>0){
            itemView.getLayoutParams().width=scrollFixModel.getWidth();
            itemView.getLayoutParams().height=scrollFixModel.getHeight();
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
