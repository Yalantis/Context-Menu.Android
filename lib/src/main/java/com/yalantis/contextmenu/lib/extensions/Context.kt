package com.yalantis.contextmenu.lib.extensions

import android.content.Context
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.v4.content.ContextCompat

fun Context.getColorCompat(@ColorRes color: Int) = ContextCompat.getColor(this, color)

fun Context.getDimension(@DimenRes dimen: Int) = resources.getDimension(dimen).toInt()