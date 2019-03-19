package com.lhp.fastvlayout.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.test.andlang.util.imageload.GlideUtil;
import com.lhp.fastvlayout.R;
import com.lhp.fastvlayout.bean.Banner;
import com.lhp.fastvlayout.bean.BaseParam;
import com.lhp.fastvlayout.core.VBaseHolder;

import java.util.List;

import butterknife.BindView;

/**
 * Created By lhp
 * on 2019/3/15
 */
public class VOnePlusNHolder extends VBaseHolder<BaseParam> {

//    @BindView(R.id.iv_image)
    ImageView ivImage;
    public VOnePlusNHolder(View itemView) {
        super(itemView);
        ivImage=itemView.findViewById(R.id.iv_image);
    }

    @Override
    public void setData(final int ps, final BaseParam mData) {
        super.setData(ps, mData);
        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams)ivImage.getLayoutParams();
        params.height=LinearLayout.LayoutParams.MATCH_PARENT;
        params.width=LinearLayout.LayoutParams.MATCH_PARENT;
        GlideUtil.getInstance().display(mContext,mData.getImgUrl(),ivImage);

        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener!=null){
                    mListener.onItemClick(v,ps,mData);
                }
            }
        });


    }
}
