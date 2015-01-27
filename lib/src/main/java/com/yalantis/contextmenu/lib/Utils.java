package com.yalantis.contextmenu.lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Utils {

    public static int getDefaultActionBarSize(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{android.R.attr.actionBarSize});
        int actionBarSize = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();
        return actionBarSize;
    }

    public static TextView getItemTextView(Context context, String title, int menuItemSize) {
        TextView itemTextView = new TextView(context);
        RelativeLayout.LayoutParams textLayoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, menuItemSize);
        itemTextView.setLayoutParams(textLayoutParams);
        itemTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, context.getResources().getDimension(R.dimen.menu_text_size));
        itemTextView.setTextColor(context.getResources().getColor(android.R.color.white));
        itemTextView.setText(title);
        itemTextView.setPadding(0, 0, (int) context.getResources().getDimension(R.dimen.text_right_padding), 0);
        itemTextView.setGravity(Gravity.CENTER_VERTICAL);
        return itemTextView;
    }

    public static ImageView getItemImageButton(Context context, Drawable drawable) {
        ImageView imageView = new ImageButton(context);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
        imageView.setLayoutParams(lp);
        imageView.setPadding((int) context.getResources().getDimension(R.dimen.menu_item_padding),
                (int) context.getResources().getDimension(R.dimen.menu_item_padding),
                (int) context.getResources().getDimension(R.dimen.menu_item_padding),
                (int) context.getResources().getDimension(R.dimen.menu_item_padding));
        imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
        imageView.setBackgroundColor(context.getResources().getColor(android.R.color.transparent));
        imageView.setImageDrawable(drawable);
        imageView.setClickable(false);
        imageView.setFocusable(false);
        return imageView;
    }

    public static View getDivider(Context context) {
        View dividerView = new View(context);
        RelativeLayout.LayoutParams viewLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) context.getResources().getDimension(R.dimen.divider_height));
        viewLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        dividerView.setLayoutParams(viewLayoutParams);
        dividerView.setClickable(true);
        dividerView.setBackgroundColor(context.getResources().getColor(R.color.divider_color));
        return dividerView;
    }

    public static RelativeLayout getImageWrapper(Context context, int menuItemSize, int drawableId,
                                                 View.OnClickListener onCLick, View.OnLongClickListener onLongClick) {
        RelativeLayout imageWrapper = new RelativeLayout(context);
        LinearLayout.LayoutParams imageWrapperLayoutParams = new LinearLayout.LayoutParams(menuItemSize, menuItemSize);
        imageWrapper.setLayoutParams(imageWrapperLayoutParams);
        imageWrapper.setBackgroundColor(context.getResources().getColor(R.color.menu_item_background));
        imageWrapper.setOnClickListener(onCLick);
        imageWrapper.setOnLongClickListener(onLongClick);
        imageWrapper.addView(Utils.getItemImageButton(context, drawableId > 0 ? context.getResources().getDrawable(drawableId) : null));
        imageWrapper.addView(getDivider(context));
        return imageWrapper;
    }

}
