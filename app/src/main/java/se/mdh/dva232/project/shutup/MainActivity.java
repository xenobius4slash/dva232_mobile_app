package se.mdh.dva232.project.shutup;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;

import java.sql.Time;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private SectionsPagerAdapter mSectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("LIFECYCLE Actvity", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        XmlController xmlC = new XmlController( getBaseContext() );
        Calendar dateStart = Calendar.getInstance();
        dateStart.set(Calendar.YEAR, 2018);
        dateStart.set(Calendar.MONTH, 12);
        dateStart.set(Calendar.DAY_OF_MONTH, 3);
        Log.d("DATE", "date: " + dateStart.toString());

        Calendar dateEnd = dateStart;

        Calendar timeStart = Calendar.getInstance();
        timeStart.set(Calendar.HOUR_OF_DAY, 12);
        timeStart.set(Calendar.MINUTE, 00);

        Calendar timeEnd = Calendar.getInstance();
        timeEnd.set(Calendar.HOUR_OF_DAY, 14);
        timeEnd.set(Calendar.MINUTE, 00);
        if( xmlC.readXmlFileAndLoad() ) {
            Log.d("XMLC","reading XML file success");
            xmlC.logCurrentXmlContent();
            xmlC.addEventToXmlContent(dateStart.getTime(), timeStart.getTime(), dateEnd.getTime(), timeEnd.getTime(), "test name" );
            xmlC.logCurrentXmlContent();
        } else {
            Log.d("XMLC", "error while reading XML file");
            xmlC.createXmlContentSkeleton();
            xmlC.logCurrentXmlContent();
            xmlC.addEventToXmlContent(dateStart.getTime(), timeStart.getTime(), dateEnd.getTime(), timeEnd.getTime(), "test name" );
            xmlC.logCurrentXmlContent();
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
