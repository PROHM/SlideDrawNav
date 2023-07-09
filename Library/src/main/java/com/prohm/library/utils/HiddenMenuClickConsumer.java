package com.prohm.library.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.prohm.library.main_slider.SlideNavLayout;

/**
 *  *****************************************************
 Created by Mblack47 on 09.07.2023 a 02:05 PM
 *****************************************************
 */
public class HiddenMenuClickConsumer extends View {
    private SlideNavLayout menuHost;

    public HiddenMenuClickConsumer(Context context)
    {
        super(context);
    }

    @Override
    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event) {
        return menuHost.isMenuClosed();
    }

    public void setMenuHost(SlideNavLayout layout) {
        this.menuHost = layout;
    }
}
