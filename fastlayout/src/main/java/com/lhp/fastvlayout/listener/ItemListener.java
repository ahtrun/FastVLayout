package com.lhp.fastvlayout.listener;

import android.view.View;

/**
 * Created By lhp
 * on 2019/3/14
 */
public interface ItemListener<T> {
    void onItemClick(View view, int position, T mData);
}
