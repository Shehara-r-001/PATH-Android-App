package com.example.path_02;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class User_Page_Adapter extends FragmentPagerAdapter {

    private ArrayList<Fragment> arrayListFrags = new ArrayList<>();
    private final ArrayList<String> fragsTitles = new ArrayList<>();

    public User_Page_Adapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return arrayListFrags.get(position);
    }

    @Override
    public int getCount() {
        return arrayListFrags.size();
    }

    public void AddFrags ( Fragment frag, String topic){

        arrayListFrags.add(frag);
        fragsTitles.add(topic);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position){
        return fragsTitles.get(position);
    }
}
