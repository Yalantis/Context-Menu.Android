package com.yalantis.contextmenu.sample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yalantis.contextmenu.R;


public class ActuFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View actuView = inflater.inflate(R.layout.fragment_actu, container, false);
        return actuView;
    }
}