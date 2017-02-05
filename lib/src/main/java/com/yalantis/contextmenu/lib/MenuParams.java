package com.yalantis.contextmenu.lib;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;


public class MenuParams implements Parcelable {

    private int mActionBarSize = 0;
    private List<MenuObject> mMenuObjects;
    /**
     * Delay after opening and before closing {@link com.yalantis.contextmenu.lib.ContextMenuDialogFragment}
     */
    private int mAnimationDelay = 0;
    private int mShowAnimationDuration = MenuAdapter.ANIMATION_DURATION_MILLIS;
    private int mHideAnimationDuration = MenuAdapter.ANIMATION_DURATION_MILLIS;
    private boolean isFitsSystemWindow = false;
    private boolean isClipToPadding = true;
    /**
     * If option menu can be closed on touch to non-button area
     */
    private boolean isClosableOutside = false;
    /**
     * If click on text works same as click at icon
     */
    private boolean isTextClickable = false;

    public void setActionBarSize(int mActionBarSize) {
        this.mActionBarSize = mActionBarSize;
    }

    public void setMenuObjects(List<MenuObject> mMenuObjects) {
        this.mMenuObjects = mMenuObjects;
    }

    public void setAnimationDelay(int mAnimationDelay) {
        this.mAnimationDelay = mAnimationDelay;
    }

    public void setShowAnimationDuration(int showAnimationDuration) {
        mShowAnimationDuration = showAnimationDuration;
    }

    public void setHideAnimationDuration(int hideAnimationDuration) {
        mHideAnimationDuration = hideAnimationDuration;
    }

    public void setFitsSystemWindow(boolean mFitsSystemWindow) {
        this.isFitsSystemWindow = mFitsSystemWindow;
    }

    public void setClipToPadding(boolean mClipToPadding) {
        this.isClipToPadding = mClipToPadding;
    }

    /**
     * Set option menu can be closed on touch to non-button area
     * @param isClosableOutside true if can
     */
    public void setClosableOutside(boolean isClosableOutside) {
        this.isClosableOutside = isClosableOutside;
    }

    public int getActionBarSize() {
        return mActionBarSize;
    }

    public List<MenuObject> getMenuObjects() {
        return mMenuObjects;
    }

    public int getAnimationDelay() {
        return mAnimationDelay;
    }

    public int getShowAnimationDuration() {
        return mShowAnimationDuration;
    }

    public int getHideAnimationDuration() {
        return mHideAnimationDuration;
    }

    public boolean isFitsSystemWindow() {
        return isFitsSystemWindow;
    }

    public boolean isClipToPadding() {
        return isClipToPadding;
    }

    public boolean isClosableOutside() {
        return isClosableOutside;
    }

    public boolean isTextClickable() {
        return isTextClickable;
    }

    public void setTextClickable(boolean textClickable) {
        isTextClickable = textClickable;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mActionBarSize);
        dest.writeTypedList(mMenuObjects);
        dest.writeInt(this.mAnimationDelay);
        dest.writeInt(this.mShowAnimationDuration);
        dest.writeInt(this.mHideAnimationDuration);
        dest.writeByte(isFitsSystemWindow ? (byte) 1 : (byte) 0);
        dest.writeByte(isClipToPadding ? (byte) 1 : (byte) 0);
        dest.writeByte(isClosableOutside ? (byte) 1 : (byte) 0);
        dest.writeByte(isTextClickable ? (byte) 1 : (byte) 0);
    }

    public MenuParams() {
    }

    private MenuParams(Parcel in) {
        this.mActionBarSize = in.readInt();
        in.readTypedList(mMenuObjects, MenuObject.CREATOR);
        this.mAnimationDelay = in.readInt();
        this.mShowAnimationDuration = in.readInt();
        this.mHideAnimationDuration = in.readInt();
        this.isFitsSystemWindow = in.readByte() != 0;
        this.isClipToPadding = in.readByte() != 0;
        this.isClosableOutside = in.readByte() != 0;
        this.isTextClickable = in.readByte() != 0;
    }

    public static final Parcelable.Creator<MenuParams> CREATOR = new Parcelable.Creator<MenuParams>() {
        public MenuParams createFromParcel(Parcel source) {
            return new MenuParams(source);
        }

        public MenuParams[] newArray(int size) {
            return new MenuParams[size];
        }
    };
}
