package se.mdh.dva232.project.shutup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.sql.Struct;
import java.util.Arrays;

public class Fragment2 extends Fragment {

    //define variables
    ListView list;
    private ListView_Adapter2 data;




    element[] values = {
            new element( "event 1", "12/07/2019","12:54","13:00"),
            new element("event 2","17/09/2019","12:45","13:56"),
            new element("event 3","11/03/2019","10:34","11:45"),
    };


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



        data = new ListView_Adapter2(getContext(),Arrays.asList(values));

        // Get a reference to the ListView, and attach this adapter to it.
        ListView listView = rootView.findViewById(R.id.listView_Items);
        listView.setAdapter(data);


        EventController EC = new EventController(getContext());
        EC.getAllSavedEvents();

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
