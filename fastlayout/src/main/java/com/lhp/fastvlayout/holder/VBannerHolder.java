package com.lhp.fastvlayout.holder;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.test.andlang.util.imageload.GlideUtil;
import com.lhp.fastvlayout.R;
import com.lhp.fastvlayout.banner.ShareCardItem;
import com.lhp.fastvlayout.banner.ShareCardView;
import com.lhp.fastvlayout.bean.Banner;
import com.lhp.fastvlayout.core.VBaseHolder;
import com.lhp.fastvlayout.listener.OnDisplayImageL;
import com.lhp.fastvlayout.model.BannerModel;
import com.lhp.fastvlayout.utils.BBCUtil;

import java.util.List;

/**
 * Created By lhp
 * on 2019/3/15
 */
public class VBannerHolder extends VBaseHolder<List<Banner>> {

    ShareCardView homeBanner;
    public VBannerHolder(View itemView) {
        super(itemView);
        homeBanner=itemView.findViewById(R.id.home_banner);
    }

    @Override
    public void setData(int ps, List<Banner> data) {
        super.setData(ps, data);
        initBanner();
    }

    private void initBanner(){
        //banner 设置
       final BannerModel bannerModel= (BannerModel) model;
        if (bannerModel!=null){
            int width = 0;
            int height=0;
            if (BBCUtil.isEmpty(bannerModel.getLeftAndRightPadding())){
                width=(BBCUtil.getDisplayWidth((Activity) mContext));
            }else{
                width=(int) (BBCUtil.getDisplayWidth((Activity) mContext) -2*BBCUtil.dp2px(mContext,Float.parseFloat(bannerModel.getLeftAndRightPadding())));
                homeBanner.setLeftRightPadding((int)BBCUtil.dp2px(mContext,Float.parseFloat(bannerModel.getLeftAndRightPadding())));
            }
            if (BBCUtil.isEmpty(bannerModel.getBottomPadding())){
                height = (int) (width* bannerModel.getAspect());
            }else{
                height = (int) ((width* bannerModel.getAspect())+2*BBCUtil.dp2px(mContext,Float.parseFloat(bannerModel.getBottomPadding())));
                homeBanner.setTopBottomPadding((int)BBCUtil.dp2px(mContext,Float.parseFloat(bannerModel.getBottomPadding())));
            }
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            homeBanner.setLayoutParams(params);
            if (!BBCUtil.isEmpty(bannerModel.getIndicatorLocation())){
                homeBanner.setIndicatorLocation(bannerModel.getIndicatorLocation());
            }
            homeBanner.setmIsAutoScrollEnable(bannerModel.isAutoScroll());
            if (bannerModel.getIndicatorLeftMargin()>0){
                homeBanner.setIndicatorLeftMargin(bannerModel.getIndicatorLeftMargin());
            }

            if (bannerModel.getmFocusImageId()!=0){
                homeBanner.setmFocusImageId(bannerModel.getmFocusImageId());
            }
            if (bannerModel.getmUnfocusImageId()!=0){
                homeBanner.setmUnfocusImageId(bannerModel.getmUnfocusImageId());
            }
        }
        ShareCardItem shareCardItem = new ShareCardItem();
        shareCardItem.setDataList(model.getData().get(0));
        homeBanner.setVisibility(View.VISIBLE);
        homeBanner.setCardData(shareCardItem);
        homeBanner.setmOnDisplayImageL(new OnDisplayImageL() {
            @Override
            public void onDisplayImage(Object object, final int position, ImageView imageView) {
                final Banner bean= (Banner) object;
                GlideUtil.getInstance().displayRoundImg(mContext, bean.getImgUrl(), imageView,bannerModel.getRadis());//显示圆角
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mListener!=null){
                            mListener.onItemClick(v,position,bean);
                        }
                    }
                });

            }
        });
        homeBanner.setBannerScrollStateI(mScrollListener);
    }
}
