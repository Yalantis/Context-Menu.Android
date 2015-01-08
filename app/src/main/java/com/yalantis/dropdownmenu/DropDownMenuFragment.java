package com.yalantis.dropdownmenu;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by Kirill-Penzykov on 23.12.2014.
 */
public class DropDownMenuFragment extends DialogFragment implements MenuAdapter.OnItemClickListener {

    private static final String ACTION_BAR_SIZE = "action_bar_size";
    private static final String MENU_OBJECTS = "menu_objects";
    private static final String ANIMATION_DELAY = "animation_delay";
    private static final String ANIMATION_DURATION = "animation_duration";

    private LinearLayout mWrapperButtons;
    private LinearLayout mWrapperText;
    private MenuAdapter mDropDownMenuAdapter;
    private ArrayList<MenuObject> mMenuObjects;
    private int mActionBarHeight;
    private ItemClickListener mItemClickListener;
    private int mAnimationDelay = 0; // delay after opening and before closing dialogfragent
    private int mAnimationDuration;

    public interface ItemClickListener {
        public void onItemClick(View clickedView, int position);
    }

    public static DropDownMenuFragment newInstance(int actionBarSize, ArrayList<MenuObject> menuObjects) {
        DropDownMenuFragment dropDownMenuFragment = new DropDownMenuFragment();
        Bundle args = new Bundle();
        args.putInt(ACTION_BAR_SIZE, actionBarSize);
        args.putParcelableArrayList(MENU_OBJECTS, menuObjects);
        dropDownMenuFragment.setArguments(args);
        return dropDownMenuFragment;
    }

    public static DropDownMenuFragment newInstance(int actionBarSize, ArrayList<MenuObject> menuObjects, int animationDelay) {
        DropDownMenuFragment dropDownMenuFragment = new DropDownMenuFragment();
        Bundle args = new Bundle();
        args.putInt(ACTION_BAR_SIZE, actionBarSize);
        args.putParcelableArrayList(MENU_OBJECTS, menuObjects);
        args.putInt(ANIMATION_DELAY, animationDelay);
        dropDownMenuFragment.setArguments(args);
        return dropDownMenuFragment;
    }

    public static DropDownMenuFragment newInstance(int actionBarSize, ArrayList<MenuObject> menuObjects, int animationDelay, int animationDuration) {
        DropDownMenuFragment dropDownMenuFragment = new DropDownMenuFragment();
        Bundle args = new Bundle();
        args.putInt(ACTION_BAR_SIZE, actionBarSize);
        args.putParcelableArrayList(MENU_OBJECTS, menuObjects);
        args.putInt(ANIMATION_DELAY, animationDelay);
        args.putInt(ANIMATION_DURATION, animationDuration);
        dropDownMenuFragment.setArguments(args);
        return dropDownMenuFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mItemClickListener = (ItemClickListener) activity;
        } catch (ClassCastException e) {
            Log.e(getClass().getName(), "Should implement ItemClickListener");
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
            mAnimationDuration = (getArguments().containsKey(ANIMATION_DELAY))?
                getArguments().getInt(ANIMATION_DELAY): MenuAdapter.ANIMATION_DURATION_MILLIS;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);
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
        mDropDownMenuAdapter = new MenuAdapter(getActivity(), mWrapperButtons, mWrapperText, mMenuObjects, mActionBarHeight, this);
        mDropDownMenuAdapter.setAnimationDuration(mAnimationDuration);
    }

    /**
     * Menu item click method
     */
    @Override
    public void onClick(View v) {
        mItemClickListener.onItemClick(v, mWrapperButtons.indexOfChild(v));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        },mAnimationDelay);
    }
}