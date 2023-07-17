package com.prohm.slidedrawnav.nav_menu;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

/**
 *  *****************************************************
 Created by Mblack47 on 16.07.2023 a 08:00 PM
 *****************************************************
 */
public class SpaceItem extends DrawerItem<SpaceItem.ViewHolder>
{
    private int spaceDp;

    public SpaceItem(int spaceDp)
    {
        this.spaceDp = spaceDp;
    }

    @Override
    public ViewHolder createViewHolder(ViewGroup parent) {
        Context c = parent.getContext();
        View view = new View(c);
        int height = (int) (c.getResources().getDisplayMetrics().density * spaceDp);
        view.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, height
        ));

        return new ViewHolder(view);
    }

    @Override
    public void bindViewHolder(ViewHolder holder) {

    }

    @Override
    public boolean isSelectable() {
        return false;
    }

    static class ViewHolder extends DrawerAdapter.ViewHolder
    {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
