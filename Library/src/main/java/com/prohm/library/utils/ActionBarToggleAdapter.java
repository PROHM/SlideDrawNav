package com.prohm.library.utils;

import android.content.Context;
import androidx.drawerlayout.widget.DrawerLayout;

import com.prohm.library.main_slider.SlideNavLayout;

/**
 *  *****************************************************
 Created by Mblack47 on 04.07.2023 a 10:27 PM
 *****************************************************
 */
public class ActionBarToggleAdapter extends DrawerLayout {
    private SlideNavLayout navAdapter;

    public ActionBarToggleAdapter(Context context) { super(context); }

    @Override
    public void openDrawer(int gravity) {
        navAdapter.openMenu();
    }

    @Override
    public void closeDrawer(int gravity) {
        navAdapter.closeMenu();
    }

    @Override
    public boolean isDrawerVisible(int drawerGravity) {
        return !navAdapter.isMenuClosed();
    }

    @Override
    public int getDrawerLockMode(int edgeGravity) {
        if (navAdapter.isMenuLocked() && navAdapter.isMenuClosed())
        {
            return DrawerLayout.LOCK_MODE_LOCKED_CLOSED;
        }
        else if (navAdapter.isMenuLocked() && !navAdapter.isMenuClosed())
        {
            return DrawerLayout.LOCK_MODE_LOCKED_OPEN;
        }
        else
        {
            return DrawerLayout.LOCK_MODE_UNLOCKED;
        }
    }

    public void setNavAdapter(SlideNavLayout navAdapter) {
        this.navAdapter = navAdapter;
    }
}