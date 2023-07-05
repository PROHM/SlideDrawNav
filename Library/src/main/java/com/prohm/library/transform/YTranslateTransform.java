package com.prohm.library.transform;

import android.view.View;

import com.prohm.library.utils.SideNavUtils;

/**
 *  *****************************************************
 Created by Mblack47 on 04.07.2023 a 09:50 PM
 *****************************************************
 */

public class YTranslateTransform implements NavTransformation {
    private static final float START_TRANSLATE = 0f;

    private final float endTranslate;

    public YTranslateTransform(float endTranslate) { this.endTranslate = endTranslate; }

    @Override
    public void transform(float dragProgress, View navView) {
        float translate = SideNavUtils.evaluate(dragProgress, START_TRANSLATE, endTranslate);
        navView.setTranslationY(translate);
    }
}
