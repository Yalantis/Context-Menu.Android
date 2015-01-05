package com.yalantis.dropdownmenu;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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

    private LinearLayout mWrapperButtons;
    private LinearLayout mWrapperText;
    private MenuAdapter mDropDownMenuAdapter;
    private ArrayList<MenuObject> mMenuObjects;
    private int mActionBarHeight;

    public static DropDownMenuFragment newInstance(int actionBarSize, ArrayList<MenuObject> menuObjects) {
        DropDownMenuFragment dropDownMenuFragment = new DropDownMenuFragment();
        Bundle args = new Bundle();
        args.putInt(ACTION_BAR_SIZE, actionBarSize);
        args.putParcelableArrayList(MENU_OBJECTS, menuObjects);
        dropDownMenuFragment.setArguments(args);
        return dropDownMenuFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.MenuFragmentStyle);
        if (getArguments() != null) {
            mActionBarHeight = getArguments().getInt(ACTION_BAR_SIZE);
            mMenuObjects = getArguments().getParcelableArrayList(MENU_OBJECTS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_menu, container, false);
        initViews(rootView);
        getDialog().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        initDropDownMenuAdapter();
        mDropDownMenuAdapter.menuToggle();
        return rootView;
    }

    private void initViews(View view) {
        mWrapperButtons = (LinearLayout) view.findViewById(R.id.wrapper_buttons);
        mWrapperText = (LinearLayout) view.findViewById(R.id.wrapper_text);
    }

    private void initDropDownMenuAdapter() {
        mDropDownMenuAdapter = new MenuAdapter(getActivity(), mWrapperButtons, mWrapperText, mMenuObjects, mActionBarHeight, this);
    }

    /**
    *  Menu item click method
    */
    @Override
    public void onClick(View v) {
        dismiss();
    }
}