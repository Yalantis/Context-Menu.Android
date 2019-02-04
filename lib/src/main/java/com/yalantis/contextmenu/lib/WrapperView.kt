package com.yalantis.contextmenu.lib

import android.content.Context
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.ScrollView
import com.yalantis.contextmenu.lib.extensions.getDimension

open class WrapperView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
) : ScrollView(context, attrs, defStyleAttr) {

    val rootRelativeLayout: RelativeLayout by lazy { RelativeLayout(context) }
    val wrapperButtons: LinearLayout by lazy { LinearLayout(context) }
    val wrapperText: LinearLayout by lazy { LinearLayout(context) }

    init {
        setupScrollView()
        setupRootRelativeLayout()
        setupWrappers()
    }

    open fun show(menuGravity: MenuGravity) {
        wrapperButtons.layoutParams =
                RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    addRule(
                            if (menuGravity == MenuGravity.START) {
                                RelativeLayout.ALIGN_PARENT_START
                            } else {
                                RelativeLayout.ALIGN_PARENT_END
                            }
                    )
                }

        wrapperText.apply {
            gravity = if (menuGravity == MenuGravity.START) {
                Gravity.START
            } else {
                Gravity.END
            }
            layoutParams =
                    RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        val verb = if (menuGravity == MenuGravity.START) {
                            RelativeLayout.END_OF
                        } else {
                            RelativeLayout.START_OF
                        }
                        addRule(verb, wrapperButtons.id)
                        setMargins(
                                0,
                                0,
                                context.getDimension(R.dimen.text_start_end_margin),
                                0
                        )
                    }
        }
    }

    private fun setupScrollView() {
        isFillViewport = true
    }

    private fun setupRootRelativeLayout() {
        addView(
                rootRelativeLayout.apply {
                    id = ViewCompat.generateViewId()
                    layoutParams = FrameLayout.LayoutParams(
                            FrameLayout.LayoutParams.MATCH_PARENT,
                            FrameLayout.LayoutParams.WRAP_CONTENT
                    )
                }
        )
    }

    private fun setupWrappers() {
        rootRelativeLayout.addView(
                wrapperButtons.apply {
                    id = ViewCompat.generateViewId()
                    orientation = LinearLayout.VERTICAL
                }
        )

        rootRelativeLayout.addView(
                wrapperText.apply {
                    id = ViewCompat.generateViewId()
                    orientation = LinearLayout.VERTICAL
                }
        )
    }
}