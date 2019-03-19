package com.lhp.fastvlayout.banner;

import android.content.Context;
import android.util.AttributeSet;

public class ViewPager extends LoopViewPager {

	public ViewPager(Context context) {
		super(context);
	}

	public ViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//		int height = 0;
//		for (int i = 0; i < getChildCount(); i++) {
//			View child = getChildAt(i);
//			child.measure(widthMeasureSpec,
//					View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//			int h = child.getMeasuredHeight();
//			if (h > height)
//				height = h;
//		}
//
//		heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(height,
//				View.MeasureSpec.EXACTLY);

		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

}
