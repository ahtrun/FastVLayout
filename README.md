# FastVLayout
基于vlayout和万能适配器快速开发复杂布局

# 目的
封装通用组件，快速进行复杂布局的开发，可配置化的修改样式以达到更方便的维护

# 原理
基于vlayout和万能适配器实现

# 已整合开发包
自用AndLang框架

# 模块及任务
VLayoutBuilder ：核心功能类，进行了绑定recylerview，注册组件，设置models，创建页面<br>
BaseModel ：组件样式的基类，每个组件有对应的model负责各自的模块，后面会详细说明有哪些models，用户也可以自定义models进行自定义页面开发
VBaseAdapter ：万能适配器adapter，继承自DelegateAdapter.Adapter，负责对vlayout中每种布局进行创建
VBaseHolder ：万能适配器holder，与VBaseAdapter配套使用，实际应用中需要继承该类创建自定义的holder也可以使用自带的一些组件的holder
ItemListener ：用于处理item的点击事件，需要的时候进行绑定
ScrollListener ：用于处理模块中的滑动事件，需要的时候进行绑定

# 快速开始
**VLayoutBuilder builder=VLayoutBuilder.newInnerBuilder(this);** <br>
        ## 绑定RecylerView <br>
        **builder.bindRecylerView(rvHome);**<br>
        ##注册了一个自定义的navigation组件<br>
        **builder.registerView("homeGuide", com.lhp.hotfixdemo.VNavigationHolder.class);**<br>
        ##绑定model数据<br>
        **builder.setModels(getModels());**<br>
        ##开始启动##<br>
        **builder.init();**<br>
       
# Model说明
**BaseModel**<br>
private String type;//模型类型 banner,navigation,onePlusN,onePlusNEx,scrollFix,后续还有<br>
private float aspect;//宽高比<br>
private String container;//包含内容（暂时没用）<br>
private List<T> data;//数据 <br>
private LayoutHelper layoutHelper;//布局类型<br>
private int layoutId;//布局文件<br>
private ItemListener<T> itemListener;//点击事件监听<br>
private ScrollListener scrollListener;//滑动事件监听<br>
private int[] margins=new int[0];//margins<br>
<br> 
**BannerModel**<br>
private boolean autoScroll;//自动滚动 <br>
private String leftAndRightPadding;//左右padding <br>
private String bottomPadding;//底部到指示器之间的高度<br>
private String indicatorLocation;//指示器位置<br>
private int indicatorLeftMargin;//指示器间隔<br>
private int mFocusImageId;//选中的指示器drawable<br>
private int mUnfocusImageId;//未选中的指示器drawable<br>
private float radis;//图片的角度数<br>
<br>
**NavigationModel** <br>
private int spanCount;//一行的item数量<br>
private boolean isOnlyImage;//是的只显示图片<br>
private float textSize;//文字大小<br>
private int textColor;//文字颜色<br>
private boolean AutoExpand;//自动填充<br>
<br>
**OnePlusNModel** <br>
private int itemCount;//item数量<br>
private float[] mColWeights;//横向item的宽度占比，最大值100<br>
private float mRowWeight;//纵向item的宽度占比，最大值100<br>
        <br>
**ScrollFixModel**<br>
//ScrollFixLayoutHelper.SHOW_ALWAYS 一直显示<br>
//ScrollFixLayoutHelper.SHOW_ON_ENTER view 进入页面时显示<br>
//ScrollFixLayoutHelper.SHOW_ON_LEAVE view 离开页面时显示<br>
private int showType;//显示方式<br> 
//FixLayoutHelper.TOP_LEFT FixLayoutHelper.TOP_RIGHT FixLayoutHelper.BOTTOM_LEFT FixLayoutHelper.BOTTOM_RIGHT <br>
private int alignType;//显示位置<br>
private int X;//X轴上的偏移量，例如在左上角时，x就是与左边的间距<br>
private int Y;//Y轴上的偏移量，例如在左上角时，y就是与顶部的间距<br>
private int width;//控件宽度，单位px<br>
private int height;//控件高度，单位px<br>
<br>
**TextTitleModel**<br>
private float textSize;//字体大小<br>
private int[] paddings=new int[0];//padding<br>
private int textColor;//字体颜色<br>
<br>
 **FloatModel**<br>
 //FixLayoutHelper.TOP_LEFT FixLayoutHelper.TOP_RIGHT FixLayoutHelper.BOTTOM_LEFT FixLayoutHelper.BOTTOM_RIGHT <br>
 private int alignType;//显示位置<br>
 private int X;//X轴上的偏移量，例如在左上角时，x就是与左边的间距<br>
 private int Y;//Y轴上的偏移量，例如在左上角时，y就是与顶部的间距<br>
 private int width;//控件宽度，单位px<br>
 private int height;//控件高度，单位px<br>   
<br>        
 **StickyModel**<br>
private boolean mStickyStart=true;//false=吸低，true=吸顶 默认吸顶<br>
private int mOffset = 0;//距离顶部或者底部的偏移量<br>
private ItemDataBind itemDataBind;//item中的数据绑定接口<br>       

