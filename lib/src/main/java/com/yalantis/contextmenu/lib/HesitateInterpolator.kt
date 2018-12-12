package com.yalantis.contextmenu.lib

import android.view.animation.Interpolator

class HesitateInterpolator : Interpolator {

    override fun getInterpolation(input: Float): Float {
        val x = 2.0f * input - 1.0f
        return 0.5f * (x * x * x + 1.0f)
    }
}