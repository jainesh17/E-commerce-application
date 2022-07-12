package com.example.orignalinternship;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

public class chatActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().hide();
        tabLayout = findViewById(R.id.chat_tab);
        viewPager = findViewById(R.id.chat_viewpage);

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
               tabLayout.setupWithViewPager(viewPager);
            }
        });
        /*FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction().replace(R.id.chat_viewpage,new ChatFregment()).commit();
*/
        viewPager.setAdapter(new TabpageAdepter(getSupportFragmentManager()));
    }


    private class TabpageAdepter extends FragmentPagerAdapter {
        public TabpageAdepter(FragmentManager supportFragmentManager) {
            super(supportFragmentManager);
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            switch (position){
                case 0:
                    return "chat";

                case 1:
                    return "status";

                case 2:
                    return "call";
                default:
                    return "other";

            }

         //   return super.getPageTitle(position);
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new chatFragment();
                case 1:
                    return new StatusFragment();
                case 2:
                    return new callFragment();
                default:
                    return new chatFragment();
            }
           // return null;
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}