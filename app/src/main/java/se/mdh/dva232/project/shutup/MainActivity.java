package se.mdh.dva232.project.shutup;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("LIFECYCLE Actvity", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        SharedPreferences.Editor editor = settings.edit();

        //SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        //SharedPreferences.Editor editor = settings.edit();


        //put false by default to all setting values
        String vibration_check = settings.getString("vibration","False");
        editor.commit();
        Log.d("Check_settings", "first check, should be false: " +vibration_check);

        //editor.putString("vibration", "True");
        //editor.commit();
        //Log.d("Check_settings", "last check after true: " + vibration_check);

        //vibration_check = settings.getString("vibration","default");
        //editor.commit();
        //Log.d("Check_settings", vibration_check);



        //String extended_mode_check = settings.getString("extended_mode","");
        //String close_activation_check = settings.getString("close_after_activation","False");











        /*if( !settings.contains("vibration") || !settings.contains("extended_mode") || !settings.contains("close") ) {
            
            // first start => initial values
            Log.d("Check_settings","initiallizing values");
            SharedPreferences.Editor editor = settings.edit();
            editor.putBoolean("vibration", false);
            editor.putBoolean("extended_mode", false);
            editor.putBoolean("close", false);
            Log.d("Check_settings","");

            editor.commit();
        }*/



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

        /*
         * Dots on bottom
         */
        TabLayout tabLayoutTab = findViewById(R.id.tab_layout_dot);
        tabLayoutTab.setupWithViewPager(mViewPager, true);

    }

    @Override
    protected void onStart() {
        Log.d("LIFECYCLE Actvity", "onStart");
        super.onStart();
    }

    @Override
    protected void onResume() {
        Log.d("LIFECYCLE Actvity", "onResume");
        super.onResume();
    }

    @Override
    protected void onPause() {
        Log.d("LIFECYCLE Actvity", "onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("LIFECYCLE Actvity", "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d("LIFECYCLE Actvity", "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        Log.d("SAVE Actvity","onSaveInstanceState");
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d("SAVE Actvity","onRestoreInstanceState");
        super.onRestoreInstanceState(savedInstanceState);
    }

    /*
     * Menu in the toolbar
     */
    /*
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */

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
