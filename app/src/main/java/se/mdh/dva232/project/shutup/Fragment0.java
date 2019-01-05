package se.mdh.dva232.project.shutup;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment0 extends Fragment {

    public Fragment0() {
        // Required empty public constructor
    }

    public static Fragment0 newInstance() {
        Log.d("LIFECYCLE Fragment0", "newInstance()");
        Fragment0 fragment = new Fragment0();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("LIFECYCLE Fragment0", "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("LIFECYCLE Fragment0", "onCreateView(...)");
        // Inflate the layout for this fragment

        SharedPreferences settings = getContext().getSharedPreferences("UserInfo", 0);
        View rootView = inflater.inflate(R.layout.fragment_fragment0, container, false);;

        return rootView;
    }

    @Override
    public void onStart() {
        Log.d("LIFECYCLE Fragment0", "onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d("LIFECYCLE Fragment0", "onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d("LIFECYCLE Fragment0", "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d("LIFECYCLE Fragment0", "onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.d("LIFECYCLE Fragment0", "onDestroy");
        super.onDestroy();
    }

}
