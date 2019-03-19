package com.lhp.fastvlayout.holder;

import android.view.View;
import android.widget.TextView;

import com.lhp.fastvlayout.R;
import com.lhp.fastvlayout.bean.BaseParam;
import com.lhp.fastvlayout.core.VBaseHolder;
import com.lhp.fastvlayout.model.TextTitleModel;
import com.lhp.fastvlayout.utils.BBCUtil;

/**
 * Created By lhp
 * on 2019/3/15
 */
public class VTextTitleHolder extends VBaseHolder {
    private TextView textView;
    public VTextTitleHolder(View itemView) {
        super(itemView);
       textView= itemView.findViewById(R.id.tv_title);
    }

    @Override
    public void setData(int ps, Object mData) {
        super.setData(ps, mData);
        TextTitleModel titleModel= (TextTitleModel) model;
        if (titleModel.getTextSize()>0){
            textView.setTextSize(titleModel.getTextSize());
        }
        if (titleModel.getTextColor()!=0){
            textView.setTextColor(titleModel.getTextColor());
        }
        BaseParam baseParam= (BaseParam) mData;
        if (!BBCUtil.isEmpty(baseParam.getTitle())){
            textView.setText(baseParam.getTitle());
        }
        switch (titleModel.getPaddings().length) {
            case 4:
                textView.setPadding(titleModel.getPaddings()[0], titleModel.getPaddings()[1], titleModel.getPaddings()[2], titleModel.getPaddings()[3]);
                break;
            case 3:
                textView.setPadding(titleModel.getPaddings()[0], titleModel.getPaddings()[1], titleModel.getPaddings()[2], 0);
                break;
            case 2:
                textView.setPadding(titleModel.getPaddings()[0], titleModel.getPaddings()[1], 0, 0);
                break;
            case 1:
                textView.setPadding(titleModel.getPaddings()[0], 0, 0, 0);
                break;
            default:
                break;
        }
    }
}
