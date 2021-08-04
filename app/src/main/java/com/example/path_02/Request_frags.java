package com.example.path_02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;

public class Request_frags extends AppCompatActivity{

    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.request_frags);

        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewpager);

        tabLayout.setupWithViewPager(viewPager);

        Page_Adapter page_adapter = new Page_Adapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        page_adapter.AddFragment(new Frag_Pending(), "PENDING");
        page_adapter.AddFragment(new Frag_Sent(), "SENT");
        page_adapter.AddFragment(new Frag_Received(), "RECEIVED");

        viewPager.setAdapter(page_adapter);
    }


}