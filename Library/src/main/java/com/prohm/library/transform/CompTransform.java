package com.prohm.library.transform;

import android.view.View;

import java.util.List;

/**
 *  *****************************************************
 Created by Mblack47 on 04.07.2023 a 09:35 PM
 *****************************************************
 */
public class CompTransform implements NavTransformation {
    private List<NavTransformation> transform;

    public CompTransform(List<NavTransformation> transform)
    {
        this.transform = transform;
    }

    @Override
    public void transform(float dragProgress, View navView) {
        for (NavTransformation t : transform)
        {
            t.transform(dragProgress, navView);
        }
    }
}
