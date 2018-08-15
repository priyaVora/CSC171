package com.example.priya.socialmediaapp.Activities;

import android.app.ActionBar;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.example.priya.socialmediaapp.Activities.Camera.CameraActivity;
import com.example.priya.socialmediaapp.Model.tab_calls;
import com.example.priya.socialmediaapp.Model.tab_contacts;
import com.example.priya.socialmediaapp.Model.tab_chat;
import com.example.priya.socialmediaapp.Model.tab_status;
import com.example.priya.socialmediaapp.R;
import com.google.firebase.auth.FirebaseAuth;

import static java.security.AccessController.getContext;

public class MainContentTabbedActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    private Menu originalMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content_tabbed);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();




        tabLayout = findViewById(R.id.tabs);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.camera));
        tabLayout.getTabAt(0).setText("");
        tabLayout.addTab(tabLayout.newTab().setText("CHATS"));
        tabLayout.addTab(tabLayout.newTab().setText("STATUS"));
        tabLayout.addTab(tabLayout.newTab().setText("CALLS"));


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 2) {
                    if(originalMenu != null) {
                            originalMenu.clear();
                        getMenuInflater().inflate(R.menu.status_menu, originalMenu);
                    }

                } else if(position == 3) {
                    if(originalMenu != null) {
                            originalMenu.clear();

                        getMenuInflater().inflate(R.menu.calls_menu, originalMenu);
                    }
                } else if(position == 1) {
                    if(originalMenu != null) {
                            originalMenu.clear();

                        getMenuInflater().inflate(R.menu.main_menu, originalMenu);
                    }
                } else {
                    if(originalMenu != null) {
                            originalMenu.clear();
                        getMenuInflater().inflate(R.menu.main_menu, originalMenu);
                    }
                }

                tabLayout.getTabAt(position).select();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setCurrentItem(1);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getText().equals("CHATS")) {
                    mViewPager.setCurrentItem(1);
                }  else if(tab.getText().equals("STATUS")) {
                    mViewPager.setCurrentItem(2);
                } else if(tab.getText().equals("CALLS")) {
                    mViewPager.setCurrentItem(3);
                } else if(tab.getText().equals("")) {
                   // mViewPager.setCurrentItem(0);
                    startActivity(new Intent(MainContentTabbedActivity.this, MainCameraActivity.class));
                    finish();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        originalMenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
                if(item.getItemId() == R.id.action_signout) {
                mAuth.signOut();
                startActivity(new Intent(MainContentTabbedActivity.this, LoginActivity.class));
                finish();
                }

        return super.onOptionsItemSelected(item);
    }

  //deleted PlaceHoldeFragment class from here.

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    tab_contacts tab1 = new tab_contacts();
                    return tab1;
                case 1:
                    tab_chat tab2 = new tab_chat();
                    return tab2;
                case 2:
                    tab_status tab3 = new tab_status();
                    return tab3;
                case 3:
                    tab_calls tab4 = new tab_calls();
                    return tab4;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "CAMERA";
                case 1:

                    return "CHATS";
                case 2:
                    return "STATUS";
                case 3:
                    return "CALLS";
            }
            return null;
        }
    }
}
