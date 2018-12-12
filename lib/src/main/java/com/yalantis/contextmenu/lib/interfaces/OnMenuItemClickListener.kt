package com.yalantis.contextmenu.lib.interfaces

import android.view.View

/**
 * Menu item click listener
 */
interface OnMenuItemClickListener {

    fun onMenuItemClick(clickedView: View, position: Int)
}