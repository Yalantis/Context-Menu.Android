package com.yalantis.contextmenu.lib.extensions

import android.animation.Animator

internal fun Animator.onAnimationEnd(onAnimationEnd: (Animator?) -> Unit) {
    this.addListener(object : Animator.AnimatorListener {
        override fun onAnimationRepeat(animation: Animator?) {
            // do nothing
        }

        override fun onAnimationEnd(animation: Animator?) {
            onAnimationEnd(animation)
        }

        override fun onAnimationCancel(animation: Animator?) {
            // do nothing
        }

        override fun onAnimationStart(animation: Animator?) {
            // do nothing
        }
    })
}