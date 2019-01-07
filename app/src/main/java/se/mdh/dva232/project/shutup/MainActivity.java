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

    private static Boolean debugLifecycle = false;
    private static Boolean debugSettings = false;
    SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (debugLifecycle) { Log.d("LIFECYCLE_Activity", "onCreate"); }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        SharedPreferences.Editor settingsEditor = settings.edit();

        //if there is no settings put default to false
        if ( !settings.contains("vibration") || !settings.contains("extended_mode") || !settings.contains("close_after_activation") || !settings.contains("btn_duration_1")
                || !settings.contains("btn_duration_2") || !settings.contains("btn_duration_3") || !settings.contains("btn_duration_4") || !settings.contains("btn_duration_5")
                || !settings.contains("btn_duration_6") )
        {
            if (debugSettings) { Log.d("SETTINGS", "no settings detected -> load default values"); }
            settingsEditor.putBoolean("vibration", false);
            settingsEditor.putBoolean("extended_mode", false);
            settingsEditor.putBoolean("close_after_activation", false);
            settingsEditor.putBoolean("silent_mode_active", false);
            settingsEditor.putString("btn_duration_1","0:30");
            settingsEditor.putString("btn_duration_2","1:00");
            settingsEditor.putString("btn_duration_3","1:30");
            settingsEditor.putString("btn_duration_4","2:00");
            settingsEditor.putString("btn_duration_5","2:30");
            settingsEditor.putString("btn_duration_6","3:00");
            settingsEditor.apply();
        } else {
            if (debugSettings) { Log.d("SETTINGS", "settings detected"); }
        }

        if (debugSettings) {
            Log.d("SETTINGS","vibration: " + settings.getAll().get("vibration") );
            Log.d("SETTINGS","extended_mode: " + settings.getAll().get("extended_mode") );
            Log.d("SETTINGS","close_after_activation: " + settings.getAll().get("close_after_activation") );
            Log.d("SETTINGS","silent_mode_active: " + settings.getAll().get("silent_mode_active" ));
            Log.d("SETTINGS","btn_duration_1: " + settings.getAll().get("btn_duration_1") );
        }

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
        // hide tabs if orientation is landscape
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
        if (debugLifecycle) { Log.d("LIFECYCLE_Activity", "onStart"); }
        super.onStart();
    }

    @Override
    protected void onResume() {
        if (debugLifecycle) { Log.d("LIFECYCLE_Activity", "onResume"); }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (debugLifecycle) { Log.d("LIFECYCLE_Activity", "onPause"); }
        super.onPause();
    }

    @Override
    protected void onStop() {
        if (debugLifecycle) { Log.d("LIFECYCLE_Actvity", "onStop"); }
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        if (debugLifecycle) { Log.d("LIFECYCLE_Activity", "onDestroy"); }
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        if (debugLifecycle) { Log.d("LIFECYCLE_Activity","onSaveInstanceState"); }
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (debugLifecycle) { Log.d("LIFECYCLE_Activity","onRestoreInstanceState"); }
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
