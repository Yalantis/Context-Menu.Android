package com.yalantis.contextmenu.lib.extensions

import android.animation.AnimatorSet
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.support.annotation.ColorRes
import android.view.View
import com.yalantis.contextmenu.lib.MenuGravity
import com.yalantis.contextmenu.lib.R

const val ROTATION_ZERO_DEGREES = 0f
const val ROTATION_NINETY_DEGREES = 90f
const val ALPHA_INVISIBLE = 0f
const val ALPHA_VISIBLE = 1f
const val TRANSLATION_ZERO_VALUE = 0f

private const val ROTATION_Y_PROPERTY = "rotationY"
private const val ROTATION_X_PROPERTY = "rotationX"
private const val ALPHA_PROPERTY = "alpha"
private const val TRANSLATION_X_PROPERTY = "translationX"
private const val BACKGROUND_COLOR_PROPERTY = "backgroundColor"

internal fun View.rotationCloseHorizontal(gravity: MenuGravity): ObjectAnimator {
    val from = ROTATION_ZERO_DEGREES
    var to = when (gravity) {
        MenuGravity.END -> -ROTATION_NINETY_DEGREES
        MenuGravity.START -> ROTATION_NINETY_DEGREES
    }

    if (context.isLayoutDirectionRtl()) {
        to *= -1f
    }

    return ObjectAnimator.ofFloat(this, ROTATION_Y_PROPERTY, from, to)
}

internal fun View.rotationOpenHorizontal(gravity: MenuGravity): ObjectAnimator {
    var from = when (gravity) {
        MenuGravity.END -> -ROTATION_NINETY_DEGREES
        MenuGravity.START -> ROTATION_NINETY_DEGREES
    }
    val to = ROTATION_ZERO_DEGREES

    if (context.isLayoutDirectionRtl()) {
        from *= -1f
    }

    return ObjectAnimator.ofFloat(this, ROTATION_Y_PROPERTY, from, to)
}

internal fun View.rotationCloseVertical(): ObjectAnimator =
        ObjectAnimator.ofFloat(this, ROTATION_X_PROPERTY, ROTATION_ZERO_DEGREES, -ROTATION_NINETY_DEGREES)

internal fun View.rotationOpenVertical(): ObjectAnimator =
        ObjectAnimator.ofFloat(this, ROTATION_X_PROPERTY, -ROTATION_NINETY_DEGREES, ROTATION_ZERO_DEGREES)

internal fun View.alphaDisappear(): ObjectAnimator =
        ObjectAnimator.ofFloat(this, ALPHA_PROPERTY, ALPHA_VISIBLE, ALPHA_INVISIBLE)

internal fun View.alphaAppear(): ObjectAnimator =
        ObjectAnimator.ofFloat(this, ALPHA_PROPERTY, ALPHA_INVISIBLE, ALPHA_VISIBLE)

internal fun View.translationEnd(x: Float): ObjectAnimator {
    var from = TRANSLATION_ZERO_VALUE
    var to = x

    if (context.isLayoutDirectionRtl()) {
        from = x
        to = TRANSLATION_ZERO_VALUE
    }

    return ObjectAnimator.ofFloat(this, TRANSLATION_X_PROPERTY, from, to)
}

internal fun View.translationStart(x: Float): ObjectAnimator {
    var from = x
    var to = TRANSLATION_ZERO_VALUE

    if (context.isLayoutDirectionRtl()) {
        from = TRANSLATION_ZERO_VALUE
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

internal fun View.colorAnimation(
        duration: Long,
        @ColorRes startColorResId: Int,
        @ColorRes endColorResId: Int
): ObjectAnimator =
        ObjectAnimator.ofObject(
                this,
                BACKGROUND_COLOR_PROPERTY,
                ArgbEvaluator(),
                context.getColorCompat(startColorResId),
                context.getColorCompat(endColorResId)
        ).apply {
            this.duration = duration
            start()
        }

internal fun View.backgroundColorAppear(duration: Long): ObjectAnimator =
        colorAnimation(duration, android.R.color.transparent, R.color.menu_fragment_background)

internal fun View.backgroundColorDisappear(
        duration: Long,
        onAnimationEnd: () -> Unit
): ObjectAnimator =
        colorAnimation(
                duration,
                R.color.menu_fragment_background,
                android.R.color.transparent
        ).apply {
            onAnimationEnd {
                onAnimationEnd()
            }
        }