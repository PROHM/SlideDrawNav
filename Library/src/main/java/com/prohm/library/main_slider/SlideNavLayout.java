package com.prohm.library.main_slider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

import com.prohm.library.callback.DragListener;
import com.prohm.library.callback.DragStateListener;
import com.prohm.library.transform.NavTransformation;

import java.util.ArrayList;
import java.util.List;

/**
 *  *****************************************************
 Created by Mblack47 on 09.07.2023 a 01:00 PM
 *****************************************************
 */
public class SlideNavLayout extends FrameLayout implements SlideDrawNav {

    private static final String EXTRA_IS_OPENED = "extra_is_opened";
    private static final String EXTRA_SUPER = "extra_super";
    private static final String EXTRA_SHOULD_BLOCK_CLICK = "extra_should_block_click";
    private static final Rect tempRect = new Rect();
    private final float FLING_MIN_VELOCITY;

    private boolean isMenuLocked;
    private boolean isMenuHidden;
    private boolean isContentClickableWhenMenuOpened;

    private NavTransformation navTransformation;
    private View navView;

    private float dragProgress;
    private int maxDragDistance;
    private int dragState;

    private ViewDragHelper dragHelper;
    private SlideGravity.Helper posHelper;
    private List<DragListener> dragListeners;
    private List<DragStateListener> dragStateListeners;

    public SlideNavLayout(Context context)
    {
        super(context);
        dragListeners = new ArrayList<>();
        dragStateListeners = new ArrayList<>();

        FLING_MIN_VELOCITY = ViewConfiguration.get(context).getScaledMinimumFlingVelocity();

        dragHelper = ViewDragHelper.create(this, new ViewDragCallBack());

        dragProgress = 0f;
        isMenuHidden = true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        return (!isMenuLocked && dragHelper.shouldInterceptTouchEvent(ev)) || shouldBlockClick(ev);
    }

    @Override
    @SuppressLint("ClickableViewAccessibility")
    public boolean onTouchEvent(MotionEvent event)
    {
        dragHelper.processTouchEvent(event);
        return true;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom)
    {
        for (int i = 0; i < getChildCount(); i++)
        {
            View child = getChildAt(i);
            if (child == navView)
            {
                int navLeft = posHelper.getRootLeft(dragProgress, maxDragDistance);
                child.layout(navLeft, top, navLeft + (right - left), bottom);
            }
            else
            {
                child.layout(left, top, right, bottom);
            }
        }
    }

    @Override
    public void computeScroll()
    {
        if (dragHelper.continueSettling(true))
        {
            ViewCompat.postInvalidateOnAnimation(this);
        }
    }

    private void changeMenuVisibility(boolean animated, float newDragProgress)
    {
        isMenuHidden = calcIsMenuHidden();
        if (animated)
        {
            int left = posHelper.getLeftToSettle(newDragProgress, maxDragDistance);
            if (dragHelper.smoothSlideViewTo(navView, left, navView.getTop()))
            {
                ViewCompat.postInvalidateOnAnimation(this);
            }
        }
        else
        {
            dragProgress = newDragProgress;
            navTransformation.transform(dragProgress, navView);
            requestLayout();
        }
    }

    @Override
    public boolean isMenuClosed() {
        return isMenuHidden;
    }

    @Override
    public boolean isMenuOpened() {
        return !isMenuHidden;
    }

    @Override
    public SlideNavLayout getLayout() {return this; }

    @Override
    public boolean isMenuLocked() {
        return isMenuLocked;
    }

    @Override
    public void closeMenu() { closeMenu(true); }

    @Override
    public void closeMenu(boolean animated) { changeMenuVisibility(animated, 0f); }

    @Override
    public void openMenu() { openMenu(true); }

    @Override
    public void openMenu(boolean animated) { changeMenuVisibility(animated, 1f); }

    @Override
    public void setMenuLocked(boolean locked) { isMenuLocked = locked; }

    public void setNavView(View navView) {
        this.navView = navView;
    }

    public void setContentClickableWhenMenuOpened(boolean contentClickableWhenMenuOpened) {
        isContentClickableWhenMenuOpened = contentClickableWhenMenuOpened;
    }

    public void setNavTransformation(NavTransformation navtransformation) {
        navTransformation = navtransformation;
    }

    public void setMaxDragDistance(int maxDragDistance) {
        this.maxDragDistance = maxDragDistance;
    }

    public void setGravity(SlideGravity navGravity)
    {
        posHelper = navGravity.createHelper();
        posHelper.enableEdgeTrackingOn(dragHelper);
    }

    public void addDragListener(DragListener listener)
    {
        dragListeners.add(listener);
    }

    public void addDragStateListener(DragStateListener listener)
    {
        dragStateListeners.add(listener);
    }

    public void removeDragListener(DragListener listener)
    {
        dragListeners.remove(listener);
    }

    public void removeDragStateListener(DragStateListener listener)
    {
        dragStateListeners.remove(listener);
    }

    public float getDragProgress() {
        return dragProgress;
    }

    private boolean shouldBlockClick(MotionEvent event)
    {
        if (isContentClickableWhenMenuOpened)
        {
            return false;
        }
        if (navView != null && isMenuOpened())
        {
            navView.getHitRect(tempRect);
            return tempRect.contains((int) event.getX(), (int) event.getY());
        }
        return false;
    }

    private void notifyDrag()
    {
        for (DragListener listener : dragListeners)
        {
            listener.onDrag(dragProgress);
        }
    }

    private void notifyDragStart()
    {
        for (DragStateListener listener : dragStateListeners)
        {
            listener.onDragStart();
        }
    }

    private void notifyDragEnd(boolean isOpened)
    {
        for (DragStateListener listener : dragStateListeners)
        {
            listener.onDragEnd(isOpened);
        }
    }

    @Override
    protected Parcelable onSaveInstanceState() {
        Bundle savedState = new Bundle();
        savedState.putParcelable(EXTRA_SUPER, super.onSaveInstanceState());
        savedState.putInt(EXTRA_IS_OPENED, dragProgress > 0.5 ? 1 : 0);
        savedState.putBoolean(EXTRA_SHOULD_BLOCK_CLICK, isContentClickableWhenMenuOpened);
        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        Bundle savedState = (Bundle) state;
        super.onRestoreInstanceState(savedState.getParcelable(EXTRA_SUPER));
        changeMenuVisibility(false, savedState.getInt(EXTRA_IS_OPENED, 0));
        isMenuHidden = calcIsMenuHidden();
        isContentClickableWhenMenuOpened = savedState.getBoolean(EXTRA_SHOULD_BLOCK_CLICK);
    }

    private boolean calcIsMenuHidden() { return dragProgress == 0f; }

    private class ViewDragCallBack extends ViewDragHelper.Callback
    {
        private boolean edgeTouched;
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId)
        {
            if (isMenuLocked)
            {
                return false;
            }
            boolean isOnEdge = edgeTouched;
            edgeTouched = false;
            if (isMenuClosed())
            {
                return child == navView && isOnEdge;
            }
            else
            {
                if(child != navView)
                {
                    dragHelper.captureChildView(navView, pointerId);
                    return false;
                }
                return true;
            }
        }

        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy)
        {
            dragProgress = posHelper.getDragProgress(left, maxDragDistance);
            navTransformation.transform(dragProgress, navView);
            notifyDrag();
            invalidate();
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel)
        {
            int left = Math.abs(xvel) < FLING_MIN_VELOCITY ?
                    posHelper.getLeftToSettle(dragProgress, maxDragDistance) :
                    posHelper.getLeftAfterFling(xvel, maxDragDistance);
            dragHelper.settleCapturedViewAt(left, navView.getTop());
            invalidate();
        }

        @Override
        public void onViewDragStateChanged(int state)
        {
            if (dragState == ViewDragHelper.STATE_IDLE && state != ViewDragHelper.STATE_IDLE)
            {
                notifyDragStart();
            } else if (dragState !=  ViewDragHelper.STATE_IDLE && state == ViewDragHelper.STATE_IDLE)
            {
                isMenuHidden = calcIsMenuHidden();
                notifyDragEnd(isMenuOpened());
            }
            dragState = state;
        }

        @Override
        public void onEdgeTouched(int edgeFlags, int pointerId)
        {
            edgeTouched = true;
        }

        @Override
        public int getViewHorizontalDragRange(@NonNull View child)
        {
            return child == navView ? maxDragDistance : 0;
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            return posHelper.clampViewPosition(left, maxDragDistance);
        }
    }
}