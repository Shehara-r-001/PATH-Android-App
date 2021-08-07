package com.example.path_02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.tabs.TabLayout;

public class Users_Fragments extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.users_fragments);


        tabLayout = findViewById(R.id.tablayout_users);
        viewPager = findViewById(R.id.viewpager_users);
        tabLayout.setupWithViewPager(viewPager);

        User_Page_Adapter user_page_adapter = new User_Page_Adapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        user_page_adapter.AddFrags( new AnC_Frag(), "CRAFTS");
        user_page_adapter.AddFrags( new Beauty_Frag(), "BEAUTY");
        user_page_adapter.AddFrags( new Decos_Fragment(), "DECOS");
        user_page_adapter.AddFrags( new Foods_Frag(), "FOOD");

        viewPager.setAdapter(user_page_adapter);
    }
}