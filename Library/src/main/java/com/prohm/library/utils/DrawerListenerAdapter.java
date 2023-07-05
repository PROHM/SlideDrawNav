package com.prohm.library.utils;

import android.view.View;

import androidx.drawerlayout.widget.DrawerLayout;

import com.prohm.library.callback.DragListener;
import com.prohm.library.callback.DragStateListener;

/**
 *  *****************************************************
 Created by Mblack47 on 04.07.2023 a 10:35 PM
 *****************************************************
 */
public class DrawerListenerAdapter implements DragListener, DragStateListener {
    private DrawerLayout.DrawerListener drawerAdpt;
    private View drawerView;

    public DrawerListenerAdapter(DrawerLayout.DrawerListener drawerAdpt, View drawerView)
    {
        this.drawerAdpt = drawerAdpt;
        this.drawerView = drawerView;
    }

    @Override
    public void onDrag(float progress) {
        drawerAdpt.onDrawerSlide(drawerView, progress);
    }

    @Override
    public void onDragStart() {
        drawerAdpt.onDrawerStateChanged(DrawerLayout.STATE_DRAGGING);
    }

    @Override
    public void onDragEnd(boolean isMenuOpened) {
        if (isMenuOpened)
        {
            drawerAdpt.onDrawerOpened(drawerView);
        }
        else
        {
            drawerAdpt.onDrawerClosed(drawerView);
        }
        drawerAdpt.onDrawerStateChanged(DrawerLayout.STATE_IDLE);
    }
}
