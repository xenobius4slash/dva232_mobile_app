package se.mdh.dva232.project.shutup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

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
        View rootView = inflater.inflate(R.layout.fragment_fragment0, container, false);

        final AudioController AC = new AudioController(rootView.getContext());
        AC.logCurrentAudioMode();
        Button btnSilent = rootView.findViewById(R.id.silent_btn);
        btnSilent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AC.setPhoneToSilentMode(false);
            }
        });
        Button btnVibration = rootView.findViewById(R.id.vibrate_btn);
        btnVibration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AC.setPhoneToSilentMode(true);
            }
        });
        Button btnNormal = rootView.findViewById(R.id.normal_btn);
        btnNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AC.setPhoneToPreviousMode();
            }
        });

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
