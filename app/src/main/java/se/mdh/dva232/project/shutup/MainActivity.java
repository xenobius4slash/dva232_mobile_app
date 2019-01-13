package se.mdh.dva232.project.shutup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.media.Image;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {

    private static Boolean debugLifecycle = false;
    SectionsPagerAdapter mSectionsPagerAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (debugLifecycle) { Log.d("LIFECYCLE_Activity", "onCreate"); }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Boolean debugSettings = true;

        SharedPreferences settings = getSharedPreferences("UserInfo", 0);
        SharedPreferences.Editor settingsEditor = settings.edit();
        //ImageView silent_image = findViewById(R.id.silent_image);
        //ImageView normal_image = findViewById(R.id.normal_image);

        //shows the image based on the actual sound mode
        //selectAudioModeForImage(silent_image,normal_image);


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
            settingsEditor.putString("semaphore_async_task", null);
            settingsEditor.putBoolean("kill_all_async_tasks", true);
            settingsEditor.putString("btn_duration_1", getString(R.string.btn_0_duration_1) );
            settingsEditor.putString("btn_duration_2", getString(R.string.btn_0_duration_2));
            settingsEditor.putString("btn_duration_3", getString(R.string.btn_0_duration_3));
            settingsEditor.putString("btn_duration_4", getString(R.string.btn_0_duration_4));
            settingsEditor.putString("btn_duration_5", getString(R.string.btn_0_duration_5));
            settingsEditor.putString("btn_duration_6", getString(R.string.btn_0_duration_6));
            settingsEditor.apply();
            showStartDialog();      //show starting dialog

        } else {
            if (debugSettings) { Log.d("SETTINGS", "settings detected"); }
        }

        if (debugSettings) {
            Log.d("SETTINGS","vibration: " + settings.getAll().get("vibration") );
            Log.d("SETTINGS","extended_mode: " + settings.getAll().get("extended_mode") );
            Log.d("SETTINGS","close_after_activation: " + settings.getAll().get("close_after_activation") );
            Log.d("SETTINGS","silent_mode_active: " + settings.getAll().get("silent_mode_active" ));
            Log.d("SETTINGS","semaphore_async_task: " + settings.getAll().get("semaphore_async_task" ));
            Log.d("SETTINGS","kill_all_async_tasks: " + settings.getAll().get("kill_all_async_tasks" ));
            Log.d("SETTINGS","btn_duration_1: " + settings.getAll().get("btn_duration_1") );
            Log.d("SETTINGS","btn_duration_2: " + settings.getAll().get("btn_duration_2") );
            Log.d("SETTINGS","btn_duration_3: " + settings.getAll().get("btn_duration_3") );
            Log.d("SETTINGS","btn_duration_4: " + settings.getAll().get("btn_duration_4") );
            Log.d("SETTINGS","btn_duration_5: " + settings.getAll().get("btn_duration_5") );
            Log.d("SETTINGS","btn_duration_6: " + settings.getAll().get("btn_duration_6") );
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


        /*
         * Initial task for cleaning the XML file and start timer, if required
         */
        EventController EC = new EventController( getBaseContext() );
        EC.initTask();
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

    //show a starting dialog every time the user starts the application for the first time
    private void showStartDialog()
    {
        new AlertDialog.Builder(this)
                .setTitle("one time dialog")
                .setMessage("This text only appear once at start of application")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();   //close the dialog


                    }
                })
                .create().show();
        //other code...

    }

    /*private void selectAudioModeForImage(ImageView silent, ImageView normal)
    {
        AudioManager AM = (AudioManager) getBaseContext().getSystemService(Context.AUDIO_SERVICE);
        //EventController EC = new EventController(getBaseContext());

        int current_mode = AM.getRingerMode();
        switch(current_mode)
        {
            case 0: Log.d("checking_images", "silent  "+current_mode);
                //need to initialize the image resource
                //silent.setImageResource(0);
                //set image
                silent.setImageResource(R.drawable.silent);
                break;
            case 1: Log.d("checking_images", "vibrate  "+current_mode);
                break;
            case 2: Log.d("checking_images", "normal  "+current_mode);
                //need to initiallize the image resource
                normal.setImageResource(0);
                //set image
                normal.setImageResource(R.drawable.normal);
                break;
            default: Log.d("checking_images", "default  "+current_mode);
                break;

        }

    }*/



}
