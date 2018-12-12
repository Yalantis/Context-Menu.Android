package com.yalantis.contextmenu.lib

import android.view.View
import com.nineoldandroids.animation.AnimatorSet
import com.nineoldandroids.animation.ObjectAnimator

object AnimatorUtils {

    private const val ROTATION_Y_PROPERTY = "rotationY"
    private const val ROTATION_X_PROPERTY = "rotationX"
    private const val ALPHA_PROPERTY = "alpha"
    private const val TRANSLATION_X_PROPERTY = "translationX"

    @JvmStatic
    fun rotationCloseToRight(view: View): ObjectAnimator =
            ObjectAnimator.ofFloat(view, ROTATION_Y_PROPERTY, 0f, -90f)

    @JvmStatic
    fun rotationOpenFromRight(view: View): ObjectAnimator =
            ObjectAnimator.ofFloat(view, ROTATION_Y_PROPERTY, -90f, 0f)

    @JvmStatic
    fun rotationCloseVertical(view: View): ObjectAnimator =
            ObjectAnimator.ofFloat(view, ROTATION_X_PROPERTY, 0f, -90f)

    @JvmStatic
    fun rotationOpenVertical(view: View): ObjectAnimator =
            ObjectAnimator.ofFloat(view, ROTATION_X_PROPERTY, -90f, 0f)

    @JvmStatic
    fun alphaDisappear(view: View): ObjectAnimator =
            ObjectAnimator.ofFloat(view, ALPHA_PROPERTY, 1f, 0f)

    @JvmStatic
    fun alphaAppear(view: View): ObjectAnimator =
            ObjectAnimator.ofFloat(view, ALPHA_PROPERTY, 0f, 1f)

    @JvmStatic
    fun translationRight(view: View, x: Float): ObjectAnimator =
            ObjectAnimator.ofFloat(view, TRANSLATION_X_PROPERTY, 0f, x)

    @JvmStatic
    fun translationLeft(view: View, x: Float): ObjectAnimator =
            ObjectAnimator.ofFloat(view, TRANSLATION_X_PROPERTY, x, 0f)

    @JvmStatic
    fun fadeOutSet(view: View, x: Float): AnimatorSet = AnimatorSet().apply {
        playTogether(alphaDisappear(view), translationRight(view, x))
    }
}