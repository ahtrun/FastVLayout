package com.lhp.fastvlayout.core;

import android.content.Context;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.FloatLayoutHelper;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.OnePlusNLayoutHelper;
import com.alibaba.android.vlayout.layout.ScrollFixLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;
import com.alibaba.android.vlayout.layout.StickyLayoutHelper;
import com.lhp.fastvlayout.R;
import com.lhp.fastvlayout.bean.BaseParam;
import com.lhp.fastvlayout.holder.VBannerHolder;
import com.lhp.fastvlayout.holder.VFloatHolder;
import com.lhp.fastvlayout.holder.VNavigationHolder;
import com.lhp.fastvlayout.holder.VOnePlusNHolder;
import com.lhp.fastvlayout.holder.VBackTopHolder;
import com.lhp.fastvlayout.holder.VStickyHolder;
import com.lhp.fastvlayout.holder.VTextTitleHolder;
import com.lhp.fastvlayout.model.BaseModel;
import com.lhp.fastvlayout.model.FloatModel;
import com.lhp.fastvlayout.model.NavigationModel;
import com.lhp.fastvlayout.model.OnePlusNModel;
import com.lhp.fastvlayout.model.ScrollFixModel;
import com.lhp.fastvlayout.model.StickyModel;
import com.lhp.fastvlayout.model.TextTitleModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * Created By lhp
 * on 2019/3/14
 */
public class VLayoutBuilder {
    public static final String BANNER = "BANNER";
    public static final String NAVIGATION = "NAVIGATION";
    public static final String ONE_PLUS_N = "ONE_PLUS_N";
    public static final String BACK_TOP = "BACK_TOP";
    public static final String TEXT_TITLE = "TEXT_TITLE";
    public static final String FLOAT_VIEW = "FLOAT_VIEW";
    public static final String STICKY_VIEW = "STICKY_VIEW";

    private Context context;
    /**
     * 储存各个布局的holder
     */
    private HashMap<String, Class<? extends VBaseHolder>> holderMap;
    /**
     * 储存各个布局的model
     */
    private List<BaseModel> modelList;
    private RecyclerView recyclerView;
    private VirtualLayoutManager layoutManager;
    private DelegateAdapter adapters;
    private List<LayoutHelper> helpers;

    private VLayoutBuilder(Context context) {
        this.context = context;
        holderMap = new HashMap<>();
        modelList = new ArrayList<>();
        layoutManager = new VirtualLayoutManager(context);
        adapters = new DelegateAdapter(layoutManager);
        helpers = new LinkedList<>();
    }

    public void bindRecylerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    public static VLayoutBuilder newInnerBuilder(final Context context) {
        return new VLayoutBuilder(context);
    }

    /**
     * 根据type注册holder，内置类型可传基类VBaseHolder,自定义类型传自己的holder
     * @param type
     * @param holderClass
     */
    public void registerView(String type, Class<? extends VBaseHolder> holderClass) {
        holderMap.put(type, holderClass);
    }

    /**
     * 绑定models
     * @param modelList
     */
    public void setModels(List<BaseModel> modelList) {
        this.modelList = modelList;
    }

    /**
     * 初始化
     */
    public void init() {
        for (BaseModel model : modelList) {
            adapters.addAdapter(createAdapter(model));
        }
        recyclerView.setLayoutManager(layoutManager);
        layoutManager.setLayoutHelpers(helpers);
        recyclerView.setAdapter(adapters);
    }

    /**
     * 根据model创建对应的DelegateAdapter.Adapter添加到vlayout中
     * @param model
     * @return
     */
    private DelegateAdapter.Adapter createAdapter(BaseModel model) {
        DelegateAdapter.Adapter adapter = null;
        switch (model.getType()) {
            case BANNER:
                SingleLayoutHelper bannerHelper = new SingleLayoutHelper();
                bannerHelper.setItemCount(1);
                adapter = new VBaseAdapter(context).setData(model)
                        .setLayout(R.layout.include_banner)
                        .setLayoutHelper(new SingleLayoutHelper())
                        .setHolder(VBannerHolder.class)
                        .setListener(model.getItemListener())
                        .setScrollListener(model.getScrollListener());
                helpers.add(bannerHelper);
                break;
            case NAVIGATION:
                NavigationModel navigationModel = (NavigationModel) model;
                GridLayoutHelper guideHelper = new GridLayoutHelper(navigationModel.getSpanCount());
                //自动填充满布局，在设置完权重，若没有占满，自动填充满布局
                guideHelper.setAutoExpand(navigationModel.isAutoExpand());
                adapter = new VBaseAdapter(context).setData(navigationModel)
                        .setLayout(R.layout.gridview_guide_item)
                        .setLayoutHelper(guideHelper)
                        .setHolder(VNavigationHolder.class)
                        .setListener(model.getItemListener());
                helpers.add(guideHelper);
                break;
            case ONE_PLUS_N:
                OnePlusNModel onePlusNModel = (OnePlusNModel) model;
                OnePlusNLayoutHelper onePlusNLayoutHelper = new OnePlusNLayoutHelper(onePlusNModel.getItemCount());
                switch (onePlusNModel.getMargins().length) {
                    case 4:
                        onePlusNLayoutHelper.setMargin(onePlusNModel.getMargins()[0], onePlusNModel.getMargins()[1], onePlusNModel.getMargins()[2], onePlusNModel.getMargins()[3]);
                        break;
                    case 3:
                        onePlusNLayoutHelper.setMargin(onePlusNModel.getMargins()[0], onePlusNModel.getMargins()[1], onePlusNModel.getMargins()[2], 0);
                        break;
                    case 2:
                        onePlusNLayoutHelper.setMargin(onePlusNModel.getMargins()[0], onePlusNModel.getMargins()[1], 0, 0);
                        break;
                    case 1:
                        onePlusNLayoutHelper.setMargin(onePlusNModel.getMargins()[0], 0, 0, 0);
                        break;
                    default:
                        onePlusNLayoutHelper.setMargin(0, 0, 0, 0);
                        break;
                }
                onePlusNLayoutHelper.setAspectRatio(onePlusNModel.getAspect());
                if (onePlusNModel.getmColWeights() != null && onePlusNModel.getmColWeights().length > 0) {
                    onePlusNLayoutHelper.setColWeights(onePlusNModel.getmColWeights());
                }
                if (onePlusNModel.getmRowWeight() > 0) {
                    onePlusNLayoutHelper.setRowWeight(onePlusNModel.getmRowWeight());
                }
                adapter=new VBaseAdapter(context).setData(onePlusNModel)
                        .setLayout(R.layout.include_image)
                        .setLayoutHelper(onePlusNLayoutHelper)
                        .setHolder(VOnePlusNHolder.class)
                        .setListener(model.getItemListener());
                helpers.add(onePlusNLayoutHelper);
                break;
            case BACK_TOP:
                ScrollFixModel scrollFixModel= (ScrollFixModel) model;
                ScrollFixLayoutHelper scrollFixLayoutHelper=new ScrollFixLayoutHelper(scrollFixModel.getAlignType(),scrollFixModel.getX(),scrollFixModel.getY());
                scrollFixLayoutHelper.setShowType(scrollFixModel.getShowType());
                if (scrollFixModel.getData()==null){
                    List<BaseParam> list=new ArrayList<>();
                    list.add(new BaseParam());
                    scrollFixModel.setData(list);
                }
                adapter=new VBaseAdapter(context).setData(scrollFixModel)
                        .setLayoutHelper(scrollFixLayoutHelper)
                        .setLayout(scrollFixModel.getLayoutId()!=0?scrollFixModel.getLayoutId():R.layout.content_back_top)
                        .setHolder(VBackTopHolder.class)
                        .setListener(model.getItemListener());
                helpers.add(scrollFixLayoutHelper);
                break;
            case TEXT_TITLE:
                LinearLayoutHelper linearLayoutHelper=new LinearLayoutHelper();
                TextTitleModel textTitleModel= (TextTitleModel) model;
                adapter=new VBaseAdapter(context).setData(textTitleModel)
                        .setLayoutHelper(linearLayoutHelper)
                        .setLayout(R.layout.content_home_product_theme_title)
                        .setHolder(VTextTitleHolder.class);
                helpers.add(linearLayoutHelper);
                break;
            case FLOAT_VIEW:

                FloatLayoutHelper floatLayoutHelper=new FloatLayoutHelper();
                FloatModel floatModel= (FloatModel) model;
                if (floatModel.getData()==null){
                    List<BaseParam> list=new ArrayList<>();
                    list.add(new BaseParam());
                    floatModel.setData(list);
                }
                floatLayoutHelper.setAlignType(floatModel.getAlignType());
                floatLayoutHelper.setDefaultLocation(floatModel.getX(),floatModel.getY());
                adapter=new VBaseAdapter(context).setData(floatModel)
                        .setLayoutHelper(floatLayoutHelper)
                        .setLayout(model.getLayoutId())
                        .setHolder(VFloatHolder.class)
                        .setListener(floatModel.getItemListener());
                helpers.add(floatLayoutHelper);
                break;
            case STICKY_VIEW:
                StickyModel stickyModel= (StickyModel) model;
                StickyLayoutHelper stickyLayoutHelper=new StickyLayoutHelper();
                stickyLayoutHelper.setStickyStart(stickyModel.ismStickyStart());
                adapter=new VBaseAdapter(context).setData(stickyModel)
                        .setLayoutHelper(stickyLayoutHelper)
                        .setLayout(R.layout.content_sticky)
                        .setHolder(VStickyHolder.class)
                        .setListener(stickyModel.getItemListener());
                helpers.add(stickyLayoutHelper);
                break;
            default:
                adapter = new VBaseAdapter(context).setData(model)//
                        .setLayout(model.getLayoutId())//
                        .setLayoutHelper(model.getLayoutHelper())//
                        .setHolder(holderMap.get(model.getType()))
                        .setListener(model.getItemListener())
                        .setScrollListener(model.getScrollListener());
                helpers.add(model.getLayoutHelper());
                break;
        }
        return adapter;
    }


}
