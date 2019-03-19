package com.lhp.fastvlayout.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.test.andlang.util.imageload.GlideUtil;
import com.lhp.fastvlayout.R;
import com.lhp.fastvlayout.bean.Navigation;
import com.lhp.fastvlayout.core.VBaseHolder;
import com.lhp.fastvlayout.model.NavigationModel;

import butterknife.BindView;

/**
 * Created By lhp
 * on 2019/3/15
 */
public class VNavigationHolder extends VBaseHolder<Navigation> {
//    @BindView(R.id.iv_home_btn)
    ImageView ivHomeBtn;
//    @BindView(R.id.tv_home_btn)
    TextView tvHomeBtn;
//    @BindView(R.id.ll_home_btn)
    RelativeLayout llHomeBtn;
//    @BindView(R.id.iv_dot)
    View ivDot;

    public VNavigationHolder(View itemView) {
        super(itemView);
        ivHomeBtn=itemView.findViewById(R.id.iv_home_btn);
        tvHomeBtn=itemView.findViewById(R.id.tv_home_btn);
        llHomeBtn=itemView.findViewById(R.id.ll_home_btn);
        ivDot=itemView.findViewById(R.id.iv_dot);
    }

    @Override
    public void setData(final int ps, final Navigation mData) {
        super.setData(ps, mData);
        NavigationModel navigationModel = (NavigationModel) model;
        if (navigationModel.isOnlyImage()) {
            ivDot.setVisibility(View.GONE);
            tvHomeBtn.setVisibility(View.GONE);
        } else {
            tvHomeBtn.setVisibility(View.VISIBLE);
        }
        if (navigationModel.getTextColor() != 0) {
            tvHomeBtn.setTextColor(navigationModel.getTextColor());
        } else {
            tvHomeBtn.setTextColor(mContext.getResources().getColor(R.color.colorBlackText2));
        }
        if (navigationModel.getTextSize() > 0) {
            tvHomeBtn.setTextSize(navigationModel.getTextSize());
        } else {
            tvHomeBtn.setTextSize(12);
        }
        tvHomeBtn.setText(mData.getName());
//                                ImageLoadUtils.doLoadImageUrl(imageView, navigation.getImgUrl());
        GlideUtil.getInstance().display(mContext, mData.getImgUrl(), ivHomeBtn);
        ivDot.setVisibility(View.GONE);
        llHomeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener!=null){
                    mListener.onItemClick(v,ps,mData);
                }
            }
        });

    }
}
