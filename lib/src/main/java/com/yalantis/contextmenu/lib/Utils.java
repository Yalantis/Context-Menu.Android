package com.yalantis.contextmenu.lib;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
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

    public static TextView getItemTextView(Context context, MenuObject menuItem, int menuItemSize,
                                           View.OnClickListener onCLick, View.OnLongClickListener onLongClick) {
        TextView itemTextView = new TextView(context);
        RelativeLayout.LayoutParams textLayoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, menuItemSize);
        itemTextView.setLayoutParams(textLayoutParams);
        itemTextView.setOnClickListener(onCLick);
        itemTextView.setOnLongClickListener(onLongClick);
        itemTextView.setText(menuItem.getTitle());
        itemTextView.setPadding(0, 0, (int) context.getResources().getDimension(R.dimen.text_right_padding), 0);
        itemTextView.setGravity(Gravity.CENTER_VERTICAL);
        int textColor = menuItem.getTextColor() == 0 ?
                android.R.color.white :
                menuItem.getTextColor();

        itemTextView.setTextColor(ContextCompat.getColor(context, textColor));

        int styleResId = menuItem.getMenuTextAppearanceStyle() > 0
                ? menuItem.getMenuTextAppearanceStyle()
                : R.style.TextView_DefaultStyle;

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
            itemTextView.setTextAppearance(context, styleResId);
        } else {
            itemTextView.setTextAppearance(styleResId);
        }

        return itemTextView;
    }

    public static ImageView getItemImageButton(Context context, MenuObject item) {
        ImageView imageView = new ImageButton(context);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        imageView.setLayoutParams(lp);
        imageView.setPadding((int) context.getResources().getDimension(R.dimen.menu_item_padding),
                (int) context.getResources().getDimension(R.dimen.menu_item_padding),
                (int) context.getResources().getDimension(R.dimen.menu_item_padding),
                (int) context.getResources().getDimension(R.dimen.menu_item_padding));
        imageView.setClickable(false);
        imageView.setFocusable(false);
        imageView.setBackgroundColor(Color.TRANSPARENT);

        if (item.getColor() != 0) {
            Drawable color = new ColorDrawable(item.getColor());
            imageView.setImageDrawable(color);
        } else if (item.getResource() != 0) {
            imageView.setImageResource(item.getResource());
        } else if (item.getBitmap() != null) {
            imageView.setImageBitmap(item.getBitmap());
        } else if (item.getDrawable() != null) {
            imageView.setImageDrawable(item.getDrawable());
        }
        imageView.setScaleType(item.getScaleType());

        return imageView;
    }

    public static View getDivider(Context context, MenuObject menuItem) {
        View dividerView = new View(context);
        RelativeLayout.LayoutParams viewLayoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, (int) context.getResources().getDimension(R.dimen.divider_height));
        viewLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        dividerView.setLayoutParams(viewLayoutParams);
        dividerView.setClickable(true);
        int dividerColor = menuItem.getDividerColor() == Integer.MAX_VALUE ?
                R.color.divider_color :
                menuItem.getDividerColor();
        dividerView.setBackgroundColor(ContextCompat.getColor(context, dividerColor));
        return dividerView;
    }

    public static RelativeLayout getImageWrapper(Context context, MenuObject menuItem, int menuItemSize,
                                                 View.OnClickListener onCLick, View.OnLongClickListener onLongClick,
                                                 boolean showDivider) {
        RelativeLayout imageWrapper = new RelativeLayout(context);
        LinearLayout.LayoutParams imageWrapperLayoutParams = new LinearLayout.LayoutParams(menuItemSize, menuItemSize);
        imageWrapper.setLayoutParams(imageWrapperLayoutParams);
        imageWrapper.setOnClickListener(onCLick);
        imageWrapper.setOnLongClickListener(onLongClick);
        imageWrapper.addView(Utils.getItemImageButton(context, menuItem));
        if (showDivider) {
            imageWrapper.addView(getDivider(context, menuItem));
        }

        if (menuItem.getBgColor() != 0) {
            imageWrapper.setBackgroundColor(menuItem.getBgColor());
        } else if (menuItem.getBgDrawable() != null) {
            if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                imageWrapper.setBackgroundDrawable(menuItem.getBgDrawable());
            } else {
                imageWrapper.setBackground(menuItem.getBgDrawable());
            }
        } else if (menuItem.getBgResource() != 0) {
            imageWrapper.setBackgroundResource(menuItem.getBgResource());
        } else {
            imageWrapper.setBackgroundColor(context.getResources().getColor(R.color.menu_item_background));
        }
        return imageWrapper;
    }

}
