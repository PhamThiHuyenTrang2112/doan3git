package com.e.doan3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;

public class Viewpageradapter extends FragmentStatePagerAdapter {
    private ArrayList<Fragment> fragmentlist=new ArrayList<>();
    private ArrayList<String> Listtitle=new ArrayList<>();


    public Viewpageradapter(@NonNull FragmentManager fm) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
       return fragmentlist.get(position);
    }

    @Override
    public int getCount() {
        return fragmentlist.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
       return Listtitle.get(position);
    }
    public void addTab(Fragment fragment,String title){
        fragmentlist.add(fragment);
        Listtitle.add(title);
    }
}
