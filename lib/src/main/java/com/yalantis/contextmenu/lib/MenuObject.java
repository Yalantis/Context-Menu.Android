package com.yalantis.contextmenu.lib;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorRes;
import android.support.annotation.StyleRes;
import android.widget.ImageView;

public class MenuObject implements Parcelable {

    private String mTitle;
    // bg
    private Drawable mBgDrawable;
    private int mBgColor;
    private int mBgResource;
    // image
    private Drawable mDrawable;
    private int mColor;
    private Bitmap mBitmap;
    private int mResource;
    // image scale type
    private ImageView.ScaleType mScaleType = ImageView.ScaleType.CENTER_INSIDE;
    // text
    private int mTextColor;
    // divider
    private int mDividerColor = Integer.MAX_VALUE;

    private int mMenuTextAppearenseStyle;

    public MenuObject(String title) {
        this.mTitle = title;
    }

    public MenuObject() {
        this.mTitle = "";
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public Drawable getBgDrawable() {
        return mBgDrawable;
    }

    public void setBgDrawable(Drawable mBgDrawable) {
        this.mBgDrawable = mBgDrawable;
        mBgColor = 0;
        mBgResource = 0;
    }

    public int getBgColor() {
        return mBgColor;
    }

    public void setBgColor(int mBgColor) {
        this.mBgColor = mBgColor;
        mBgResource = 0;
        mBgDrawable = null;
    }

    public int getBgResource() {
        return mBgResource;
    }

    public void setBgResource(int mBgResource) {
        this.mBgResource = mBgResource;
        mBgColor = 0;
        mBgDrawable = null;
    }

    public int getTextColor() {
        return mTextColor;
    }

    /**
     * Use {@link #setMenuTextAppearanceStyle(int)} to set all text style params at one place
     */
    @Deprecated
    public void setTextColor(@ColorRes int mTextColor) {
        this.mTextColor = mTextColor;
    }

    public int getColor() {
        return mColor;
    }

    public void setColor(int mColor) {
        this.mColor = mColor;
        mResource = 0;
        mBitmap = null;
        mDrawable = null;
    }

    public Bitmap getBitmap() {
        return mBitmap;
    }

    public void setBitmap(Bitmap mBitmap) {
        this.mBitmap = mBitmap;
        mColor = 0;
        mResource = 0;
        mDrawable = null;
    }

    public int getResource() {
        return mResource;
    }

    public void setResource(int mResource) {
        this.mResource = mResource;
        mColor = 0;
        mBitmap = null;
        mDrawable = null;
    }

    public Drawable getDrawable() {
        return mDrawable;
    }

    public void setDrawable(Drawable mDrawable) {
        this.mDrawable = mDrawable;
        mColor = 0;
        mResource = 0;
        mBitmap = null;
    }

    @StyleRes
    public int getMenuTextAppearanceStyle() {
        return mMenuTextAppearenseStyle;
    }

    /**
     * Set style resource id, it will be used for setting text appearance of menu item title.
     * For better effect your style should extend TextView.DefaultStyle
     */
    public void setMenuTextAppearanceStyle(@StyleRes int mMenuTextAppearanceStyle) {
        this.mMenuTextAppearenseStyle = mMenuTextAppearanceStyle;
    }

    @ColorRes
    public int getDividerColor() {
        return mDividerColor;
    }

    public void setDividerColor(@ColorRes int mDividerColor) {
        this.mDividerColor = mDividerColor;
    }

    public ImageView.ScaleType getScaleType() {
        return mScaleType;
    }

    public void setScaleType(ImageView.ScaleType mScaleType) {
        this.mScaleType = mScaleType;
    }

    public static Creator<MenuObject> getCreator() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTitle);
        dest.writeParcelable(mBgDrawable == null ? null :
                ((BitmapDrawable) this.mBgDrawable).getBitmap(), flags);
        dest.writeInt(this.mBgColor);
        dest.writeInt(this.mBgResource);
        dest.writeParcelable(mDrawable == null ? null :
                ((BitmapDrawable) this.mDrawable).getBitmap(), flags);
        dest.writeInt(this.mColor);
        dest.writeParcelable(this.mBitmap, 0);
        dest.writeInt(this.mResource);
        dest.writeInt(this.mScaleType == null ? -1 : this.mScaleType.ordinal());
        dest.writeInt(this.mTextColor);
        dest.writeInt(this.mDividerColor);
        dest.writeInt(this.mMenuTextAppearenseStyle);
    }

    private MenuObject(Parcel in) {
        this.mTitle = in.readString();
        Bitmap bitmapBgDrawable = in.readParcelable(Bitmap.class.getClassLoader());
        if (bitmapBgDrawable != null) {
            this.mBgDrawable = new BitmapDrawable(bitmapBgDrawable);
        }
        this.mBgColor = in.readInt();
        this.mBgResource = in.readInt();
        Bitmap bitmapDrawable = in.readParcelable(Bitmap.class.getClassLoader());
        if (bitmapDrawable != null) {
            this.mDrawable = new BitmapDrawable(bitmapDrawable);
        }
        this.mColor = in.readInt();
        this.mBitmap = in.readParcelable(Bitmap.class.getClassLoader());
        this.mResource = in.readInt();
        int tmpMScaleType = in.readInt();
        this.mScaleType = tmpMScaleType == -1 ? null : ImageView.ScaleType.values()[tmpMScaleType];
        this.mTextColor = in.readInt();
        this.mDividerColor = in.readInt();
        this.mMenuTextAppearenseStyle = in.readInt();
    }

    public static final Creator<MenuObject> CREATOR = new Creator<MenuObject>() {
        public MenuObject createFromParcel(Parcel source) {
            return new MenuObject(source);
        }

        public MenuObject[] newArray(int size) {
            return new MenuObject[size];
        }
    };
}
