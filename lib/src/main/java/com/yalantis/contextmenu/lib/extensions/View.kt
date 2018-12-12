package com.yalantis.contextmenu.lib.extensions

import android.view.View
import com.nineoldandroids.animation.AnimatorSet
import com.nineoldandroids.animation.ObjectAnimator

private const val ROTATION_Y_PROPERTY = "rotationY"
private const val ROTATION_X_PROPERTY = "rotationX"
private const val ALPHA_PROPERTY = "alpha"
private const val TRANSLATION_X_PROPERTY = "translationX"

fun View.rotationCloseToRight(): ObjectAnimator =
        ObjectAnimator.ofFloat(this, ROTATION_Y_PROPERTY, 0f, -90f)

fun View.rotationOpenFromRight(): ObjectAnimator =
        ObjectAnimator.ofFloat(this, ROTATION_Y_PROPERTY, -90f, 0f)

fun View.rotationCloseVertical(): ObjectAnimator =
        ObjectAnimator.ofFloat(this, ROTATION_X_PROPERTY, 0f, -90f)

fun View.rotationOpenVertical(): ObjectAnimator =
        ObjectAnimator.ofFloat(this, ROTATION_X_PROPERTY, -90f, 0f)

fun View.alphaDisappear(): ObjectAnimator =
        ObjectAnimator.ofFloat(this, ALPHA_PROPERTY, 1f, 0f)

fun View.alphaAppear(): ObjectAnimator =
        ObjectAnimator.ofFloat(this, ALPHA_PROPERTY, 0f, 1f)

fun View.translationRight(x: Float): ObjectAnimator =
        ObjectAnimator.ofFloat(this, TRANSLATION_X_PROPERTY, 0f, x)

fun View.translationLeft(x: Float): ObjectAnimator =
        ObjectAnimator.ofFloat(this, TRANSLATION_X_PROPERTY, x, 0f)

fun View.fadeOutSet(x: Float): AnimatorSet = AnimatorSet().apply {
    playTogether(alphaDisappear(), translationRight(x))
}