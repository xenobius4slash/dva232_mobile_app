package se.mdh.dva232.project.shutup;

import android.app.Activity;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import java.util.Calendar;

public class Fragment1 extends Fragment {


    private Activity rootView;

    public Fragment1() {
        // Required empty public constructor
    }

    public static Fragment1 newInstance() {
        Log.d("LIFECYCLE Fragment1", "newInstance()");
        Fragment1 fragment = new Fragment1();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("LIFECYCLE Fragment1", "onCreate");
        super.onCreate(savedInstanceState);







    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("LIFECYCLE Fragment1", "onCreateView(...)");
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_fragment1, container, false);

        Button button = (Button)rootView.findViewById(R.id.button_date);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment  = new DialogFragment() ;
                //newFragment.show(getActivity().getFragmentManager(), DIALOG_TIME);
                newFragment.show(getFragmentManager(),"time picker");
                // if you are using the nested fragment then user the
                //newFragment.show(getChildFragmentManager(), DIALOG_TIME);
            }
        });




        return rootView;


    }



    @Override
    public void onStart() {
        Log.d("LIFECYCLE Fragment1", "onStart");
        super.onStart();
    }

    @Override
    public void onResume() {
        Log.d("LIFECYCLE Fragment1", "onResume");
        super.onResume();
    }

    @Override
    public void onPause() {
        Log.d("LIFECYCLE Fragment1", "onPause");
        super.onPause();
    }

    @Override
    public void onStop() {
        Log.d("LIFECYCLE Fragment1", "onStop");
        super.onStop();
    }

    @Override
    public void onDestroy() {
        Log.d("LIFECYCLE Fragment1", "onDestroy");
        super.onDestroy();
    }

}
