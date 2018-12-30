package se.mdh.dva232.project.shutup;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("LIFECYCLE Actvity", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        XmlController xmlC = new XmlController( getBaseContext() );

        String id1 = "2018-12-20_1200";
        String dateStart1 = "2018-12-20";
        String timeStart1 = "12:00";
        String dateEnd1 = "2018-12-20";
        String timeEnd1 = "14:00";
        String name1 = "test name 1";

        String id2 = "2018-12-20_1800";
        String dateStart2 = "2018-12-20";
        String timeStart2 = "18:00";
        String dateEnd2 = "2018-12-20";
        String timeEnd2 = "19:00";
        String name2 = "test name 2";

        String id3 = "2018-12-20_0900";
        String dateStart3 = "2018-12-20";
        String timeStart3 = "09:00";
        String dateEnd3 = "2018-12-20";
        String timeEnd3 = "10:00";
        String name3 = "test name 3";

        String id4 = "2018-12-20_0900";
        String dateStart4 = "2018-12-20";
        String timeStart4 = "14:30";
        String dateEnd4 = "2018-12-20";
        String timeEnd4 = "16:00";
        String name4 = "test name 4";

        // 3, 1, 4, 2


        if( xmlC.readXmlFileAndLoad() ) {
            Log.d("XMLC","reading XML file success");
            xmlC.createXmlContentSkeleton();    // TODO: del

        } else {
            Log.d("XMLC", "error while reading XML file -> file not exist");
            xmlC.createXmlContentSkeleton();
        }
        xmlC.logCurrentXmlContent();
        xmlC.addEventToXmlContent(id1, dateStart1, timeStart1, dateEnd1, timeEnd1, name1);
        xmlC.logCurrentXmlContent();
        xmlC.addEventToXmlContent(id2, dateStart2, timeStart2, dateEnd2, timeEnd2, name2);
        xmlC.logCurrentXmlContent();
        xmlC.addEventToXmlContent(id3, dateStart3, timeStart3, dateEnd3, timeEnd3, name3);
        xmlC.logCurrentXmlContent();
        xmlC.addEventToXmlContent(id4, dateStart4, timeStart4, dateEnd4, timeEnd4, name4);
        xmlC.logCurrentXmlContent();
        xmlC.saveXmlContentToFile();

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
