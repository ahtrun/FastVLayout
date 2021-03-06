package com.example.test.andlang.andlangutil;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.test.andlang.R;
import com.example.test.andlang.util.ActivityUtil;
import com.example.test.andlang.util.BaseLangUtil;
import com.example.test.andlang.util.LogUtil;
import com.example.test.andlang.util.NetUtil;
import com.example.test.andlang.util.StatusBarUtils;
import com.example.test.andlang.util.ToastUtil;
//import com.squareup.leakcanary.RefWatcher;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Field;
import java.util.Observer;

import butterknife.ButterKnife;


/**
 * Created by lang on 18-3-7.
 */

public abstract class BaseLangActivity<T extends BaseLangPresenter> extends AppCompatActivity implements Observer {
    public T presenter;
    public RelativeLayout rlLoading;
    private ImageView ivLoading;
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
        ActivityUtil.getInstance().pushOneActivity(this);
        LogUtil.d("当前创建的Activity:" + getClass().getSimpleName());
        LogUtil.d("当前activity数量：" + ActivityUtil.getInstance().getActivitySize());
        Intent intent=getIntent();
        if(intent!=null){
            boolean noChangeStatus=intent.getBooleanExtra("noChangeStatus",false);
            if(!noChangeStatus) {
                setStatusBar(1, R.color.lang_colorWhite);
            }
        }
        initView();
        initLoading();
        initPresenter();
        setObserModelForTag();
        initData();
        initModel();
    }

    private void initModel(){
        if(presenter==null){
            LogUtil.e(BaseLangPresenter.TAG,"presenter 未创建 ");
        }else {
            presenter.initModel();
        }
    }
    private void setObserModelForTag(){
        if(presenter!=null&&presenter.model!=null){
            presenter.model.addObserver(this);
        }else {
            LogUtil.e("0.0","presenter 未初始化");
        }
    }
    public void initTitleBar(String title){
        initTitleBar(true,title);
    }
    public void initTitleBar(boolean isCanBack,String title){
        setStatusBar(1,R.color.colorLangTitle);
        LinearLayout llBack = ButterKnife.findById(this,R.id.lang_ll_back);
        ImageView lang_iv_back=ButterKnife.findById(this,R.id.lang_iv_back);
        TextView tvTitle = ButterKnife.findById(this,R.id.lang_tv_title);
        if(tvTitle!=null&& title!=null){
            tvTitle.setText(title);
            tvTitle.setTextColor(Color.argb( 255, 41, 41, 41));
        }
        if(llBack!=null){
            if(!isCanBack){
                llBack.setVisibility(View.GONE);
            }else {
                llBack.setVisibility(View.VISIBLE);
                lang_iv_back.getDrawable().setAlpha(255);
                llBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goBack();
                    }
                });
            }
        }
    }
    public void initTitleBar(boolean isCanBack,boolean haveClose,String title,boolean noChangeStatus){
        if(!noChangeStatus) {
            setStatusBar(1, R.color.colorLangTitle);
        }
        LinearLayout llBack = ButterKnife.findById(this,R.id.lang_ll_back);
        ImageView lang_iv_back=ButterKnife.findById(this,R.id.lang_iv_back);
        TextView tvTitle = ButterKnife.findById(this,R.id.lang_tv_title);
        TextView lang_tv_close=ButterKnife.findById(this,R.id.lang_tv_close);
        if(tvTitle!=null&& title!=null){
            tvTitle.setText(title);
            tvTitle.setTextColor(Color.argb( 255, 41, 41, 41));
        }
        if(llBack!=null){
            if(!isCanBack){
                llBack.setVisibility(View.GONE);
            }else {
                llBack.setVisibility(View.VISIBLE);
                lang_iv_back.getDrawable().setAlpha(255);
                llBack.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goBack();
                    }
                });
            }
        }
        if(lang_tv_close!=null){
            if(!haveClose){
                lang_tv_close.setVisibility(View.GONE);
            }else {
                lang_tv_close.setVisibility(View.VISIBLE);
                lang_tv_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        closeH5();
                    }
                });
            }
        }
    }
    public void initTitleBar(boolean isCanBack, String title, int imageId, View.OnClickListener listener){
        initTitleBar(isCanBack,title);
        ImageView ivEdit = ButterKnife.findById(this,R.id.lang_iv_right);
        if(ivEdit!=null){
            ivEdit.setVisibility(View.VISIBLE);
            ivEdit.setImageResource(imageId);
            if(listener!=null){
                ivEdit.setOnClickListener(listener);
            }
        }

    }
    public void initTitleBar(boolean isCanBack, String title, String rightTitle, View.OnClickListener listener){

        initTitleBar(isCanBack,title);
        TextView tvEdit = ButterKnife.findById(this,R.id.lang_tv_right);
        if(tvEdit!=null&& !BaseLangUtil.isEmpty(rightTitle)){
            tvEdit.setVisibility(View.VISIBLE);
            tvEdit.setText(rightTitle);
            if(listener!=null){
                tvEdit.setOnClickListener(listener);
            }
        }

    }

    public void setTitle(String title){
        TextView tvTitle = ButterKnife.findById(this,R.id.lang_tv_title);
        if(tvTitle!=null&& !BaseLangUtil.isEmpty(title)){
            tvTitle.setText(title);
            tvTitle.setTextColor(Color.argb( 255, 41, 41, 41));
        }
    }

    public void initLoading(){
        rlLoading=ButterKnife.findById(this,R.id.rl_loading);
        ivLoading=ButterKnife.findById(this,R.id.iv_loading);
    }

    /**  设置状态栏高度  */
    protected void setStatusBar(int flag,int colorId) {
        View mStatusBar=ButterKnife.findById(this,R.id.m_statusBar);
        if(flag==1){
            if(mStatusBar != null) {
                StatusBarUtils.translateStatusBar(this);
                mStatusBar.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams layoutParams = mStatusBar.getLayoutParams();
                layoutParams.height = StatusBarUtils.getStatusHeight(this);
                mStatusBar.setLayoutParams(layoutParams);
                mStatusBar.setBackgroundResource(colorId);
                StatusBarUtils.setTextColorStatusBar(this, true);
            } else {
                StatusBarUtils.setWindowStatusBarColor(this, R.color.lang_colorWhite);
            }
        } else{
            StatusBarUtils.setWindowStatusBarColor(this, R.color.lang_colorWhite);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        String localClassName = getClass().getSimpleName();
        MobclickAgent.onPageStart(localClassName); //手动统计页面("SplashScreen"为页面名称，可自定义)
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        String localClassName = getClass().getSimpleName();
        MobclickAgent.onPageEnd(localClassName); //手动统计页面("SplashScreen"为页面名称，可自定义)，必须保证 onPageEnd 在 onPause 之前调用，因为SDK会在 onPause 中保存onPageEnd统计到的页面数据。
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if(LogUtil.isCash) {
//            //内存检测
//            RefWatcher refWatcher = BaseLangApplication.getRefWatcher(this);
//            refWatcher.watch(this);
//        }
        if(presenter!=null&&presenter.model!=null){
            presenter.model.deleteObserver(this);
        }
        fixInputMethodManagerLeak(this);//修复键盘内存溢出
        fixHuaWeiMemoryLeak();//修复华为手机内存泄露
        ActivityUtil.getInstance().removeActivity(this);
    }

    //网络请求异常显示默认布局
    public void showNoNet(boolean isHaveCode){
        RelativeLayout rl_nonet=ButterKnife.findById(this,R.id.rl_nonet);
        TextView tv_net_reload=ButterKnife.findById(this,R.id.tv_net_reload);
        ImageView iv_net_data=ButterKnife.findById(this,R.id.iv_net_data);
        TextView tv_net_data=ButterKnife.findById(this,R.id.tv_net_data);
        if(rl_nonet!=null){
            rl_nonet.setVisibility(View.VISIBLE);
            rl_nonet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //拦截覆盖住的页面点击事件
                }
            });
            if(tv_net_reload!=null) {
                tv_net_reload.setVisibility(View.VISIBLE);
                tv_net_reload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //刷新
                        refreshNet();
                    }
                });
            }
            if(isHaveCode) {
                //有code  开小差
                iv_net_data.setImageResource(R.mipmap.lang_error);
                tv_net_data.setText(getResources().getString(R.string.tip_error));
            }else {
                //无code  网络不顺畅
                iv_net_data.setImageResource(R.mipmap.lang_nonet);
                tv_net_data.setText(getResources().getString(R.string.tip_no_network));
            }
        }
    }
    public void dismissNoNet(){
        RelativeLayout rl_nonet=ButterKnife.findById(this,R.id.rl_nonet);
        if(rl_nonet!=null) {
            rl_nonet.setVisibility(View.GONE);
        }
    }

    public void refreshNet(){
        //无网络刷新
        dismissNoNet();
    }

    /**
     * 展示网络加载动画
     */
    public void showWaitDialog() {
        if (rlLoading != null && ivLoading != null&&rlLoading.getVisibility()==View.GONE) {
            final Animation operatingAnim = AnimationUtils.loadAnimation(this, R.anim.loading);
            LinearInterpolator lin = new LinearInterpolator();
            operatingAnim.setInterpolator(lin);
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    //已在主线程中，可以更新UI
                    ivLoading.startAnimation(operatingAnim);
                }
            });
            rlLoading.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 关闭网络加载动画
     */
    public void dismissWaitDialog() {
        if (rlLoading != null && ivLoading != null&&rlLoading.getVisibility()==View.VISIBLE) {
            ivLoading.clearAnimation();
            rlLoading.setVisibility(View.GONE);
        }
    }
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            goBack();
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
    protected void goBack() {
        ActivityUtil.getInstance().exit(this);
    }


    //修复键盘内存溢出
    public void fixInputMethodManagerLeak(Context destContext) {
        if (destContext == null) {
            return;
        }

        InputMethodManager imm = (InputMethodManager) destContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        String [] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field f = null;
        Object obj_get = null;
        for (int i = 0;i < arr.length;i ++) {
            String param = arr[i];
            try{
                f = imm.getClass().getDeclaredField(param);
                if (f.isAccessible() == false) {
                    f.setAccessible(true);
                } // author: sodino mail:sodino@qq.com
                obj_get = f.get(imm);
                if (obj_get != null && obj_get instanceof View) {
                    View v_get = (View) obj_get;
                    if (v_get.getContext() == destContext) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        f.set(imm, null); // 置空，破坏掉path to gc节点
                    } else {
                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
                        break;
                    }
                }
            }catch(Throwable t){
                t.printStackTrace();
            }
        }
    }
    /**
     * 修复华为手机内存的泄露
     */
    public void fixHuaWeiMemoryLeak(){
        try {
            Class<?> GestureBoostManagerClass = Class.forName("android.gestureboost.GestureBoostManager");
            Field sGestureBoostManagerField = GestureBoostManagerClass.getDeclaredField("sGestureBoostManager");
            sGestureBoostManagerField.setAccessible(true);
            Object gestureBoostManager = sGestureBoostManagerField.get(GestureBoostManagerClass);
            Field contextField = GestureBoostManagerClass.getDeclaredField("mContext");
            contextField.setAccessible(true);
            if (contextField.get(gestureBoostManager)==this) {
                contextField.set(gestureBoostManager, null);
            }
        } catch (ClassNotFoundException e) {

        } catch (NoSuchFieldException e) {

        } catch (IllegalAccessException e) {

        } catch (Throwable t) {

        }

    }

    public void closeH5(){

    }

    public abstract int getLayoutId();//设置布局id
    public abstract void initView();
    public abstract void initPresenter();
    public abstract void initData();
}
