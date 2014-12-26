package com.yalantis.dropdownmenu;

import android.graphics.drawable.Drawable;

/**
 * Created by Kirill-Penzykov on 23.12.2014.
 */
public class MenuObject {

    private Drawable mDrawable;
    private String mTitle;

    public MenuObject(Drawable drawable, String title) {
        this.mDrawable = drawable;
        this.mTitle = title;
    }

    public MenuObject(Drawable drawable) {
        this.mDrawable = drawable;
        this.mTitle = "";
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public Drawable getDrawable() {
        return mDrawable;
    }

    public void setDrawable(Drawable drawable) {
        this.mDrawable = drawable;
    }

}
