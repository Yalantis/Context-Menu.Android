package com.yalantis.dropdownmenu;

import android.content.Context;
import android.content.res.TypedArray;

/**
 * Created by Kirill-Penzykov on 24.12.2014.
 */
public class Utils {

    public static int getDefaultActionBarSize(Context context){
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[] { android.R.attr.actionBarSize });
        int actionBarSize = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        return actionBarSize;
    }

}
