package com.yalantis.contextmenu.lib

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Parcel
import android.os.Parcelable
import android.widget.ImageView

open class MenuObject(var title: String = "") : Parcelable {

    var bgDrawable: Drawable? = null

    var bgColor: Int = 0
        private set

    var bgResource: Int = 0
        private set

    var drawable: Drawable? = null

    var color: Int = 0
        private set

    var bitmap: Bitmap? = null
        private set

    var resource: Int = 0
        private set

    var scaleType: ImageView.ScaleType = ImageView.ScaleType.CENTER_INSIDE
    var textColor: Int = 0
    var dividerColor: Int = Integer.MAX_VALUE
    var menuTextAppearanceStyle: Int = 0

    private constructor(parcel: Parcel) : this(parcel.readString() ?: "") {
        val bitmapBgDrawable = parcel.readParcelable<Bitmap>(Bitmap::class.java.classLoader)
        bgDrawable = if (bitmapBgDrawable == null) {
            ColorDrawable(parcel.readInt())
        } else {
            // TODO create BitmapDrawable with resources
            BitmapDrawable(bitmapBgDrawable)
        }

        bgColor = parcel.readInt()
        bgResource = parcel.readInt()

        val bitmapDrawable = parcel.readParcelable<Bitmap>(Bitmap::class.java.classLoader)
        // TODO create BitmapDrawable with resources
        bitmapDrawable?.let { drawable = BitmapDrawable(it) }

        color = parcel.readInt()
        bitmap = parcel.readParcelable(Bitmap::class.java.classLoader)
        resource = parcel.readInt()
        scaleType = ImageView.ScaleType.values()[parcel.readInt()]
        textColor = parcel.readInt()
        dividerColor = parcel.readInt()
        menuTextAppearanceStyle = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.apply {
            writeString(title)

            when (bgDrawable) {
                null -> writeParcelable(null, flags)
                is BitmapDrawable -> writeParcelable((bgDrawable as BitmapDrawable).bitmap, flags)
                is ColorDrawable -> writeInt((bgDrawable as ColorDrawable).color)
            }

            writeInt(bgColor)
            writeInt(bgResource)

            when (drawable) {
                null -> writeParcelable(null, flags)
                is BitmapDrawable -> writeParcelable((drawable as BitmapDrawable).bitmap, flags)
            }

            writeInt(color)
            writeParcelable(bitmap, flags)
            writeInt(resource)
            writeInt(scaleType.ordinal)
            writeInt(textColor)
            writeInt(dividerColor)
            writeInt(menuTextAppearanceStyle)
        }
    }

    override fun describeContents(): Int = 0

    fun setBgDrawable(drawable: ColorDrawable) {
        setBgDrawableInternal(drawable)
    }

    fun setBgDrawable(drawable: BitmapDrawable) {
        setBgDrawableInternal(drawable)
    }

    fun setBgColorValue(value: Int) {
        bgColor = value
        bgDrawable = null
        bgResource = 0
    }

    fun setBgResourceValue(value: Int) {
        bgResource = value
        bgDrawable = null
        bgColor = 0
    }

    fun setColorValue(value: Int) {
        color = value
        drawable = null
        bitmap = null
        resource = 0
    }

    fun setBitmapValue(value: Bitmap) {
        bitmap = value
        drawable = null
        color = 0
        resource = 0
    }

    fun setResourceValue(value: Int) {
        resource = value
        drawable = null
        color = 0
        bitmap = null
    }

    private fun setBgDrawableInternal(drawable: Drawable) {
        bgDrawable = drawable
        bgColor = 0
        bgResource = 0
    }

    companion object CREATOR : Parcelable.Creator<MenuObject> {
        override fun createFromParcel(parcel: Parcel): MenuObject = MenuObject(parcel)

        override fun newArray(size: Int): Array<MenuObject?> = arrayOfNulls(size)
    }
}