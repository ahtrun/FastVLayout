package com.example.test.andlang.andlangutil;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.multidex.MultiDex;
import android.telephony.TelephonyManager;
import android.widget.ImageView;

import com.example.test.andlang.R;
import com.example.test.andlang.log.AppCrashHandler;
import com.example.test.andlang.util.BaseLangUtil;
import com.example.test.andlang.util.LogUtil;
import com.example.test.andlang.util.PreferencesUtil;
import com.example.test.andlang.util.imageload.*;
import com.example.test.andlang.util.imageload.IInnerImageSetter;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;
//import com.squareup.leakcanary.LeakCanary;
//import com.squareup.leakcanary.RefWatcher;

import java.io.File;

/**
 * Created by root on 18-3-27.
 */

public class BaseLangApplication extends Application {
    public static final DisplayImageOptions BOUTIQUE_OPTIPON = new DisplayImageOptions.Builder()
            .showImageOnLoading(R.mipmap.image_def) // 设置图片下载期间显示的图片
            .showImageForEmptyUri(R.mipmap.image_def) // 设置图片Uri为空或是错误的时候显示的图片
            .showImageOnFail(R.mipmap.image_def) // 设置图片加载或解码过程中发生错误显示的图片
            .bitmapConfig(Bitmap.Config.RGB_565)
            .imageScaleType(ImageScaleType.IN_SAMPLE_INT)//设置图片以如何的编码方式显示
            .cacheInMemory(true)//设置下载的图片是否缓存在内存中
            .cacheOnDisk(true)//设置下载的图片是否缓存在SD卡中
            .build(); // 创建配置过得DisplayImageO

    public static String tmpImageDir = "sudian";
    public static long NOW_TIME; // 当前服务器时间
    public static String logDir;
    private static BaseLangApplication mApp;
    private PreferencesUtil mSpUtil;
//    private RefWatcher refWatcher;//内存检测
    @Override
    public void onCreate() {
        super.onCreate();
        initData();
//        if(LogUtil.isCash) {
//            refWatcher = setupLeakCanary();
//        }
    }
//    private RefWatcher setupLeakCanary() {
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return RefWatcher.DISABLED;
//        }
//        return LeakCanary.install(this);
//    }

//    public static RefWatcher getRefWatcher(Context context) {
//        BaseLangApplication leakApplication = (BaseLangApplication) context.getApplicationContext();
//        return leakApplication.refWatcher;
//    }
    private void initData() {
        mApp = this;
        //程序错误日志信息收集
        logDir = Environment.getExternalStorageDirectory().getPath() + "/sudian/crash/";
        AppCrashHandler crashHandler = AppCrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
        Logger.addLogAdapter(new AndroidLogAdapter());
        initImageLoad();
    }

    private void initImageLoad() {
        //配置uil工具
        File cacheDir = StorageUtils.getOwnCacheDirectory(
                getApplicationContext(), tmpImageDir);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
                .memoryCacheExtraOptions(480, 800)          // default = device screen dimensions
                //  .discCacheExtraOptions(480, 800, Bitmap.CompressFormat.JPEG, 75)
                .threadPriority(Thread.NORM_PRIORITY - 1)   // default
                .tasksProcessingOrder(QueueProcessingType.FIFO) // default
                .threadPoolSize(3)
                .denyCacheImageMultipleSizesInMemory()
                .memoryCache(new LruMemoryCache(2 * 1024 * 1024))
                .memoryCacheSize(2 * 1024 * 1024)
                .memoryCacheSizePercentage(13)              // default
                .diskCache(new UnlimitedDiskCache(cacheDir))
                .diskCacheSize(50 * 1024 * 1024)        // 缓冲大小
                .diskCacheFileCount(100)                // 缓冲文件数目
                .diskCacheFileNameGenerator(new HashCodeFileNameGenerator()) // default
                .imageDownloader(new BaseImageDownloader(this)) // default
                .defaultDisplayImageOptions(DisplayImageOptions.createSimple()) // default
                .writeDebugLogs()
                .build();

        //  2.单例ImageLoader类的初始化
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.init(config);

        ImageLoadUtils.setImageSetter(new IInnerImageSetter() {
            @Override
            public <IMAGE extends ImageView> void doLoadImageUrl(@NonNull IMAGE view, @Nullable String url) {
                if (!BaseLangUtil.isEmpty(url) && url.toLowerCase().contains(".gif")) {
                    GlideUtil.getInstance().displayGif(getApplicationContext(), url, view);
                } else {
                    GlideUtil.getInstance().display(getApplicationContext(), url, view);
                }
            }

            @Override
            public <IMAGE extends ImageView> void doLoadCircleImageUrl(@NonNull IMAGE view, @Nullable String url) {
                if (!BaseLangUtil.isEmpty(url) && url.toLowerCase().contains(".gif")) {
                    GlideUtil.getInstance().displayGif(getApplicationContext(), url, view);
                } else {
                    GlideUtil.getInstance().displayHead(getApplicationContext(), url, view);
                }
            }

            @Override
            public <IMAGE extends ImageView> void doLoadByImageLoader(@NonNull IMAGE view, @Nullable String url) {
                //测试比较加载速度使用
                ImageLoader.getInstance().displayImage(url,view, BOUTIQUE_OPTIPON);
            }

            @Override
            public <IMAGE extends ImageView> void doLoadImageRes(@NonNull IMAGE view, @Nullable int resId) {
                GlideUtil.getInstance().displayLocRes(getApplicationContext(), resId, view);
            }
        });
    }

    public static BaseLangApplication getInstance() {
        return mApp;
    }

    public synchronized PreferencesUtil getSpUtil() {
        if (mSpUtil == null)
            mSpUtil = new PreferencesUtil(this, PreferencesUtil.PREFERENCES_DEFAULT);
        return mSpUtil;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    /**imei*/
    public String getIMEI(){
        String imei=BaseLangApplication.getInstance().getSpUtil().getString(this,"imei");
        if(BaseLangUtil.isEmpty(imei)){
            try {
                TelephonyManager mTelephonyMgr=(TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);
                imei=mTelephonyMgr.getDeviceId();
                BaseLangApplication.getInstance().getSpUtil().putString(this,"imei",imei);
            } catch (SecurityException e) {
                imei= Settings.Secure.getString(
                        this.getContentResolver(), Settings.Secure.ANDROID_ID);
                BaseLangApplication.getInstance().getSpUtil().putString(this,"imei",imei);
            }
        }
        return imei;
    }
}
