package com.yalantis.dropdownmenu;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kirill-Penzykov on 23.12.2014.
 */
public class DropDownMenuFragment extends DialogFragment implements MenuAdapter.OnItemClickListener {

    private static final String ACTION_BAR_SIZE = "action_bar_size";

    private LinearLayout mWrapperButtons;
    private LinearLayout mWrapperText;
    private MenuAdapter mDropDownMenuAdapter;
    private int mActionBarHeight;

    public static DropDownMenuFragment newInstance(int actionBarSize) {
        DropDownMenuFragment dropDownMenuFragment = new DropDownMenuFragment();
        Bundle args = new Bundle();
        args.putInt(ACTION_BAR_SIZE, actionBarSize);
        dropDownMenuFragment.setArguments(args);
        return dropDownMenuFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, R.style.MenuFragmentStyle);
        if (getArguments() != null) {
            mActionBarHeight = getArguments().getInt(ACTION_BAR_SIZE);
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
        List<MenuObject> menuObjects = new ArrayList<>();
        menuObjects.add(new MenuObject(getResources().getDrawable(R.drawable.icn_close)));
        menuObjects.add(new MenuObject(getResources().getDrawable(R.drawable.icn_1), "Send message"));
        menuObjects.add(new MenuObject(getResources().getDrawable(R.drawable.icn_2), "Like profile"));
        menuObjects.add(new MenuObject(getResources().getDrawable(R.drawable.icn_3), "Add to friends"));
        menuObjects.add(new MenuObject(getResources().getDrawable(R.drawable.icn_4), "Add to favorites"));
        menuObjects.add(new MenuObject(getResources().getDrawable(R.drawable.icn_5), "Block user"));

        mDropDownMenuAdapter = new MenuAdapter(getActivity(), mWrapperButtons, mWrapperText, menuObjects, mActionBarHeight, this);
    }

    @Override
    public void onClick(View v) {
        dismiss();
    }
}