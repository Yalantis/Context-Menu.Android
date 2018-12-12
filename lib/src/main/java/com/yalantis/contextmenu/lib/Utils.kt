package com.yalantis.contextmenu.lib

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.yalantis.contextmenu.lib.extensions.getColorCompat
import com.yalantis.contextmenu.lib.extensions.getDimension

object Utils {

    @JvmStatic
    fun getDefaultActionBarSize(context: Context): Int {
        val styledAttrs = context.theme
                .obtainStyledAttributes(intArrayOf(android.R.attr.actionBarSize))
        val actionBarSize = styledAttrs.getDimension(0, 0f).toInt()
        styledAttrs.recycle()
        return actionBarSize
    }

    @JvmStatic
    fun getItemTextView(
            context: Context,
            menuItem: MenuObject,
            menuItemSize: Int,
            onCLick: View.OnClickListener,
            onLongClick: View.OnLongClickListener
    ): TextView = TextView(context).apply {
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
        setPadding(0, 0, context.getDimension(R.dimen.text_right_padding), 0)
        setTextColor(context.getColorCompat(textColor))

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            setTextAppearance(context, styleResId)
        } else {
            setTextAppearance(styleResId)
        }
    }

    @JvmStatic
    fun getItemImageButton(context: Context, menuItem: MenuObject): ImageView =
            ImageButton(context).apply {
                val paddingValue = context.getDimension(R.dimen.menu_item_padding)

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

    @JvmStatic
    fun getDivider(context: Context, menuItem: MenuObject): View = View(context).apply {
        val dividerColor = if (menuItem.dividerColor == Integer.MAX_VALUE) {
            R.color.divider_color
        } else {
            menuItem.dividerColor
        }

        layoutParams = RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                context.getDimension(R.dimen.divider_height)
        ).apply { addRule(RelativeLayout.ALIGN_PARENT_BOTTOM) }
        isClickable = true

        setBackgroundColor(context.getColorCompat(dividerColor))
    }

    @JvmStatic
    fun getImageWrapper(
            context: Context,
            menuItem: MenuObject,
            menuItemSize: Int,
            onCLick: View.OnClickListener,
            onLongClick: View.OnLongClickListener,
            showDivider: Boolean
    ): RelativeLayout = RelativeLayout(context).apply {
        layoutParams = LinearLayout.LayoutParams(menuItemSize, menuItemSize)

        setOnClickListener(onCLick)
        setOnLongClickListener(onLongClick)
        addView(getItemImageButton(context, menuItem))
        if (showDivider) {
            addView(getDivider(context, menuItem))
        }

        menuItem.apply {
            when {
                bgColorInternal != -1 -> setBackgroundColor(bgColor)
                bgDrawable != null -> {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        setBackgroundDrawable(bgDrawable)
                    } else {
                        background = bgDrawable
                    }
                }
                bgResource != 0 -> setBackgroundResource(bgResource)
                else -> setBackgroundColor(context.getColorCompat(R.color.menu_item_background))

            }
        }
    }
}