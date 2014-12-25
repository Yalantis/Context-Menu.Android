package com.yalantis.dropdownmenu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kirill-Penzykov on 23.12.2014.
 */
public class DropDownMenuFragment extends Fragment implements MenuAdapter.OnItemClickListener {

    private LinearLayout mWrapperButtons;
    private LinearLayout mWrapperText;
    private Button mButtonMenu;

    private MenuAdapter mDropDownMenuAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        initViews(rootView);
        initDropDownMenuAdapter();
        setOnClickListeners();
        return rootView;
    }

    private void initViews(View view) {
        mButtonMenu = (Button) view.findViewById(R.id.button_menu);
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

        mDropDownMenuAdapter = new MenuAdapter(getActivity(), mWrapperButtons, mWrapperText, menuObjects, this);
    }

    private void setOnClickListeners() {
        mButtonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDropDownMenuAdapter.menuToggle();
            }
        });
    }


    @Override
    public void onClick(View v) {
        Toast.makeText(getActivity(), "WoooHaa", Toast.LENGTH_SHORT).show();
    }
}