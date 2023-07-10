package com.prohm.library.main_slider;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.appcompat.widget.Toolbar;

import androidx.annotation.FloatRange;
import androidx.annotation.IntRange;
import androidx.annotation.LayoutRes;
import androidx.appcompat.app.ActionBarDrawerToggle;

import com.prohm.library.R;
import com.prohm.library.callback.DragListener;
import com.prohm.library.callback.DragStateListener;
import com.prohm.library.transform.CompTransform;
import com.prohm.library.transform.ElevateTransform;
import com.prohm.library.transform.NavTransformation;
import com.prohm.library.transform.ScaleTransform;
import com.prohm.library.transform.YTranslateTransform;
import com.prohm.library.utils.ActionBarToggleAdapter;
import com.prohm.library.utils.DrawerListenerAdapter;
import com.prohm.library.utils.HiddenMenuClickConsumer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *  *****************************************************
 Created by Mblack47 on 09.07.2023 a 08:10 PM
 *****************************************************
 */
public class SlideNavBuilder {
    private static final float DEFAULT_END_SCALE = 0.65f;
    private static final int DEFAULT_END_ELEVATION_DP = 8;
    private static final int DEFAULT_DRAG_DIST_DP = 180;

    private Activity navActivity;
    private ViewGroup cntView;
    private View menuView;
    private int menuLayoutRes;

    private List<NavTransformation> navTransform;
    private List<DragListener> dragListeners;
    private List<DragStateListener> dragStateListeners;

    private int dragDistance;
    private Toolbar toolbar;
    private SlideGravity gravity;

    private boolean isMenuOpened;
    private boolean isMenuLocked;
    private boolean isContentClickableWhenMenuOpened;
    private Bundle savedState;

    public SlideNavBuilder(Activity activity)
    {
        this.navActivity = activity;
        this.navTransform = new ArrayList<>();
        this.dragListeners = new ArrayList<>();
        this.dragStateListeners = new ArrayList<>();
        this.gravity = SlideGravity.LEFT;
        this.dragDistance = dpToPx(DEFAULT_DRAG_DIST_DP);
        this.isContentClickableWhenMenuOpened = true;
    }

    public SlideNavBuilder withMenuView(View view)
    {
        menuView = view;
        return this;
    }

    public SlideNavBuilder withMenuLayout(@LayoutRes int layout)
    {
        menuLayoutRes = layout;
        return this;
    }

    public SlideNavBuilder withToolbarMenuToggle(Toolbar tb)
    {
        toolbar = tb;
        return this;
    }

    public SlideNavBuilder withGravity(SlideGravity g)
    {
        gravity = g;
        return this;
    }

    public SlideNavBuilder withContentView(ViewGroup cv)
    {
        cntView = cv;
        return this;
    }

    public SlideNavBuilder withMenuLocked(boolean locked)
    {
        isMenuLocked = locked;
        return this;
    }

    public SlideNavBuilder withSavedState(Bundle state)
    {
        savedState = state;
        return this;
    }

    public SlideNavBuilder withMenuOpened(boolean opened)
    {
        isMenuOpened = opened;
        return this;
    }

    public SlideNavBuilder withContentClickableWhenMenuOpened(boolean clickable)
    {
        isContentClickableWhenMenuOpened = clickable;
        return this;
    }

    public SlideNavBuilder withDragDistance(int dp)
    {
        return withDragDistancePx(dpToPx(dp));
    }

    public SlideNavBuilder withDragDistancePx(int px)
    {
        dragDistance = px;
        return this;
    }

    public SlideNavBuilder withNavViewScale(@FloatRange(from = 0.01f) float scale)
    {
        navTransform.add(new ScaleTransform(scale));
        return this;
    }

    public SlideNavBuilder withNavViewElevation(@IntRange(from = 0) int elevation)
    {
        return withNavViewElevationPx(dpToPx(elevation));
    }

    public SlideNavBuilder withNavViewElevationPx(@IntRange(from = 0) int elevation)
    {
        navTransform.add(new ElevateTransform(elevation));
        return this;
    }

    public SlideNavBuilder withNavViewYTranslation(int translation)
    {
        return withNavViewYTranslationPx(dpToPx(translation));
    }

    public SlideNavBuilder withNavViewYTranslationPx(int translation)
    {
        navTransform.add(new YTranslateTransform(translation));
        return this;
    }

    public SlideNavBuilder addNavTransformation(NavTransformation transform)
    {
        navTransform.add(transform);
        return this;
    }

    public SlideNavBuilder addDragListener(DragListener dragListener)
    {
        dragListeners.add(dragListener);
        return this;
    }

    public SlideNavBuilder addDragStateListener(DragStateListener dragStateListener)
    {
        dragStateListeners.add(dragStateListener);
        return this;
    }

    public SlideDrawNav inject()
    {
        ViewGroup cntView = getContentView();

        View oldNav = cntView.getChildAt(0);
        cntView.removeAllViews();

        SlideNavLayout newNav = createAndInitNewNav(oldNav);

        View menu = getMenuViewFor(newNav);

        initToolbarMenuVisibilityToggle(newNav, menu);

        HiddenMenuClickConsumer clickConsumer = new HiddenMenuClickConsumer(navActivity);
        clickConsumer.setMenuHost(newNav);

        newNav.addView(menu);
        newNav.addView(clickConsumer);
        newNav.addView(oldNav);

        cntView.addView(newNav);

        if (savedState == null)
        {
            if (isMenuOpened)
            {
                newNav.openMenu(false);
            }
        }

        newNav.setMenuLocked(isMenuLocked);

        return newNav;
    }

    private SlideNavLayout createAndInitNewNav(View oldNav)
    {
        SlideNavLayout newNav = new SlideNavLayout(navActivity);
        newNav.setId(R.id.srn_root_layout);
        newNav.setNavTransformation(createCompositeTransformation());
        newNav.setMaxDragDistance(dragDistance);
        newNav.setGravity(gravity);
        newNav.setNavView(oldNav);
        newNav.setContentClickableWhenMenuOpened(isContentClickableWhenMenuOpened);

        for (DragListener l : dragListeners)
        {
            newNav.addDragListener(l);
        }

        for (DragStateListener l : dragStateListeners)
        {
            newNav.addDragStateListener(l);
        }

        return newNav;
    }

    private ViewGroup getContentView()
    {
        if (cntView == null)
        {
            cntView = navActivity.findViewById(android.R.id.content);
        }

        if (cntView.getChildCount() != 1)
        {
            throw new IllegalStateException(navActivity.getString(R.string.srn_ex_bad_content_view));
        }
        return cntView;
    }

    private View getMenuViewFor(SlideNavLayout parent)
    {
        if (menuView == null)
        {
            if (menuLayoutRes == 0)
            {
                throw new IllegalStateException(navActivity.getString(R.string.srn_ex_no_menu_view));
            }
            menuView = LayoutInflater.from(navActivity).inflate(menuLayoutRes, parent, false);
        }
        return menuView;
    }

    private NavTransformation createCompositeTransformation()
    {
        if (navTransform.isEmpty())
        {
            return new CompTransform(Arrays.asList(
                    new ScaleTransform(DEFAULT_END_SCALE),
                    new ElevateTransform(dpToPx(DEFAULT_END_ELEVATION_DP))
            ));
        }
        else
        {
            return new CompTransform(navTransform);
        }
    }

    protected void initToolbarMenuVisibilityToggle(final SlideNavLayout sideNav, View drawer)
    {
        if (toolbar != null)
        {
            ActionBarToggleAdapter dlAdapter = new ActionBarToggleAdapter(navActivity);
            dlAdapter.setNavAdapter(sideNav);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(navActivity, dlAdapter, toolbar,
                    R.string.srn_drawer_open,
                    R.string.srn_drawer_close);
            toggle.syncState();
            DrawerListenerAdapter listenerAdapter = new DrawerListenerAdapter(toggle, drawer);
            sideNav.addDragListener(listenerAdapter);
            sideNav.addDragStateListener(listenerAdapter);
        }
    }

    private int dpToPx(int dp)
    {
        return Math.round(navActivity.getResources().getDisplayMetrics().density * dp);
    }
}