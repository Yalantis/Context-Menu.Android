package com.yalantis.contextmenu.lib.extensions

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.v4.content.ContextCompat
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.yalantis.contextmenu.lib.MenuObject
import com.yalantis.contextmenu.lib.R

@JvmName("getDefaultActionBarSize")
fun Context.getDefaultActionBarSize(): Int {
    val styledAttrs = theme.obtainStyledAttributes(intArrayOf(android.R.attr.actionBarSize))
    val actionBarSize = styledAttrs.getDimension(0, 0f).toInt()
    styledAttrs.recycle()
    return actionBarSize
}

internal fun Context.getColorCompat(@ColorRes color: Int) = ContextCompat.getColor(this, color)

internal fun Context.getDimension(@DimenRes dimen: Int) = resources.getDimension(dimen).toInt()

internal fun Context.isLayoutDirectionRtl(): Boolean =
        resources.configuration.layoutDirection == View.LAYOUT_DIRECTION_RTL

internal fun Context.getItemTextView(
        menuItem: MenuObject,
        menuItemSize: Int,
        onCLick: View.OnClickListener,
        onLongClick: View.OnLongClickListener
): TextView = TextView(this).apply {
    val textColor = if (menuItem.textColor == 0) {
        android.R.color.white
    } else {
        menuItem.textColor
    }

    val styleResId = if (menuItem.menuTextAppearanceStyle > 0) {
        menuItem.menuTextAppearanceStyle
    } else {
        R.style.TextView_DefaultStyle
    }

    layoutParams = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            menuItemSize
    )
    text = menuItem.title
    gravity = Gravity.CENTER_VERTICAL

    setOnClickListener(onCLick)
    setOnLongClickListener(onLongClick)
    setPadding(
            getDimension(R.dimen.text_start_end_padding_medium),
            0,
            getDimension(R.dimen.text_start_end_padding_small),
            0
    )

    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
        setTextAppearance(context, styleResId)
    } else {
        setTextAppearance(styleResId)
    }

    setTextColor(getColorCompat(textColor))
}

internal fun Context.getItemImageButton(menuItem: MenuObject): ImageView =
        ImageButton(this).apply {
            val paddingValue = getDimension(R.dimen.menu_item_padding)

            layoutParams = RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
            )
            isClickable = false
            isFocusable = false
            scaleType = menuItem.scaleType

            setPadding(paddingValue, paddingValue, paddingValue, paddingValue)
            setBackgroundColor(Color.TRANSPARENT)

            menuItem.apply {
                when {
                    color != 0 -> setImageDrawable(ColorDrawable(color))
                    resource != 0 -> setImageResource(resource)
                    bitmap != null -> setImageBitmap(bitmap)
                    drawable != null -> setImageDrawable(drawable)
                }
            }
        }

internal fun Context.getDivider(menuItem: MenuObject): View = View(this).apply {
    val dividerColor = if (menuItem.dividerColor == Integer.MAX_VALUE) {
        R.color.divider_color
    } else {
        menuItem.dividerColor
    }

    layoutParams = RelativeLayout.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            getDimension(R.dimen.divider_height)
    ).apply { addRule(RelativeLayout.ALIGN_PARENT_BOTTOM) }
    isClickable = true

    setBackgroundColor(getColorCompat(dividerColor))
}

internal fun Context.getImageWrapper(
        menuItem: MenuObject,
        menuItemSize: Int,
        onCLick: View.OnClickListener,
        onLongClick: View.OnLongClickListener,
        showDivider: Boolean
): RelativeLayout = RelativeLayout(this).apply {
    layoutParams = LinearLayout.LayoutParams(menuItemSize, menuItemSize)

    setOnClickListener(onCLick)
    setOnLongClickListener(onLongClick)
    addView(getItemImageButton(menuItem))
    if (showDivider) {
        addView(getDivider(menuItem))
    }

    menuItem.apply {
        when {
            bgColor != 0 -> setBackgroundColor(bgColor)
            bgDrawable != null -> background = bgDrawable
            bgResource != 0 -> setBackgroundResource(bgResource)
            else -> setBackgroundColor(getColorCompat(R.color.menu_item_background))
        }
    }
}