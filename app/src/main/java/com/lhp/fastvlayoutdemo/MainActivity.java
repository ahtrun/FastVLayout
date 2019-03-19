package com.lhp.fastvlayoutdemo;

import android.graphics.Typeface;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.alibaba.android.vlayout.layout.FixLayoutHelper;
import com.alibaba.android.vlayout.layout.FloatLayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.ScrollFixLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.alibaba.android.vlayout.layout.StickyLayoutHelper;
import com.example.test.andlang.util.ToastUtil;
import com.lhp.fastvlayout.bean.Banner;
import com.lhp.fastvlayout.bean.BaseParam;
import com.lhp.fastvlayout.bean.LimitTime;
import com.lhp.fastvlayout.bean.Navigation;
import com.lhp.fastvlayout.core.VLayoutBuilder;
import com.lhp.fastvlayout.holder.VBackTopHolder;
import com.lhp.fastvlayout.holder.VBannerHolder;
import com.lhp.fastvlayout.holder.VFloatHolder;
import com.lhp.fastvlayout.holder.VNavigationHolder;
import com.lhp.fastvlayout.holder.VOnePlusNHolder;
import com.lhp.fastvlayout.holder.VStickyHolder;
import com.lhp.fastvlayout.holder.VTextTitleHolder;
import com.lhp.fastvlayout.listener.ItemDataBind;
import com.lhp.fastvlayout.listener.ItemListener;
import com.lhp.fastvlayout.model.BannerModel;
import com.lhp.fastvlayout.model.BaseModel;
import com.lhp.fastvlayout.model.FloatModel;
import com.lhp.fastvlayout.model.NavigationModel;
import com.lhp.fastvlayout.model.OnePlusNModel;
import com.lhp.fastvlayout.model.ScrollFixModel;
import com.lhp.fastvlayout.model.StickyModel;
import com.lhp.fastvlayout.model.TextTitleModel;
import com.lhp.fastvlayout.utils.BBCUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.lhp.fastvlayout.core.VLayoutBuilder.BACK_TOP;
import static com.lhp.fastvlayout.core.VLayoutBuilder.BANNER;
import static com.lhp.fastvlayout.core.VLayoutBuilder.FLOAT_VIEW;
import static com.lhp.fastvlayout.core.VLayoutBuilder.NAVIGATION;
import static com.lhp.fastvlayout.core.VLayoutBuilder.ONE_PLUS_N;
import static com.lhp.fastvlayout.core.VLayoutBuilder.STICKY_VIEW;
import static com.lhp.fastvlayout.core.VLayoutBuilder.TEXT_TITLE;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.rv_home)
    RecyclerView rvHome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        VLayoutBuilder builder=VLayoutBuilder.newInnerBuilder(this);
        builder.bindRecylerView(rvHome);
        builder.registerView("homeGuide", com.lhp.fastvlayoutdemo.VNavigationHolder.class);
        builder.setModels(getModels());
        builder.init();

    }

    private List<BaseModel> getModels() {
        List<BaseModel> models = new ArrayList<>();

        //float
        FloatModel floatModel=new FloatModel(FLOAT_VIEW);
        floatModel.setWidth(100);
        floatModel.setHeight(100);
        floatModel.setAlignType(FixLayoutHelper.BOTTOM_LEFT);
        floatModel.setX(100);
        floatModel.setY(100);
        floatModel.setLayoutId(R.layout.content_float);
        floatModel.setItemListener(new ItemListener() {
            @Override
            public void onItemClick(View view, int position, Object mData) {
                ToastUtil.show(MainActivity.this,"点击FloatView");
            }
        });
        models.add(floatModel);

        //banner
        BannerModel bannerModel = new BannerModel(BANNER);
        bannerModel.setAspect(0.493f);
        bannerModel.setLeftAndRightPadding("15");
        bannerModel.setBottomPadding("10");
        bannerModel.setAutoScroll(true);
        bannerModel.setData(getBannerList());
        bannerModel.setRadis(16f);
        bannerModel.setLayoutHelper(new SingleLayoutHelper());
        bannerModel.setLayoutId(com.lhp.fastvlayout.R.layout.include_banner);
        bannerModel.setItemListener(new ItemListener() {
            @Override
            public void onItemClick(View view, int position, Object mData) {
                Banner bean= (Banner) mData;
                if (BBCUtil.isEmpty(bean.getAdrUrl())) {
                    //跳主题场参数
                    if (bean.getId() > 0) {
                        bean.setAdrUrl("app?gotype=4&id=" + bean.getId() + "&title=" + bean.getTitle());
                    } }
                //点击埋点banner
//                ToastUtil.show(MainActivity.this, "跳转到" + bean.getLink());
                Toast.makeText(MainActivity.this,"跳转到" + bean.getAdrUrl(),Toast.LENGTH_LONG).show();
            }
        });
        models.add(bannerModel);
        //navigation
        NavigationModel navigationModel = new NavigationModel(NAVIGATION);
        navigationModel.setSpanCount(5);
        navigationModel.setTextSize(12);
        navigationModel.setAutoExpand(false);
        navigationModel.setData(getNavitionList());
        models.add(navigationModel);


        OnePlusNModel onePlusNModel=new OnePlusNModel(ONE_PLUS_N);
        onePlusNModel.setItemCount(1);
        onePlusNModel.setAspect(2.9f);
        onePlusNModel.setData(getImage());
        onePlusNModel.setMargins(new int[]{0,10,0,10});
        models.add(onePlusNModel);

        //吸顶
        StickyModel stickyModel=new StickyModel(STICKY_VIEW);
        stickyModel.setLayoutId(R.layout.content_tab);
        stickyModel.setItemDataBind(new ItemDataBind() {
            @Override
            public void bindView(View itemView, Object data) {
                initView(itemView,(LimitTime) data);
            }
        });
        stickyModel.setItemListener(new ItemListener() {
            @Override
            public void onItemClick(View view, int currenP, Object mData) {
                TabLayout tb_limittime= (TabLayout) view;
                for (int position = 0; position < tb_limittime.getTabCount(); position++) {
                    TabLayout.Tab tab = tb_limittime.getTabAt(position);
                    setView(tab.getCustomView(),currenP==position);
                }
            }
        });
        stickyModel.setData(getLimitTimes());
        models.add(stickyModel);

        onePlusNModel=new OnePlusNModel(ONE_PLUS_N);
        onePlusNModel.setItemCount(2);
        onePlusNModel.setAspect(2.9f);
        onePlusNModel.setmColWeights(new float[]{20f});
        onePlusNModel.setMargins(new int[]{0,10,0,10});
        onePlusNModel.setData(getImage2());
        models.add(onePlusNModel);

        onePlusNModel=new OnePlusNModel(ONE_PLUS_N);
        onePlusNModel.setItemCount(3);
        onePlusNModel.setAspect(1.5f);
        onePlusNModel.setmColWeights(new float[]{20f});
        onePlusNModel.setMargins(new int[]{0,10,0,10});
        onePlusNModel.setmRowWeight(30f);
        onePlusNModel.setData(getImage3());
        models.add(onePlusNModel);


        ScrollFixModel scrollFixModel=new ScrollFixModel(BACK_TOP);
        scrollFixModel.setAlignType(ScrollFixLayoutHelper.BOTTOM_RIGHT);
        scrollFixModel.setShowType(ScrollFixLayoutHelper.SHOW_ON_ENTER);
        scrollFixModel.setX(100);
        scrollFixModel.setY(100);
        scrollFixModel.setItemListener(new ItemListener() {
            @Override
            public void onItemClick(View view, int position, Object mData) {
                rvHome.smoothScrollToPosition(0);
            }
        });
        models.add(scrollFixModel);

        onePlusNModel=new OnePlusNModel(ONE_PLUS_N);
        onePlusNModel.setItemCount(4);
        onePlusNModel.setAspect(1.5f);
        onePlusNModel.setmColWeights(new float[]{20f});
        onePlusNModel.setMargins(new int[]{0,10,0,10});
        onePlusNModel.setmRowWeight(30f);
        onePlusNModel.setData(getImage4());
        models.add(onePlusNModel);

        onePlusNModel=new OnePlusNModel(ONE_PLUS_N);
        onePlusNModel.setItemCount(5);
        onePlusNModel.setAspect(1.5f);
        onePlusNModel.setmColWeights(new float[]{20f});
        onePlusNModel.setMargins(new int[]{0,10,0,10});
        onePlusNModel.setmRowWeight(30f);
        onePlusNModel.setData(getImage5());
        models.add(onePlusNModel);

        navigationModel = new NavigationModel("homeGuide");
        navigationModel.setSpanCount(4);
        navigationModel.setTextSize(12);
        navigationModel.setAutoExpand(false);
        GridLayoutHelper guideHelper = new GridLayoutHelper(navigationModel.getSpanCount());
        //自动填充满布局，在设置完权重，若没有占满，自动填充满布局
        guideHelper.setAutoExpand(navigationModel.isAutoExpand());
        navigationModel.setLayoutHelper(guideHelper);
        navigationModel.setLayoutId(com.lhp.fastvlayout.R.layout.gridview_guide_item);
        navigationModel.setData(getNavitionList());
        models.add(navigationModel);

        TextTitleModel textTitleModel=new TextTitleModel(TEXT_TITLE);
        textTitleModel.setData(getText());
        models.add(textTitleModel);


        return models;
    }

    private void setView(View itemView,boolean selected){
        TextView tv_limite_time = itemView.findViewById(R.id.tv_limite_time);
        TextView tv_limite_tip = itemView.findViewById(R.id.tv_limite_tip);
        View view_indicator=itemView.findViewById(R.id.view_indicator);
        LinearLayout ll_saletime_time=itemView.findViewById(R.id.ll_saletime_time);
        if (selected) {
            tv_limite_time.setTextColor(getResources().getColor(R.color.colorWhite));
            tv_limite_tip.setTextColor(getResources().getColor(R.color.colorWhite));
            tv_limite_time.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            tv_limite_tip.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            view_indicator.setVisibility(View.INVISIBLE);
            ll_saletime_time.setBackgroundResource(R.drawable.bg_rect_red_9dp);
        } else {
            tv_limite_time.setTextColor(getResources().getColor(R.color.colorBlackText));
            tv_limite_tip.setTextColor(getResources().getColor(R.color.colorBlackText2));
            tv_limite_time.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            tv_limite_tip.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
            view_indicator.setVisibility(View.INVISIBLE);
            ll_saletime_time.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        }
    }

    private void initView(View itemView, LimitTime limitTimeBean){
        TextView tv_limite_time = itemView.findViewById(R.id.tv_limite_time);
        TextView tv_limite_tip = itemView.findViewById(R.id.tv_limite_tip);
        tv_limite_time.setText(limitTimeBean.getShowTime());
        if(limitTimeBean.getActiveState()==-1){
            tv_limite_tip.setText("已结束");
        }else if(limitTimeBean.getActiveState()==1){
            tv_limite_tip.setText("秒杀中");
        }else if(limitTimeBean.getActiveState()==2){
            tv_limite_tip.setText("预热中");
        }
        setView(itemView,limitTimeBean.getFlag()==1);
    }

    private  List<List<LimitTime>> getLimitTimes(){
        List<List<LimitTime>> list=new ArrayList<>();
        List<LimitTime> timeList=new ArrayList<>();

        LimitTime limitTime=new LimitTime();
        limitTime.setActiveState(1);
        limitTime.setShowTime("10:00");
        limitTime.setFlag(2);
        timeList.add(limitTime);

        limitTime=new LimitTime();
        limitTime.setActiveState(1);
        limitTime.setFlag(1);
        limitTime.setShowTime("13:00");
        timeList.add(limitTime);

        limitTime=new LimitTime();
        limitTime.setActiveState(2);
        limitTime.setFlag(2);
        limitTime.setShowTime("22:00");
        timeList.add(limitTime);

        limitTime=new LimitTime();
        limitTime.setActiveState(2);
        limitTime.setFlag(2);
        limitTime.setShowTime("23:00");
        timeList.add(limitTime);
        
        list.add(timeList);
        return list;
    }

    private List<BaseParam> getText(){
        List<BaseParam> list = new ArrayList<>();
        BaseParam param=new BaseParam();
        param.setTitle("商品主题场");
        list.add(param);
        return list;
    }

    private List<BaseParam> getImage(){
        List<BaseParam> images = new ArrayList<>();
        BaseParam param=new BaseParam();
        param.setImgUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1553061187&di=11839b032df9e848037b6b3b75127a48&imgtype=jpg&er=1&src=http%3A%2F%2Fbpic.588ku.com%2Fback_pic%2F03%2F51%2F96%2F1557930fceee5aa.jpg");
        param.setLinkUrl("http://101.68.69.242:8000/#/share/vip-recruit?gotype=100");
        images.add(param);
        return images;
    }

    private List<BaseParam> getImage2(){
        List<BaseParam> images = new ArrayList<>();
        BaseParam param=new BaseParam();
        param.setImgUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1552470443412&di=46085862188a94cf2e7652d73eed4878&imgtype=0&src=http%3A%2F%2Fimg1.gtimg.com%2Fzj%2Fpics%2Fhv1%2F53%2F96%2F2130%2F138527783.jpg");
        param.setLinkUrl("http://101.68.69.242:8000/#/share/vip-recruit?gotype=100");
        images.add(param);

        param=new BaseParam();
        param.setImgUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1552471599755&di=031c0f650f8385cdd010db09b8398d5e&imgtype=0&src=http%3A%2F%2Fo4.xiaohongshu.com%2Fdiscovery%2Fw640%2F2ded2bd9e6a00a90ea6829fce17662f7_640_640_92.jpg");
        param.setLinkUrl("http://101.68.69.242:8000/#/share/vip-recruit?gotype=100");
        images.add(param);


        return images;
    }

    private List<BaseParam> getImage3(){
        List<BaseParam> images = new ArrayList<>();
        BaseParam param=new BaseParam();
        param.setImgUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1552470443412&di=46085862188a94cf2e7652d73eed4878&imgtype=0&src=http%3A%2F%2Fimg1.gtimg.com%2Fzj%2Fpics%2Fhv1%2F53%2F96%2F2130%2F138527783.jpg");
        param.setLinkUrl("http://101.68.69.242:8000/#/share/vip-recruit?gotype=100");
        images.add(param);

        param=new BaseParam();
        param.setImgUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1552471599755&di=031c0f650f8385cdd010db09b8398d5e&imgtype=0&src=http%3A%2F%2Fo4.xiaohongshu.com%2Fdiscovery%2Fw640%2F2ded2bd9e6a00a90ea6829fce17662f7_640_640_92.jpg");
        param.setLinkUrl("http://101.68.69.242:8000/#/share/vip-recruit?gotype=100");
        images.add(param);

        param=new BaseParam();
        param.setImgUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1552470443412&di=46085862188a94cf2e7652d73eed4878&imgtype=0&src=http%3A%2F%2Fimg1.gtimg.com%2Fzj%2Fpics%2Fhv1%2F53%2F96%2F2130%2F138527783.jpg");
        param.setLinkUrl("http://101.68.69.242:8000/#/share/vip-recruit?gotype=100");
        images.add(param);

        return images;
    }

    private List<BaseParam> getImage4(){
        List<BaseParam> images = new ArrayList<>();
        BaseParam param=new BaseParam();
        param.setImgUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1552470443412&di=46085862188a94cf2e7652d73eed4878&imgtype=0&src=http%3A%2F%2Fimg1.gtimg.com%2Fzj%2Fpics%2Fhv1%2F53%2F96%2F2130%2F138527783.jpg");
        param.setLinkUrl("http://101.68.69.242:8000/#/share/vip-recruit?gotype=100");
        images.add(param);

        param=new BaseParam();
        param.setImgUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1552471599755&di=031c0f650f8385cdd010db09b8398d5e&imgtype=0&src=http%3A%2F%2Fo4.xiaohongshu.com%2Fdiscovery%2Fw640%2F2ded2bd9e6a00a90ea6829fce17662f7_640_640_92.jpg");
        param.setLinkUrl("http://101.68.69.242:8000/#/share/vip-recruit?gotype=100");
        images.add(param);

        param=new BaseParam();
        param.setImgUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1552470443412&di=46085862188a94cf2e7652d73eed4878&imgtype=0&src=http%3A%2F%2Fimg1.gtimg.com%2Fzj%2Fpics%2Fhv1%2F53%2F96%2F2130%2F138527783.jpg");
        param.setLinkUrl("http://101.68.69.242:8000/#/share/vip-recruit?gotype=100");
        images.add(param);

        param=new BaseParam();
        param.setImgUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1552471599755&di=031c0f650f8385cdd010db09b8398d5e&imgtype=0&src=http%3A%2F%2Fo4.xiaohongshu.com%2Fdiscovery%2Fw640%2F2ded2bd9e6a00a90ea6829fce17662f7_640_640_92.jpg");
        param.setLinkUrl("http://101.68.69.242:8000/#/share/vip-recruit?gotype=100");
        images.add(param);


        return images;
    }


    private List<BaseParam> getImage5(){
        List<BaseParam> images = new ArrayList<>();
        BaseParam param=new BaseParam();
        param.setImgUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1552470443412&di=46085862188a94cf2e7652d73eed4878&imgtype=0&src=http%3A%2F%2Fimg1.gtimg.com%2Fzj%2Fpics%2Fhv1%2F53%2F96%2F2130%2F138527783.jpg");
        param.setLinkUrl("http://101.68.69.242:8000/#/share/vip-recruit?gotype=100");
        images.add(param);

        param=new BaseParam();
        param.setImgUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1552471599755&di=031c0f650f8385cdd010db09b8398d5e&imgtype=0&src=http%3A%2F%2Fo4.xiaohongshu.com%2Fdiscovery%2Fw640%2F2ded2bd9e6a00a90ea6829fce17662f7_640_640_92.jpg");
        param.setLinkUrl("http://101.68.69.242:8000/#/share/vip-recruit?gotype=100");
        images.add(param);

        param=new BaseParam();
        param.setImgUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1552470443412&di=46085862188a94cf2e7652d73eed4878&imgtype=0&src=http%3A%2F%2Fimg1.gtimg.com%2Fzj%2Fpics%2Fhv1%2F53%2F96%2F2130%2F138527783.jpg");
        param.setLinkUrl("http://101.68.69.242:8000/#/share/vip-recruit?gotype=100");
        images.add(param);

        param=new BaseParam();
        param.setImgUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1552471599755&di=031c0f650f8385cdd010db09b8398d5e&imgtype=0&src=http%3A%2F%2Fo4.xiaohongshu.com%2Fdiscovery%2Fw640%2F2ded2bd9e6a00a90ea6829fce17662f7_640_640_92.jpg");
        param.setLinkUrl("http://101.68.69.242:8000/#/share/vip-recruit?gotype=100");
        images.add(param);

        param=new BaseParam();
        param.setImgUrl("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1552470443412&di=46085862188a94cf2e7652d73eed4878&imgtype=0&src=http%3A%2F%2Fimg1.gtimg.com%2Fzj%2Fpics%2Fhv1%2F53%2F96%2F2130%2F138527783.jpg");
        param.setLinkUrl("http://101.68.69.242:8000/#/share/vip-recruit?gotype=100");
        images.add(param);
        return images;
    }

    private List<Navigation> getNavitionList() {
        List<Navigation> navigationList = new ArrayList<>();
        Navigation navigation = new Navigation();
        navigation.setAdrUrl("gotype\u003d26\u0026categoryId\u003d256595020004685\u0026id\u003d256627140000048\u0026title\u003d美妆护肤");
        navigation.setImgUrl("https://image.sudian178.com/sd/linkDataImg/348530547516477.png");
        navigation.setName("美妆护肤");
        navigation.setId(258654990000052l);
        navigationList.add(navigation);

        navigation = new Navigation();
        navigation.setAdrUrl("gotype\u003d26\u0026categoryId\u003d256595020004656\u0026id\u003d256627140000278\u0026title\u003d个人护理");
        navigation.setImgUrl("https://image.sudian178.com/sd/linkDataImg/348575006103647.png");
        navigation.setId(258654990000068l);
        navigation.setName("个人护理");
        navigationList.add(navigation);

        navigation = new Navigation();
        navigation.setAdrUrl("gotype\u003d26\u0026categoryId\u003d256595020004722\u0026id\u003d256627140001531\u0026title\u003d营养保健");
        navigation.setImgUrl("https://image.sudian178.com/sd/linkDataImg/348611549347679.png");
        navigation.setName("营养保健");
        navigation.setId(258654990000075l);
        navigationList.add(navigation);

        navigation = new Navigation();
        navigation.setAdrUrl("gotype\u003d4\u0026categoryId\u003d256595020004717\u0026id\u003d256627140000693\u0026title\u003d环球美食");
        navigation.setImgUrl("https://image.sudian178.com/sd/linkDataImg/348707873100878.png");
        navigation.setName("环球美食");
        navigationList.add(navigation);

        navigation = new Navigation();
        navigation.setAdrUrl("app?gotype\u003d1\u0026id\u003d256944790563392");
        navigation.setImgUrl("https://image.sudian178.com/sd/linkDataImg/348746552237361.png");
        navigation.setId(258654990000097l);
        navigation.setName("数码家电");
        navigationList.add(navigation);

        navigation = new Navigation();
        navigation.setAdrUrl("app?gotype\u003d20");
        navigation.setImgUrl("https://image.sudian178.com/sd/linkDataImg/348853161528808.png");
        navigation.setName("环球馆");
        navigation.setId(258654990000107l);
        navigationList.add(navigation);

        navigation = new Navigation();
        navigation.setAdrUrl("http://192.168.11.231/#/universal-detail?id\u003d256627140001114\u0026gotype\u003d4\u0026title\u003d居家百货");
        navigation.setImgUrl("https://image.sudian178.com/sd/linkDataImg/348887060322244.png");
        navigation.setName("居家百货");
        navigation.setId(258654990000112l);
        navigationList.add(navigation);

        navigation = new Navigation();
        navigation.setAdrUrl("gotype\u003d4\u0026id\u003d256627140001322\u0026title\u003d国际轻奢");
        navigation.setImgUrl("https://image.sudian178.com/sd/linkDataImg/348936634335078.png");
        navigation.setName("国际轻奢");
        navigation.setId(258654990000135l);
        navigationList.add(navigation);

        navigation = new Navigation();
        navigation.setAdrUrl("gotype\u003d26\u0026categoryId\u003d256595020004693\u0026id\u003d256627140000907\u0026title\u003d母婴儿童");
        navigation.setImgUrl("https://image.sudian178.com/sd/linkDataImg/348974560317894.png");
        navigation.setName("母婴儿童");
        navigation.setId(258654990000141l);
        navigationList.add(navigation);

        navigation = new Navigation();
        navigation.setAdrUrl("app?gotype\u003d21");
        navigation.setImgUrl("https://image.sudian178.com/sd/linkDataImg/349265000969793.png");
        navigation.setName("分类");
        navigation.setId(258654990000156l);
        navigationList.add(navigation);
        return navigationList;
    }

    private List<List<Banner>> getBannerList() {
        List<List<Banner>> list=new ArrayList<>();
        List<Banner> bannerList = new ArrayList<>();
        Banner banner = new Banner();
        banner.setImgUrl("https://image.sudian178.com/sd/themeImg/402550512076430.jpg");
        banner.setBgColor("#6FA6E8");
        banner.setId(258654990000168l);
        banner.setTitle("私人定制");
        bannerList.add(banner);

        banner = new Banner();
        banner.setImgUrl("https://image.sudian178.com/sd/themeImg/402578292457079.jpg");
        banner.setBgColor("#549AC0");
        banner.setTitle("Akin五件套");
        banner.setId(258654990001857l);
        banner.setAdrUrl("http://123.157.216.154:8000/#/share/goddess?gotype\u003d100\u0026title\u003d女神节活动");
        bannerList.add(banner);

        banner = new Banner();
        banner.setImgUrl("https://image.sudian178.com/sd/themeImg/402605214377507.jpg");
        banner.setBgColor("#EE6370");
        banner.setTitle("Milk\u0026Co婴儿呵护套装");
        banner.setId(258654990001868l);
        bannerList.add(banner);

        banner = new Banner();
        banner.setImgUrl("https://image.sudian178.com/sd/themeImg/402634321341828.jpg");
        banner.setBgColor("#F02C3E");
        banner.setTitle("百年润喉龙角散");
        banner.setId(258654990001938l);
        bannerList.add(banner);

        banner = new Banner();
        banner.setImgUrl("https://image.sudian178.com/sd/themeImg/350596957065792.jpg");
        banner.setBgColor("#4B0082");
        banner.setTitle("情人节立减20元");
        banner.setAdrUrl("app?gotype\u003d28\u0026id\u003d258758300000056");
        banner.setId(258654990001942l);
        bannerList.add(banner);

        list.add(bannerList);
        return list;
    }
}
