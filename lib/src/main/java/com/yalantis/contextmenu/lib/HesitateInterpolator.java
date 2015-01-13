package com.yalantis.contextmenu.lib;

import android.view.animation.Interpolator;

public class HesitateInterpolator implements Interpolator {

    public HesitateInterpolator() {
    }

    public float getInterpolation(float t) {
        float x = 2.0f * t - 1.0f;
        return 0.5f * (x * x * x + 1.0f);
    }
}