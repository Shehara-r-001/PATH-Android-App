package com.example.path_02;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class Page_Adapter extends FragmentStatePagerAdapter {

    int tab;

    public Page_Adapter(@NonNull FragmentManager fm, int tab) {
        super(fm);
        this.tab = tab;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 1 :
                Frag_pending frag_pending = new Frag_pending();
                return frag_pending;

                case 2 :
                Frag_Sent frag_sent = new Frag_Sent();
                return frag_sent;

                case 3 :
                Frag_Received frag_received = new Frag_Received();
                return frag_received;

            default: return null;
        }

    }

    @Override
    public int getCount() {
        return tab;
    }
}
