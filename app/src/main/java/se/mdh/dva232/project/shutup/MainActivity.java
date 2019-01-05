package se.mdh.dva232.project.shutup;

import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("LIFECYCLE_Activity", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        SharedPreferences.Editor settingsEditor = settings.edit();

        //if there is no settings put default to false
        Log.d("Check_settings", "first vibration_check, should be false: " + settings.getAll().get("vibration") );
        Log.d("Check_settings", "first extended_mode_check, should be false: " + settings.getAll().get("extended_mode") );
        Log.d("Check_settings", "first close_activation_check, should be false: " + settings.getAll().get("close_after_activation") );
        if ((!settings.contains("vibration"))|| (!settings.contains("extended_mode")) || (!settings.contains("close_after_activation")) || (!settings.contains("silent_mode_active"))) {
            Log.d("SETTINGS", "no settings detected -> default settings");
            settingsEditor.putBoolean("vibration", false);
            settingsEditor.putBoolean("extended_mode", false);
            settingsEditor.putBoolean("close_after_activation", false);
            settingsEditor.putBoolean("silent_mode_active", false);
            settingsEditor.apply();
            /*
            Log.d("Check_settings", "first vibration_check, should be false: " + settings.getAll().get("vibration") );
            Log.d("Check_settings", "first extended_mode_check, should be false: " + settings.getAll().get("extended_mode") );
            Log.d("Check_settings", "first close_activation_check, should be false: " + settings.getAll().get("close_after_activation") );
            */
        } else {
            Log.d("SETTINGS", "settings detected");
        }


        /*
         * Toolbar with app-name and menu
         */
        /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        */

        /*
         * Frame container
         */
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        /*
         * Tabs on top
         */
        TabLayout tabLayout = findViewById(R.id.tabs);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            tabLayout.setVisibility(View.GONE);
        }

        /*
         * Dots on bottom
         */
        TabLayout tabLayoutTab = findViewById(R.id.tab_layout_dot);
        tabLayoutTab.setupWithViewPager(mViewPager, true);

    }

    @Override
    protected void onStart() {
        Log.d("LIFECYCLE_Activity", "onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d("LIFECYCLE_Activity", "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("LIFECYCLE_Activity", "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("LIFECYCLE_Actvity", "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("LIFECYCLE_Activity", "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        Log.d("SAVE_Activity","onSaveInstanceState");
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d("SAVE_Activity","onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case 0: return Fragment0.newInstance();
                case 1: return Fragment1.newInstance();
                case 2: return Fragment2.newInstance();
                default: return Fragment0.newInstance();
            }
        }

        @Override
        public int getCount() { return 3; }
    }
}
