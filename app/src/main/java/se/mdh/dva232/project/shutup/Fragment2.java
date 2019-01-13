package se.mdh.dva232.project.shutup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.sql.Struct;

public class Fragment2 extends Fragment {

    //define variables
    ListView list;

    //struct that contains the elements of each item
    /*class elements
    {
        public String name;
        public String duration;
    }*/


    String [] item_elements =
            {"event 1","11:00 - 12:00"};
            //{"event 2","12:00 - 13:00"},
           // {"event 3","13:00 - 14:00"},
    //};

    public Fragment2() {
        // Required empty public constructor
    }

    public static Fragment2 newInstance() {
        Log.d("LIFECYCLE Fragment2", "newInstance()");
        Fragment2 fragment = new Fragment2();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("LIFECYCLE Fragment2", "onCreate");
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("LIFECYCLE Fragment2", "onCreateView(...)");
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_fragment2, container, false);

        list = rootView.findViewById(R.id.listView_Items);

        Log.d("set adapter", "setting listi view and item");

        list.setAdapter(new ListView_Adapter(getContext(),item_elements));

        return rootView;
    }

    @Override
    public void onStart() {
        Log.d("LIFECYCLE Fragment2", "onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d("LIFECYCLE Fragment2", "onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d("LIFECYCLE Fragment2", "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d("LIFECYCLE Fragment2", "onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.d("LIFECYCLE Fragment2", "onDestroy");
        super.onDestroy();
    }

}
