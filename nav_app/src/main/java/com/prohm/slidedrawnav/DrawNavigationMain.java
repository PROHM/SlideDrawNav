package com.prohm.slidedrawnav;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.TypedValue;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.prohm.library.main_slider.SlideDrawNav;
import com.prohm.library.main_slider.SlideNavBuilder;
import com.prohm.slidedrawnav.nav_menu.DrawerItem;
import com.prohm.slidedrawnav.nav_menu.SimpleItem;

public class DrawNavigationMain extends AppCompatActivity
{
    private static final int POS_CLOSE = 0;
    private static final int POS_DASHBOARD = 1;
    private static final int POS_SELLS = 2;
    private static final int POS_REPORTS = 3;
    private static final int POS_USER_PREFS = 4;
    private static final int POS_SETTINGS = 5;
    private static final int POS_HELP = 6;
    private static final int POS_LOGOUT = 7;

    private String[] screenTittles;
    private Drawable[] screenIcons;

    private SlideDrawNav slideDrawNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar tbNavView = findViewById(R.id.tbNavDraw);
        setSupportActionBar(tbNavView);

        slideDrawNav = new SlideNavBuilder(this)
                .withDragDistance(180)
                .withNavViewScale(0.75f)
                .withNavViewElevation(25)
                .withToolbarMenuToggle(tbNavView)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .inject();

        //screenIcons = loadScreenIcons();
        //screenTittles = loadScreenTittles();
    }
/*
    private DrawerItem createItemFor(int position)
    {
        return new SimpleItem(screenIcons[position], screenTittles[position])
                .withIconTint(fetchColorAttr(android.R.attr.colorAccent))
                .withTextTint(fetchColorAttr(android.R.attr.colorPrimary))
                .withSelectedIconTint()
                .withSelectedTextTint()
    }
*/
    private int fetchColorAttr(int colorAttr)
    {
        TypedValue typedValue = new TypedValue();
        Resources.Theme theme = getTheme();
        theme.resolveAttribute(colorAttr, typedValue, true);
        return typedValue.data;
    }
    /*
    private String[] loadScreenTittles()
    {
    }

    private Drawable[] loadScreenIcons()
    {
    }*/
}
