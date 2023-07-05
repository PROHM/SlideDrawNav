package com.prohm.library.transform;

import android.view.View;

import com.prohm.library.utils.SideNavUtils;

/**
 *  *****************************************************
 Created by Mblack47 on 04.07.2023 a 09:45 PM
 *****************************************************
 */
public class ScaleTransform implements NavTransformation {
    private static final float START_SCALE = 1f;

    private final float endScale;

    public ScaleTransform(float endScale) { this.endScale = endScale; }

    @Override
    public void transform(float dragProgress, View navView) {
        float scale = SideNavUtils.evaluate(dragProgress, START_SCALE, endScale);
        navView.setScaleX(scale);
        navView.setScaleY(scale);
    }
}
