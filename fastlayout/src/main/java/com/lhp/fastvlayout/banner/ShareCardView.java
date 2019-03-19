package com.lhp.fastvlayout.banner;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lhp.fastvlayout.R;
import com.lhp.fastvlayout.listener.OnDisplayImageL;
import com.lhp.fastvlayout.listener.ScrollListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author SoBan
 * @create 2017/5/19 15:33.
 */
public class ShareCardView extends FrameLayout implements ViewPager.OnPageChangeListener {
    private static final int MSG_NEXT = 1;
    private Context mContext;
    private ViewPager mViewPager; //自定义的无限循环ViewPager
    private ViewGroup mViewGroup;
    private CardAdapter mAdapter;
    private int mFocusImageId;
    private int mUnfocusImageId;
    private Handler mHandler;
    private TimerTask mTimerTask;
    private Timer mTimer;
    private int centerPos; //中间卡片位置
    private int pageCount; //所有卡片的个数
    private int leftRightPadding;//item的左右padding
    private int topBottomPadding;//item的上下padding
    private int indicatorLeftMargin;//指示器间隔


    public void setIndicatorLeftMargin(int indicatorLeftMargin) {
        this.indicatorLeftMargin = indicatorLeftMargin;
    }

    /**
     * 是否自动滚动
     */
    private boolean mIsAutoScrollEnable = true;
    //指示器所在位置，left,right,center
    public void setIndicatorLocation(String indicatorLocation) {
        if (mViewGroup!=null){
            RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) mViewGroup.getLayoutParams();
            params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            if ("left".equals(indicatorLocation)){
                params.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
                params.setMargins((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25,mContext.getResources().getDisplayMetrics()),
                        0,0,(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15,mContext.getResources().getDisplayMetrics()));

            }else if ("center".equals(indicatorLocation)){
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                params.setMargins(0,0,0,(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15,mContext.getResources().getDisplayMetrics()));
            }else{
                params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                params.setMargins(0,0,(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25,mContext.getResources().getDisplayMetrics()),
                        (int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 15,mContext.getResources().getDisplayMetrics()));
            }
        }
    }
    //传入drawableId
    public void setmFocusImageId(int mFocusImageId) {
        this.mFocusImageId = mFocusImageId;
    }
    //传入drawableId
    public void setmUnfocusImageId(int mUnfocusImageId) {
        this.mUnfocusImageId = mUnfocusImageId;
    }

    public void setLeftRightPadding(int leftRightPadding) {
        this.leftRightPadding = leftRightPadding;
    }

    public void setTopBottomPadding(int topBottomPadding) {
        this.topBottomPadding = topBottomPadding;
    }


    public void setmIsAutoScrollEnable(boolean mIsAutoScrollEnable) {
        this.mIsAutoScrollEnable = mIsAutoScrollEnable;
    }

    public ShareCardView(Context context) {
        this(context, null);
    }

    public ShareCardView(Context context, AttributeSet set) {
        super(context, set);
        init(context);
    }


    private void init(Context context) {
        mContext = context;
        View container = LayoutInflater.from(getContext()).inflate(R.layout.layout_share_cardview, null);
        addView(container);
        mViewPager = (ViewPager) (container.findViewById(R.id.slide_viewPager));
        mViewGroup = (ViewGroup) (container.findViewById(R.id.slide_viewGroup));
        mFocusImageId = R.drawable.focus_banner;
        mUnfocusImageId = R.drawable.unfocus_banner;
        mViewPager.setPageMargin(0);//卡片与卡片间的距离
        mViewPager.setOnPageChangeListener(this);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case MSG_NEXT:
                        if (mIsAutoScrollEnable){
                            next();
                        }else {
                            stopTimer();
                        }
                        break;
                }
                super.handleMessage(msg);
            }
        };

        mViewPager.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                    case MotionEvent.ACTION_MOVE:
                        stopTimer();
                        break;
                    case MotionEvent.ACTION_UP:
                        if (mIsAutoScrollEnable){
                            startTimer();
                        }
                        break;
                }
                return false;
            }
        });
    }

    //设置数据
    public void setCardData(ShareCardItem cardItem) {
        pageCount = cardItem.getDataList().size();
        centerPos = pageCount / 2;//中间卡片的位置
        mAdapter = new CardAdapter(cardItem);
        mViewPager.setAdapter(mAdapter);
        mViewPager.setOffscreenPageLimit(pageCount);//预加载所有卡片
        mAdapter.select(0);//默认从中间卡片开始
        mViewPager.setCurrentItem(0);
        if (mIsAutoScrollEnable){
            startTimer();
        }
    }

    //启动动画
    public void startTimer() {
        stopTimer();
        mTimerTask = new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(MSG_NEXT);
            }
        };
        mTimer = new Timer(true);
        mTimer.schedule(mTimerTask, 5000, 5000);
    }

    //停止动画
    public void stopTimer() {
        mHandler.removeMessages(MSG_NEXT);
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }

        if (mTimerTask != null) {
            mTimerTask.cancel();
            mTimerTask = null;
        }
    }

    //选择下一个卡片
    public void next() {
        int pos = mViewPager.getCurrentItem();
        pos += 1;
        mViewPager.setCurrentItem(pos);
    }

    //判断是否显隐控件
    public void refresh() {
        if (getCount() <= 0) {
            this.setVisibility(View.GONE);
        } else {
            this.setVisibility(View.VISIBLE);
        }
    }

    public int getCount() {
        if (mAdapter != null) {
            return mAdapter.getCount();
        }
        return 0;
    }

    public class CardAdapter extends PagerAdapter {
        private ArrayList<ImageView> mPoints;
        private List lrCardItems = new ArrayList<>();

        public CardAdapter(ShareCardItem cardItem) {
            mPoints = new ArrayList<>();
            lrCardItems = cardItem.getDataList();
            setItems();
        }

        @Override
        public int getCount() {
            return pageCount;
        }

        private ImageView newPoint() {
            ImageView imageView = new ImageView(getContext());
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            if (indicatorLeftMargin!=0){
                params.leftMargin = indicatorLeftMargin;
            }else{
                params.leftMargin = 20;
            }
            imageView.setLayoutParams(params);
            imageView.setBackgroundResource(mUnfocusImageId);
            return imageView;
        }

        //其他卡片
        public void setLRCard(final View itemView, int lrCardItemPos) {
            final Object item = lrCardItems.size() > lrCardItemPos ?
                    lrCardItems.get(lrCardItemPos) : new Object();
            ImageView imageView = itemView.findViewById(R.id.iv_banner_item);
            if (mOnDisplayImageL !=null){
                mOnDisplayImageL.onDisplayImage(item,lrCardItemPos,imageView);
            }
        }

        public void setItems() {
            while (mPoints.size() < pageCount) mPoints.add(newPoint());
            while (mPoints.size() > pageCount) mPoints.remove(0);
            mViewGroup.removeAllViews();
            for (ImageView view : mPoints) {
                mViewGroup.addView(view);
            }

        }

        public void select(int pos) {
            if (mPoints.size() > 0) {
                pos = pos % mPoints.size();
                for (int i = 0; i < mPoints.size(); i++) {
                    if (i == pos) {
                        mPoints.get(i).setBackgroundResource(mFocusImageId);
                    } else {
                        mPoints.get(i).setBackgroundResource(mUnfocusImageId);
                    }
                }
            }
            refresh();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.layout_banner_imgitem, null);
            ImageView imageView=itemView.findViewById(R.id.iv_banner_item);
            RelativeLayout.LayoutParams params= (RelativeLayout.LayoutParams) imageView.getLayoutParams();
            params.setMargins(leftRightPadding,topBottomPadding,leftRightPadding,topBottomPadding);
            setLRCard(itemView, position);
            container.addView(itemView);
            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        mAdapter.select(position);
        if(bannerScrollStateI!=null){
            bannerScrollStateI.changeView(position);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if(bannerScrollStateI!=null){
            bannerScrollStateI.onPageScrollStateChanged(state);
        }
    }

    private int downX;
    private int downY;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                // 父控件不要拦截
                getParent().requestDisallowInterceptTouchEvent(true);
                downX = (int) ev.getX();
                downY = (int) ev.getY();
                if (mIsAutoScrollEnable) {
                    stopTimer();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int moveX = (int) ev.getX();
                int moveY = (int) ev.getY();
                // 下滑
                if (Math.abs(moveY - downY) > Math.abs(moveX - downX)) {
                    getParent().requestDisallowInterceptTouchEvent(false);
                } else {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
            case MotionEvent.ACTION_CANCEL:
                if (mIsAutoScrollEnable) {
                    startTimer();
                }
                break;
            case MotionEvent.ACTION_UP:
                if (mIsAutoScrollEnable) {
                    startTimer();
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    private OnDisplayImageL mOnDisplayImageL;
    private ScrollListener bannerScrollStateI;
    public void setBannerScrollStateI(ScrollListener bannerScrollStateI) {
        this.bannerScrollStateI = bannerScrollStateI;
    }

    public void setmOnDisplayImageL(OnDisplayImageL mOnDisplayImageL) {
        this.mOnDisplayImageL = mOnDisplayImageL;
    }



}
