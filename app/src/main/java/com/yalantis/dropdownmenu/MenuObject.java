package com.yalantis.dropdownmenu;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Kirill-Penzykov on 23.12.2014.
 */
public class MenuObject implements Parcelable {

    private String mTitle;
    private int mId;

    public MenuObject(int id, String title) {
        this.mId = id;
        this.mTitle = title;
    }

    public MenuObject(int id) {
        this.mId = id;
        this.mTitle = "";
    }

    private MenuObject(Parcel in) {
        mTitle = in.readString();
        mId = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mTitle);
        dest.writeInt(mId);
    }

    public static final Parcelable.Creator<MenuObject> CREATOR = new Parcelable.Creator<MenuObject>() {
        @Override
        public MenuObject createFromParcel(Parcel in) {
            return new MenuObject(in);
        }

        @Override
        public MenuObject[] newArray(int size) {
            return new MenuObject[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

//    TODO: Realize drawable constructor
//    private Drawable mDrawable;
//    public MenuObject(Drawable drawable, String title) {
//        this.mDrawable = drawable;
//        this.mTitle = title;
//    }
//
//    public MenuObject(Drawable drawable) {
//        this.mDrawable = drawable;
//        this.mTitle = "";
//    }
//    public Drawable getDrawable() {
//        return mDrawable;
//    }
//
//    public void setDrawable(Drawable drawable) {
//        this.mDrawable = drawable;
//    }

}
