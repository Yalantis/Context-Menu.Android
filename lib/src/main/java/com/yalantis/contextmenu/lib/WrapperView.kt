package com.yalantis.contextmenu.lib

import android.content.Context
import android.support.v4.content.ContextCompat
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

    fun showOnTheStartSide() {
        wrapperButtons.layoutParams =
                RelativeLayout.LayoutParams(
                        RelativeLayout.LayoutParams.WRAP_CONTENT,
                        RelativeLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    addRule(RelativeLayout.ALIGN_PARENT_START)
                }

        wrapperText.apply {
            gravity = Gravity.START
            layoutParams =
                    RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT
                    ).apply {
                        addRule(RelativeLayout.END_OF, wrapperButtons.id)
                        setMargins(
                                context.getDimension(R.dimen.text_start_end_margin),
                                0,
                                0,
                                0
                        )
                    }
        }
    }

    private fun setupScrollView() {
        setBackgroundColor(ContextCompat.getColor(context, R.color.menu_fragment_background))
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
                    layoutParams =
                            RelativeLayout.LayoutParams(
                                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                                    RelativeLayout.LayoutParams.WRAP_CONTENT
                            ).apply {
                                addRule(RelativeLayout.ALIGN_PARENT_END)
                            }
                }
        )

        rootRelativeLayout.addView(
                wrapperText.apply {
                    id = ViewCompat.generateViewId()
                    orientation = LinearLayout.VERTICAL
                    gravity = Gravity.END
                    layoutParams =
                            RelativeLayout.LayoutParams(
                                    RelativeLayout.LayoutParams.WRAP_CONTENT,
                                    RelativeLayout.LayoutParams.WRAP_CONTENT
                            ).apply {
                                addRule(RelativeLayout.START_OF, wrapperButtons.id)
                                setMargins(
                                        0,
                                        0,
                                        context.getDimension(R.dimen.text_start_end_margin),
                                        0
                                )
                            }
                }
        )
    }
}