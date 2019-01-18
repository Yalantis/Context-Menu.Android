package com.yalantis.contextmenu.lib

import android.os.Parcel
import android.os.Parcelable

/**
 * @property [animationDelay]
 * Delay after opening and before closing.
 * @see ContextMenuDialogFragment
 *
 * @property [isClosableOutside]
 * If option menu can be closed on touch to non-button area.
 *
 * @property [gravity]
 * You can change the side. By default - MenuGravity.END
 * @see MenuGravity
 */
data class MenuParams(
        var actionBarSize: Int = 0,
        var menuObjects: List<MenuObject> = listOf(),
        var animationDelay: Int = 0,
        var animationDuration: Int = MenuAdapter.ANIMATION_DURATION_MILLIS.toInt(),
        var isFitsSystemWindow: Boolean = false,
        var isClipToPadding: Boolean = true,
        var isClosableOutside: Boolean = false,
        var gravity: MenuGravity = MenuGravity.END
) : Parcelable {

    private constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.createTypedArrayList(MenuObject.CREATOR) ?: listOf(),
            parcel.readInt(),
            parcel.readInt(),
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte(),
            parcel.readSerializable() as? MenuGravity ?: MenuGravity.END
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.apply {
            writeInt(actionBarSize)
            writeTypedList(menuObjects)
            writeInt(animationDelay)
            writeInt(animationDuration)
            writeByte(if (isFitsSystemWindow) 1 else 0)
            writeByte(if (isClipToPadding) 1 else 0)
            writeByte(if (isClosableOutside) 1 else 0)
            writeSerializable(gravity)
        }
    }

    override fun describeContents(): Int = 0

    companion object CREATOR : Parcelable.Creator<MenuParams> {
        override fun createFromParcel(parcel: Parcel): MenuParams = MenuParams(parcel)

        override fun newArray(size: Int): Array<MenuParams?> = arrayOfNulls(size)
    }
}