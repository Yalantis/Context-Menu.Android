package com.yalantis.contextmenu.lib.extensions

import android.view.View
import com.nineoldandroids.animation.AnimatorSet
import com.nineoldandroids.animation.ObjectAnimator
import com.yalantis.contextmenu.lib.MenuGravity

private const val ROTATION_Y_PROPERTY = "rotationY"
private const val ROTATION_X_PROPERTY = "rotationX"
private const val ALPHA_PROPERTY = "alpha"
private const val TRANSLATION_X_PROPERTY = "translationX"

internal fun View.rotationCloseHorizontal(gravity: MenuGravity): ObjectAnimator {
    val from = 0f
    var to = when (gravity) {
        MenuGravity.END -> -90f
        MenuGravity.START -> 90f
    }

    if (context.isLayoutDirectionRtl()) {
        to *= -1f
    }

    return ObjectAnimator.ofFloat(this, ROTATION_Y_PROPERTY, from, to)
}

internal fun View.rotationOpenHorizontal(gravity: MenuGravity): ObjectAnimator {
    var from = when (gravity) {
        MenuGravity.END -> -90f
        MenuGravity.START -> 90f
    }
    val to = 0f

    if (context.isLayoutDirectionRtl()) {
        from *= -1f
    }

    return ObjectAnimator.ofFloat(this, ROTATION_Y_PROPERTY, from, to)
}

internal fun View.rotationCloseVertical(): ObjectAnimator =
        ObjectAnimator.ofFloat(this, ROTATION_X_PROPERTY, 0f, -90f)

internal fun View.rotationOpenVertical(): ObjectAnimator =
        ObjectAnimator.ofFloat(this, ROTATION_X_PROPERTY, -90f, 0f)

internal fun View.alphaDisappear(): ObjectAnimator =
        ObjectAnimator.ofFloat(this, ALPHA_PROPERTY, 1f, 0f)

internal fun View.alphaAppear(): ObjectAnimator =
        ObjectAnimator.ofFloat(this, ALPHA_PROPERTY, 0f, 1f)

internal fun View.translationEnd(x: Float): ObjectAnimator {
    var from = 0f
    var to = x

    if (context.isLayoutDirectionRtl()) {
        from = x
        to = 0f
    }

    return ObjectAnimator.ofFloat(this, TRANSLATION_X_PROPERTY, from, to)
}

internal fun View.translationStart(x: Float): ObjectAnimator {
    var from = x
    var to = 0f

    if (context.isLayoutDirectionRtl()) {
        from = 0f
        to = x
    }

    return ObjectAnimator.ofFloat(this, TRANSLATION_X_PROPERTY, from, to)
}

internal fun View.fadeOutSet(x: Float, gravity: MenuGravity): AnimatorSet = AnimatorSet().apply {
    val translation = when (gravity) {
        MenuGravity.END -> translationEnd(x)
        MenuGravity.START -> translationStart(x)
    }
    playTogether(alphaDisappear(), translation)
}