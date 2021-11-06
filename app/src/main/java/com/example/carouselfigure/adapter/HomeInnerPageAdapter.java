package com.example.carouselfigure.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.carouselfigure.fragment.home;

import java.util.List;

public class HomeInnerPageAdapter extends FragmentPagerAdapter {

List <Fragment> fragmentList;
List <String> tabTitles;
    public static Fragment instantFragment;
    public Fragment currentFragment;
    private View mCurrentView;
    public HomeInnerPageAdapter(FragmentManager fm,List<Fragment> fragmentList,List <String> tabTitles) {
        super(fm);
        this.fragmentList = fragmentList;
        this.tabTitles = tabTitles;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 1) {
            return home.classificationPaper;
        } else if (position == 2) {
            return home.finePaper;
        } else if (position == 3) {
            return home.fleaMarketPaper;
        }
        return home.mainPaper;


    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {

        return tabTitles.get(position);
    }

    @Override
    public void setPrimaryItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        this.currentFragment= (Fragment) object;
        super.setPrimaryItem(container, position, object);
    }


    public Fragment getInstantFragment(){
        return instantFragment;
    }

}

