package com.yalantis.contextmenu.lib.interfaces

import android.view.View

/**
 * Menu item long click listener
 */
interface OnMenuItemLongClickListener {

    fun onMenuItemLongClick(clickedView: View, position: Int)
}