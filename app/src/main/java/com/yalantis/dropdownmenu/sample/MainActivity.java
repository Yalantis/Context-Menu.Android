package com.yalantis.dropdownmenu.sample;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.yalantis.dropdownmenu.R;
import com.yalantis.dropdownmenu.lib.DropDownMenuFragment;
import com.yalantis.dropdownmenu.lib.MenuObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ActionBarActivity implements DropDownMenuFragment.ItemClickListener {

    private FragmentManager fragmentManager;
    private DialogFragment mMenuDialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        initToolbar();
        mMenuDialogFragment = DropDownMenuFragment.newInstance((int) getResources().getDimension(R.dimen.tool_bar_height), getMenuObjects());
        addFragment(new MainFragment(), true, R.id.container);
    }

    private List<MenuObject> getMenuObjects() {
        List<MenuObject> menuObjects = new ArrayList<>();
        menuObjects.add(new MenuObject(R.drawable.icn_close));
        menuObjects.add(new MenuObject(R.drawable.icn_1, "Send message"));
        menuObjects.add(new MenuObject(R.drawable.icn_2, "Like profile"));
        menuObjects.add(new MenuObject(R.drawable.icn_3, "Add to friends"));
        menuObjects.add(new MenuObject(R.drawable.icn_4, "Add to favorites"));
        menuObjects.add(new MenuObject(R.drawable.icn_5, "Block user"));
        return menuObjects;
    }

    private void initToolbar() {
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView mToolBarTextView = (TextView) findViewById(R.id.text_view_toolbar_title);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        mToolbar.setNavigationIcon(R.drawable.btn_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        mToolBarTextView.setText("Samantha");
    }

    protected void addFragment(Fragment fragment, boolean addToBackStack, int containerId) {
        invalidateOptionsMenu();
        String backStackName = fragment.getClass().getName();
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStackName, 0);
        if (!fragmentPopped) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(containerId, fragment, backStackName)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            if (addToBackStack)
                transaction.addToBackStack(backStackName);
            transaction.commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.context_menu:
                mMenuDialogFragment.show(fragmentManager, "DropDownMenuFragment");
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (fragmentManager.getBackStackEntryCount() == 1) {
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onItemClick(View clickedView, int position) {
        Toast.makeText(this, "Position: " + position, Toast.LENGTH_SHORT).show();
    }
}
