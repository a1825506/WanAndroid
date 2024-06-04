package com.tgh.wanandroid.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

public class ViewPagerFixed extends ViewPager {
    public ViewPagerFixed(@NonNull Context context) {
        super(context);
    }

    public ViewPagerFixed(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        try{
            return super.onTouchEvent(ev);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        try{
            return super.onInterceptHoverEvent(event);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
        return false;
    }
}
