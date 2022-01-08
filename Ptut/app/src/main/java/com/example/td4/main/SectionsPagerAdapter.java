package com.example.td4.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.td4.FaitsPage;
import com.example.td4.JugementPage;
import com.example.td4.PersonnagePage;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                FaitsPage faits=new FaitsPage();
                return faits;
            case 1:
                PersonnagePage personnagePage=new PersonnagePage();
                return personnagePage;
            case 2:
                JugementPage jugementPage =new JugementPage();
                return jugementPage;
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Faits";
            case 1:
                return "Personnage";
            case 2:
                return "Jugement";
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        // Montre le total de Pages.
        return 3;
    }
}