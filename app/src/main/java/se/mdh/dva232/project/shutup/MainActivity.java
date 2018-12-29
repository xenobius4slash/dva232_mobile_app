package se.mdh.dva232.project.shutup;

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

        XmlController xmlC = new XmlController( getBaseContext() );


        //SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        //SimpleDateFormat timeFormater = new SimpleDateFormat("hh:mm");

        String id1 = "2018-12-03_1200";
        String dateStart1 = "2018-12-29";
        String timeStart1 = "12:00";
        String dateEnd1 = "2018-12-29";
        String timeEnd1 = "15:45";
        String name1 = "test name 1";

        String id2 = "2018-12-04_1200";
        String dateStart2 = "2018-12-29";
        String timeStart2 = "12:00";
        String dateEnd2 = "2018-12-29";
        String timeEnd2 = "15:51";
        String name2 = "test name 2";

        if( xmlC.readXmlFileAndLoad() ) {
            Log.d("XMLC","reading XML file success");
            xmlC.createXmlContentSkeleton();
            xmlC.logCurrentXmlContent();
            xmlC.addEventToXmlContent(id1, dateStart1, timeStart1, dateEnd1, timeEnd1, name1);
            //xmlC.logCurrentXmlContent();
            xmlC.addEventToXmlContent(id2, dateStart2, timeStart2, dateEnd2, timeEnd2, name2);
            xmlC.saveXmlContentToFile();
            //xmlC.logCurrentXmlContent();
            //xmlC.removeEventFromXmlContent(id1);
            xmlC.removeAllPastEventsFromXmlContent();
            xmlC.saveXmlContentToFile();
            xmlC.logCurrentXmlContent();
        } else {
            Log.d("XMLC", "error while reading XML file -> file not exist");
            xmlC.createXmlContentSkeleton();
            xmlC.logCurrentXmlContent();
            xmlC.addEventToXmlContent(id1, dateStart1, timeStart1, dateEnd1, timeEnd1, name1);
            //xmlC.logCurrentXmlContent();
            xmlC.addEventToXmlContent(id2, dateStart2, timeStart2, dateEnd2, timeEnd2, name2);
            xmlC.saveXmlContentToFile();
            //xmlC.logCurrentXmlContent();
            //xmlC.removeEventFromXmlContent(id1);
            xmlC.removeAllPastEventsFromXmlContent();
            xmlC.saveXmlContentToFile();
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
