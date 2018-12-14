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
        private val actionBarSize: Int
) {

    private var onItemClickListener: OnItemClickListener? = null
    private var onItemLongClickListener: OnItemLongClickListener? = null
    private var onItemClickListenerCalled: OnItemClickListener? = null
    private var onItemLongClickListenerCalled: OnItemLongClickListener? = null

    private var clickedView: View? = null

    private val hideMenuAnimatorSet: AnimatorSet by lazy { setOpenCloseAnimation(false) }
    private val showMenuAnimatorSet: AnimatorSet by lazy { setOpenCloseAnimation(true) }

    private var isMenuOpen = false
    private var isAnimationRun = false

    private var animationDurationMillis = ANIMATION_DURATION_MILLIS

    init {
        setViews()
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        onItemClickListener = listener
    }

    fun setOnItemLongClickListener(listener: OnItemLongClickListener) {
        onItemLongClickListener = listener
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
            ViewHelper.setRotation(view, 0f)
            ViewHelper.setRotationY(view, 0f)
            ViewHelper.setRotationX(view, -90f)
        }

        ViewHelper.setPivotX(view, (actionBarSize / 2).toFloat())
        ViewHelper.setPivotY(view, (if (!toTop) 0 else actionBarSize).toFloat())
    }

    /**
     * Set starting params to side animations
     */
    private fun resetSideAnimation(view: View) {
        if (!isMenuOpen) {
            ViewHelper.setRotation(view, 0f)
            ViewHelper.setRotationY(view, -90f)
            ViewHelper.setRotationX(view, 0f)
        }

        ViewHelper.setPivotX(view, actionBarSize.toFloat())
        ViewHelper.setPivotY(view, (actionBarSize / 2).toFloat())
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
            addListener(mCloseOpenAnimatorListener)
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
                    translationRight(getTextRightTranslation())
                } else {
                    translationLeft(getTextRightTranslation())
                }

                playTogether(textAppearance, textTranslation)
            })

        }

        menuWrapper.getChildAt(wrapperPosition).apply {
            imageAnimations.add(
                    if (isCloseAnimation) {
                        if (wrapperPosition == 0) {
                            rotationCloseToRight()
                        } else {
                            rotationCloseVertical()
                        }
                    } else {
                        if (wrapperPosition == 0) {
                            rotationOpenFromRight()
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
            val view = menuWrapper.getChildAt(i)
            resetVerticalAnimation(view, true)
            closeToBottomImageAnimatorList.add(view.rotationCloseVertical())
            fadeOutTextTopAnimatorList.add(
                    textWrapper.getChildAt(i).fadeOutSet(getTextRightTranslation())
            )
        }

        val closeToBottom = AnimatorSet()
        closeToBottom.playSequentially(closeToBottomImageAnimatorList)
        val fadeOutTop = AnimatorSet()
        fadeOutTop.playSequentially(fadeOutTextTopAnimatorList)

        val fadeOutTextBottomAnimatorList = mutableListOf<Animator>()
        val closeToTopAnimatorObjects = mutableListOf<Animator>()

        // TODO continue it
    }

    private fun getTextRightTranslation() =
            context.getDimension(R.dimen.text_right_translation).toFloat()

    private val itemClickListener = View.OnClickListener { view ->
        onItemClickListenerCalled = onItemClickListener
        viewClicked(view)
    }

    private val itemLongClickListener = View.OnLongClickListener { view ->
        onItemLongClickListenerCalled = onItemLongClickListener
        viewClicked(view)
        true
    }

    companion object {

        const val ANIMATION_DURATION_MILLIS = 100L
    }
}