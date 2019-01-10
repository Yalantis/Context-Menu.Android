package com.yalantis.contextmenu.lib

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import com.nineoldandroids.animation.Animator
import com.nineoldandroids.animation.AnimatorSet
import com.nineoldandroids.view.ViewHelper
import com.yalantis.contextmenu.lib.extensions.*
import com.yalantis.contextmenu.lib.interfaces.OnItemClickListener
import com.yalantis.contextmenu.lib.interfaces.OnItemLongClickListener

class MenuAdapter(
        private val context: Context,
        private val menuWrapper: LinearLayout,
        private val textWrapper: LinearLayout,
        private val menuObjects: List<MenuObject>,
        private val actionBarSize: Int,
        private val gravity: MenuGravity
) {

    private var onItemClickListener: OnItemClickListener? = null
    private var onItemLongClickListener: OnItemLongClickListener? = null
    private var onItemClickListenerCalled: OnItemClickListener? = null
    private var onItemLongClickListenerCalled: OnItemLongClickListener? = null

    private var clickedView: View? = null

    private val hideMenuAnimatorSet: AnimatorSet by lazy { setOpenCloseAnimation(true) }
    private val showMenuAnimatorSet: AnimatorSet by lazy { setOpenCloseAnimation(false) }

    private var isMenuOpen = false
    private var isAnimationRun = false

    private var animationDurationMillis = ANIMATION_DURATION_MILLIS

    private val itemClickListener = View.OnClickListener { view ->
        onItemClickListenerCalled = onItemClickListener
        viewClicked(view)
    }

    private val itemLongClickListener = View.OnLongClickListener { view ->
        onItemLongClickListenerCalled = onItemLongClickListener
        viewClicked(view)
        true
    }

    init {
        setViews()
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
        onItemLongClickListener = listener
    }

    fun setAnimationDuration(durationMillis: Int) {
        animationDurationMillis = durationMillis.toLong()
        showMenuAnimatorSet.duration = animationDurationMillis
        hideMenuAnimatorSet.duration = animationDurationMillis
    }

    fun menuToggle() {
        if (!isAnimationRun) {
            resetAnimations()
            isAnimationRun = true

            if (isMenuOpen) {
                hideMenuAnimatorSet.start()
            } else {
                showMenuAnimatorSet.start()
            }

            toggleIsMenuOpen()
        }
    }

    fun getItemCount() = menuObjects.size

    private fun getLastItemPosition() = getItemCount() - 1

    /**
     * Creating views and filling to wrappers
     */
    private fun setViews() {
        menuObjects.forEachIndexed { index, menuObject ->
            context.apply {
                textWrapper.addView(
                        getItemTextView(
                                menuObject,
                                actionBarSize,
                                itemClickListener,
                                itemLongClickListener
                        )
                )
                menuWrapper.addView(
                        getImageWrapper(
                                menuObject,
                                actionBarSize,
                                itemClickListener,
                                itemLongClickListener,
                                index != getLastItemPosition()
                        )
                )
            }
        }
    }

    /**
     * Set starting params to vertical animations
     */
    private fun resetVerticalAnimation(view: View, toTop: Boolean) {
        if (!isMenuOpen) {
            ViewHelper.setRotation(view, ROTATION_ZERO_DEGREES)
            ViewHelper.setRotationY(view, ROTATION_ZERO_DEGREES)
            ViewHelper.setRotationX(view, -ROTATION_NINETY_DEGREES)
        }

        ViewHelper.setPivotX(view, (actionBarSize / 2).toFloat())
        ViewHelper.setPivotY(view, (if (!toTop) 0 else actionBarSize).toFloat())
    }

    /**
     * Set starting params to side animations
     */
    private fun resetSideAnimation(view: View) {
        if (!isMenuOpen) {
            ViewHelper.setRotation(view, ROTATION_ZERO_DEGREES)
            ViewHelper.setRotationY(view, getRotationY())
            ViewHelper.setRotationX(view, ROTATION_ZERO_DEGREES)
        }

        ViewHelper.setPivotX(view, getPivotX())
        ViewHelper.setPivotY(view, (actionBarSize / 2).toFloat())
    }

    private fun getRotationY() =
            when (gravity) {
                MenuGravity.END -> if (context.isLayoutDirectionRtl()) {
                    ROTATION_NINETY_DEGREES
                } else {
                    -ROTATION_NINETY_DEGREES
                }
                MenuGravity.START -> if (context.isLayoutDirectionRtl()) {
                    -ROTATION_NINETY_DEGREES
                } else {
                    ROTATION_NINETY_DEGREES
                }
            }

    private fun getPivotX() =
            when (gravity) {
                MenuGravity.END -> if (context.isLayoutDirectionRtl()) {
                    ROTATION_ZERO_DEGREES
                } else {
                    actionBarSize.toFloat()
                }
                MenuGravity.START -> if (context.isLayoutDirectionRtl()) {
                    actionBarSize.toFloat()
                } else {
                    ROTATION_ZERO_DEGREES
                }
            }

    /**
     * Set starting params to text animations
     */
    private fun resetTextAnimation(view: View) {
        ViewHelper.setAlpha(view, (if (!isMenuOpen) 0 else 1).toFloat())
        ViewHelper.setTranslationX(view, (if (!isMenuOpen) actionBarSize else 0).toFloat())
    }

    /**
     * Set starting params to all animations
     */
    private fun resetAnimations() {
        for (i in 0 until getItemCount()) {
            resetTextAnimation(textWrapper.getChildAt(i))

            if (i == 0) {
                resetSideAnimation(menuWrapper.getChildAt(i))
            } else {
                resetVerticalAnimation(menuWrapper.getChildAt(i), false)
            }
        }
    }

    /**
     * Creates open/close AnimatorSet
     */
    private fun setOpenCloseAnimation(isCloseAnimation: Boolean): AnimatorSet {
        val textAnimations = mutableListOf<Animator>()
        val imageAnimations = mutableListOf<Animator>()

        if (isCloseAnimation) {
            for (i in getLastItemPosition() downTo 0) {
                fillOpenClosingAnimations(true, textAnimations, imageAnimations, i)
            }
        } else {
            for (i in 0 until getItemCount()) {
                fillOpenClosingAnimations(false, textAnimations, imageAnimations, i)
            }
        }

        return AnimatorSet().apply {
            duration = animationDurationMillis
            startDelay = 0

            playTogether(
                    AnimatorSet().apply { playSequentially(textAnimations) },
                    AnimatorSet().apply { playSequentially(imageAnimations) }
            )
            setInterpolator(HesitateInterpolator())
            onAnimationEnd { toggleIsAnimationRun() }
        }
    }

    /**
     * Filling arrays of animations to build Set of Closing / Opening animations
     */
    private fun fillOpenClosingAnimations(
            isCloseAnimation: Boolean,
            textAnimations: MutableList<Animator>,
            imageAnimations: MutableList<Animator>,
            wrapperPosition: Int
    ) {
        textWrapper.getChildAt(wrapperPosition).apply {
            textAnimations.add(AnimatorSet().apply {
                val textAppearance = if (isCloseAnimation) {
                    alphaDisappear()
                } else {
                    alphaAppear()
                }

                val textTranslation = if (isCloseAnimation) {
                    when (gravity) {
                        MenuGravity.END -> translationEnd(getTextEndTranslation())
                        MenuGravity.START -> translationStart(getTextEndTranslation())
                    }
                } else {
                    when (gravity) {
                        MenuGravity.END -> translationStart(getTextEndTranslation())
                        MenuGravity.START -> translationEnd(getTextEndTranslation())
                    }
                }

                playTogether(textAppearance, textTranslation)
            })
        }

        menuWrapper.getChildAt(wrapperPosition).apply {
            imageAnimations.add(
                    if (isCloseAnimation) {
                        if (wrapperPosition == 0) {
                            rotationCloseHorizontal(gravity)
                        } else {
                            rotationCloseVertical()
                        }
                    } else {
                        if (wrapperPosition == 0) {
                            rotationOpenHorizontal(gravity)
                        } else {
                            rotationOpenVertical()
                        }
                    }
            )
        }
    }

    private fun viewClicked(view: View) {
        if (isMenuOpen && !isAnimationRun) {
            clickedView = view

            val childIndex = (view.parent as ViewGroup).indexOfChild(view)
            if (childIndex == -1) {
                return
            }

            toggleIsAnimationRun()
            buildChosenAnimation(childIndex)
            toggleIsMenuOpen()
        }
    }

    private fun buildChosenAnimation(childIndex: Int) {
        val fadeOutTextTopAnimatorList = mutableListOf<Animator>()
        val closeToBottomImageAnimatorList = mutableListOf<Animator>()

        for (i in 0 until childIndex) {
            val menuWrapperChild = menuWrapper.getChildAt(i)
            resetVerticalAnimation(menuWrapperChild, true)
            closeToBottomImageAnimatorList.add(menuWrapperChild.rotationCloseVertical())
            fadeOutTextTopAnimatorList.add(
                    textWrapper.getChildAt(i).fadeOutSet(getTextEndTranslation(), gravity)
            )
        }

        val fadeOutTextBottomAnimatorList = mutableListOf<Animator>()
        val closeToTopAnimatorObjects = mutableListOf<Animator>()

        for (i in getLastItemPosition() downTo childIndex + 1) {
            val menuWrapperChild = menuWrapper.getChildAt(i)
            resetVerticalAnimation(menuWrapperChild, false)
            closeToTopAnimatorObjects.add(menuWrapperChild.rotationCloseVertical())
            fadeOutTextBottomAnimatorList.add(
                    textWrapper.getChildAt(i).fadeOutSet(getTextEndTranslation(), gravity)
            )
        }

        resetSideAnimation(menuWrapper.getChildAt(childIndex))

        val closeToBottom = AnimatorSet()
        closeToBottom.playSequentially(closeToBottomImageAnimatorList)
        val fadeOutTop = AnimatorSet()
        fadeOutTop.playSequentially(fadeOutTextTopAnimatorList)

        val closeToTop = AnimatorSet()
        closeToTop.playSequentially(closeToTopAnimatorObjects)
        val fadeOutBottom = AnimatorSet()
        fadeOutBottom.playSequentially(fadeOutTextBottomAnimatorList)

        val closeToEnd = menuWrapper.getChildAt(childIndex).rotationCloseHorizontal(gravity)
        closeToEnd.onAnimationEnd {
            toggleIsAnimationRun()

            clickedView?.let { notNullView ->
                onItemClickListenerCalled?.onClick(notNullView)
                onItemLongClickListenerCalled?.onLongClick(notNullView)
            }
        }
        val fadeOutChosenText =
                textWrapper.getChildAt(childIndex).fadeOutSet(getTextEndTranslation(), gravity)

        val imageFullAnimatorSet = AnimatorSet()
        imageFullAnimatorSet.play(closeToBottom).with(closeToTop)
        val textFullAnimatorSet = AnimatorSet()
        textFullAnimatorSet.play(fadeOutTop).with(fadeOutBottom)

        if (closeToBottomImageAnimatorList.size >= closeToTopAnimatorObjects.size) {
            imageFullAnimatorSet.play(closeToBottom).before(closeToEnd)
            textFullAnimatorSet.play(fadeOutTop).before(fadeOutChosenText)
        } else {
            imageFullAnimatorSet.play(closeToTop).before(closeToEnd)
            textFullAnimatorSet.play(fadeOutBottom).before(fadeOutChosenText)
        }

        AnimatorSet().apply {
            playTogether(imageFullAnimatorSet, textFullAnimatorSet)
            duration = animationDurationMillis
            setInterpolator(HesitateInterpolator())
            start()
        }
    }

    private fun getTextEndTranslation() =
            context.getDimension(R.dimen.text_translation).toFloat()

    private fun toggleIsAnimationRun() {
        isAnimationRun = !isAnimationRun
    }

    private fun toggleIsMenuOpen() {
        isMenuOpen = !isMenuOpen
    }

    companion object {

        const val ANIMATION_DURATION_MILLIS = 100L

        private const val ROTATION_ZERO_DEGREES = 0f
        private const val ROTATION_NINETY_DEGREES = 90f
    }
}