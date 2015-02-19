package com.yalantis.contextmenu.lib;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.yalantis.contextmenu.lib.interfaces.OnItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnItemLongClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemClickListener;
import com.yalantis.contextmenu.lib.interfaces.OnMenuItemLongClickListener;

import java.util.ArrayList;
import java.util.List;

public class ContextMenuDialogFragment extends DialogFragment implements OnItemClickListener, OnItemLongClickListener {

    public static final String TAG = ContextMenuDialogFragment.class.getSimpleName();
    private static final String ACTION_BAR_SIZE = "action_bar_size";
    private static final String MENU_OBJECTS = "menu_objects";
    private static final String ANIMATION_DELAY = "animation_delay";
    private static final String ANIMATION_DURATION = "animation_duration";
    private static final String FITS_SYSTEM_WINDOW = "fits_system_window";
    private static final String CLIP_TO_PADDING = "clip_to_padding";

    private LinearLayout mWrapperButtons;
    private LinearLayout mWrapperText;
    private MenuAdapter mDropDownMenuAdapter;
    private ArrayList<MenuObject> mMenuObjects;
    private int mActionBarHeight;
    private OnMenuItemClickListener mItemClickListener;
    private OnMenuItemLongClickListener mItemLongClickListener;
    /**
     * Delay after opening and before closing {@link com.yalantis.contextmenu.lib.ContextMenuDialogFragment}
     */
    private int mAnimationDelay = 0;
    private int mAnimationDuration;

    public static ContextMenuDialogFragment newInstance(int actionBarSize, List<MenuObject> menuObjects) {
        ContextMenuDialogFragment contextMenuDialogFragment = new ContextMenuDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ACTION_BAR_SIZE, actionBarSize);
        args.putParcelableArrayList(MENU_OBJECTS, new ArrayList<>(menuObjects));
        contextMenuDialogFragment.setArguments(args);
        return contextMenuDialogFragment;
    }

    public static ContextMenuDialogFragment newInstance(int actionBarSize, List<MenuObject> menuObjects, int animationDelay) {
        ContextMenuDialogFragment contextMenuDialogFragment = new ContextMenuDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ACTION_BAR_SIZE, actionBarSize);
        args.putParcelableArrayList(MENU_OBJECTS, new ArrayList<>(menuObjects));
        args.putInt(ANIMATION_DELAY, animationDelay);
        contextMenuDialogFragment.setArguments(args);
        return contextMenuDialogFragment;
    }

    public static ContextMenuDialogFragment newInstance(int actionBarSize, List<MenuObject> menuObjects, int animationDelay, int animationDuration) {
        ContextMenuDialogFragment contextMenuDialogFragment = new ContextMenuDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ACTION_BAR_SIZE, actionBarSize);
        args.putParcelableArrayList(MENU_OBJECTS, new ArrayList<>(menuObjects));
        args.putInt(ANIMATION_DELAY, animationDelay);
        args.putInt(ANIMATION_DURATION, animationDuration);
        contextMenuDialogFragment.setArguments(args);
        return contextMenuDialogFragment;
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    public static ContextMenuDialogFragment newInstance(int actionBarSize, List<MenuObject> menuObjects,
                                                        int animationDelay, int animationDuration,
                                                        boolean fitsSystemWindow, boolean clipToPadding) {
        ContextMenuDialogFragment contextMenuDialogFragment = new ContextMenuDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ACTION_BAR_SIZE, actionBarSize);
        args.putParcelableArrayList(MENU_OBJECTS, new ArrayList<>(menuObjects));
        args.putInt(ANIMATION_DELAY, animationDelay);
        args.putInt(ANIMATION_DURATION, animationDuration);
        args.putBoolean(FITS_SYSTEM_WINDOW, fitsSystemWindow);
        args.putBoolean(CLIP_TO_PADDING, clipToPadding);
        contextMenuDialogFragment.setArguments(args);
        return contextMenuDialogFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mItemClickListener = (OnMenuItemClickListener) activity;
        } catch (ClassCastException e) {
            Log.e(TAG, activity.getClass().getSimpleName() +
                    " should implement " + OnMenuItemClickListener.class.getSimpleName());
        }
        try {
            mItemLongClickListener = (OnMenuItemLongClickListener) activity;
        } catch (ClassCastException e) {
            Log.e(TAG, activity.getClass().getSimpleName() +
                    " should implement " + OnMenuItemLongClickListener.class.getSimpleName());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.MenuFragmentStyle);
        if (getArguments() != null) {
            mActionBarHeight = getArguments().getInt(ACTION_BAR_SIZE);
            mMenuObjects = getArguments().getParcelableArrayList(MENU_OBJECTS);
            if(getArguments().containsKey(ANIMATION_DELAY)){
                mAnimationDelay = getArguments().getInt(ANIMATION_DELAY);
            }
            mAnimationDuration = (getArguments().containsKey(ANIMATION_DURATION))?
                getArguments().getInt(ANIMATION_DURATION): MenuAdapter.ANIMATION_DURATION_MILLIS;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);

        if (getArguments().containsKey(FITS_SYSTEM_WINDOW)) {
            rootView.setFitsSystemWindows(getArguments().getBoolean(FITS_SYSTEM_WINDOW));
        }
        if (getArguments().containsKey(CLIP_TO_PADDING)) {
            ((ViewGroup) rootView).setClipToPadding(getArguments().getBoolean(CLIP_TO_PADDING));
        }

        initViews(rootView);
        getDialog().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        initDropDownMenuAdapter();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mDropDownMenuAdapter.menuToggle();
            }
        },mAnimationDelay);
        return rootView;
    }

    private void initViews(View view) {
        mWrapperButtons = (LinearLayout) view.findViewById(R.id.wrapper_buttons);
        mWrapperText = (LinearLayout) view.findViewById(R.id.wrapper_text);
    }

    private void initDropDownMenuAdapter() {
        mDropDownMenuAdapter = new MenuAdapter(getActivity(), mWrapperButtons, mWrapperText, mMenuObjects, mActionBarHeight);
        mDropDownMenuAdapter.setOnItemClickListener(this);
        mDropDownMenuAdapter.setOnItemLongClickListener(this);
        mDropDownMenuAdapter.setAnimationDuration(mAnimationDuration);
    }

    private void close() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        },mAnimationDelay);
    }

    /**
     * Menu item click method
     */
    @Override
    public void onClick(View v) {
        if (mItemClickListener != null) {
            mItemClickListener.onMenuItemClick(v, mWrapperButtons.indexOfChild(v));
        }
        close();
    }

    @Override
    public void onLongClick(View v) {
        if (mItemLongClickListener != null) {
            mItemLongClickListener.onMenuItemLongClick(v, mWrapperButtons.indexOfChild(v));
        }
        close();
    }
}