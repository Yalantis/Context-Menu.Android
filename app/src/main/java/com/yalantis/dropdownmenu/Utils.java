package com.yalantis.dropdownmenu;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

    public static TextView getItemTextView(Context context, String title, int actionBarSize){
        TextView itemTextView = new TextView(context);
        RelativeLayout.LayoutParams textLayoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, actionBarSize);
        itemTextView.setLayoutParams(textLayoutParams);
        itemTextView.setTextColor(context.getResources().getColor(android.R.color.white));
        itemTextView.setText(title);
        itemTextView.setPadding(0,0,(int)context.getResources().getDimension(R.dimen.text_right_padding),0);
        itemTextView.setGravity(Gravity.CENTER_VERTICAL);
        return itemTextView;
    }

    public static ImageButton getItemImageButton(Context context, int actionBarSize, Drawable drawable,View.OnClickListener onCLick){
        ImageButton imageButton = new ImageButton(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                actionBarSize, actionBarSize);
        imageButton.setLayoutParams(lp);
        imageButton.setBackgroundColor(context.getResources().getColor(android.R.color.white));
        imageButton.setPadding((int) context.getResources().getDimension(R.dimen.menu_item_padding),
                (int) context.getResources().getDimension(R.dimen.menu_item_padding),
                (int) context.getResources().getDimension(R.dimen.menu_item_padding),
                (int) context.getResources().getDimension(R.dimen.menu_item_padding));
        imageButton.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageButton.setImageDrawable(drawable);
        imageButton.setOnClickListener(onCLick);
        return imageButton;
    }

}
