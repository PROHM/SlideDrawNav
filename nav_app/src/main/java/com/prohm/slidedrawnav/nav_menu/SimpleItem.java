package com.prohm.slidedrawnav.nav_menu;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.prohm.slidedrawnav.R;

/**
 *  *****************************************************
 Created by Mblack47 on 16.07.2023 a 05:00 PM
 *****************************************************
 */
public class SimpleItem extends DrawerItem<SimpleItem.ViewHolder>
{
    private int selectedItemIconTint;
    private int selectedItemTextTint;
    private int normalItemIconTint;
    private int normalItemTextTint;

    private Drawable navIcon;
    private String navTittle;

    public SimpleItem(Drawable nav_icon, String nav_title)
    {
        this.navIcon = nav_icon;
        this.navTittle = nav_title;
    }

    @Override
    public ViewHolder createViewHolder(ViewGroup parent)
    {
        LayoutInflater inflater  = LayoutInflater.from(parent.getContext());
        View v = inflater.inflate(R.layout.item_option, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void bindViewHolder(ViewHolder holder)
    {
        holder.navTittle.setText(navTittle);
        holder.navIcon.setImageDrawable(navIcon);

        holder.navTittle.setText(isChecked ? selectedItemTextTint : normalItemTextTint);
        holder.navIcon.setColorFilter(isChecked ? selectedItemIconTint : normalItemIconTint);
    }

    public SimpleItem withSelectedIconTint(int selectedItemIconTint)
    {
        this.selectedItemIconTint = selectedItemIconTint;
        return this;
    }

    public SimpleItem withSelectedTextTint (int selectedItemTextTint)
    {
        this.selectedItemTextTint = selectedItemTextTint;
        return this;
    }

    public SimpleItem withIconTint(int normalItemIconTint)
    {
        this.normalItemIconTint = normalItemIconTint;
        return this;
    }

    public SimpleItem withTextTint(int normalItemTextTint)
    {
        this.normalItemTextTint = normalItemTextTint;
        return this;
    }

    static class ViewHolder extends DrawerAdapter.ViewHolder
    {
        private ImageView navIcon;
        private TextView navTittle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            navIcon = itemView.findViewById(R.id.imgIcon);
            navTittle = itemView.findViewById(R.id.txtTittle);
        }
    }
}
