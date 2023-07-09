package com.prohm.library.transform;

import android.view.View;

import com.prohm.library.utils.SideNavUtils;

/**
 * *****************************************************
 * Created by Mblack47 on 04.07.2023 a 09:40 PM
 * ****************************************************
 */
public class ElevateTransform implements NavTransformation {
    private static final float START_ELEVATION = 0f;

    private final float endElevation;

    public ElevateTransform(float endElevation) {
        this.endElevation = endElevation;
    }

    @Override
    public void transform(float dragProgress, View navView) {
        float elevate = SideNavUtils.evaluate(dragProgress, START_ELEVATION, endElevation);
        navView.setElevation(elevate);
    }
}
