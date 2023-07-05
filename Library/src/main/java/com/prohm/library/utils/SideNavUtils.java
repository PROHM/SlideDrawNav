package com.prohm.library.utils;

/**
 *  *****************************************************
 Created by Mblack47 on 04.07.2023 a 09:48 PM
 *****************************************************
 */
public abstract class SideNavUtils {
    public static float evaluate(float fraction, float startValue, float endValue)
    {
        return startValue + fraction * (endValue - startValue);
    }
}
