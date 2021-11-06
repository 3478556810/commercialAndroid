package com.example.carouselfigure.util;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.carouselfigure.R;

public class SFInnerHomeUtil {
    public static Fragment mTempFragment;

    public static void switchFragment(Fragment fragment, FragmentActivity activity) {
        if (fragment != mTempFragment) {
            if (!fragment.isAdded()) {
                activity.getSupportFragmentManager().beginTransaction().hide(mTempFragment)
                        .add(R.id.homeInnerContainer, fragment).commit();
            } else {
                activity.getSupportFragmentManager().beginTransaction().hide(mTempFragment)
                        .show(fragment).commit();
            }
            mTempFragment = fragment;
        }
    }
}
