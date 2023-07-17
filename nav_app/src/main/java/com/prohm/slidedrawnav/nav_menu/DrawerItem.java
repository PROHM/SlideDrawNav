package com.prohm.slidedrawnav.nav_menu;

import android.view.ViewGroup;

/**
 *  *****************************************************
 Created by Mblack47 on 16.07.2023 a 05:00 PM
 *****************************************************
 */
public abstract class DrawerItem<T extends DrawerAdapter.ViewHolder>
{
    protected boolean isChecked;

    public abstract T createViewHolder(ViewGroup parent);
    public abstract void bindViewHolder(T holder);

    public DrawerItem<T>setChecked(boolean isChecked)
    {
        this.isChecked = isChecked;
        return this;
    }

    public boolean isChecked()
    {
        return isChecked();
    }

    public boolean isSelectable()
    {
        return true;
    }
}
