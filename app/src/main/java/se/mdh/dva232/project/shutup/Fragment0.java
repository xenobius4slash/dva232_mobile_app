package se.mdh.dva232.project.shutup;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

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
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        Log.d("LIFECYCLE Fragment0", "onCreateView(...)");
        // Inflate the layout for this fragment

        final SharedPreferences settings = getContext().getSharedPreferences("UserInfo", 0);
        View rootView;
//        rootViewNormal = inflater.inflate(R.layout.fragment_fragment0, container, false);
//        rootViewExtended = inflater.inflate(R.layout.fragment_fragment0_extended, container, false);

        /*
        // init switches at normal layout landscape
        Switch switchVibrationNormalModeLandscape = rootViewNormal.findViewById(R.id.f0_normal_switch_vibration_land);
        Switch switchModeNormalModeLandscape = rootViewNormal.findViewById(R.id.f0_normal_switch_extended_mode);
        Switch switchCloseNormalModeLandscape = rootViewNormal.findViewById(R.id.f0_normal_switch_close_after_activation_land);
        // required
        switchVibrationNormalModeLandscape.setChecked(false);
        switchModeNormalModeLandscape.setChecked(false);
        switchCloseNormalModeLandscape.setChecked(false);
        // set by settings
        switchVibrationNormalModeLandscape.setChecked( (Boolean) settings.getAll().get("vibration") );
        switchModeNormalModeLandscape.setChecked( (Boolean) settings.getAll().get("extended_mode") );
        switchCloseNormalModeLandscape.setChecked( (Boolean) settings.getAll().get("close_after_activation") );
        */

        Switch switchExtendedMode;
        final SharedPreferences.Editor settingsEditor = settings.edit();
        if( (Boolean) settings.getAll().get("extended_mode") ) {
            // extended mode
            rootView = inflater.inflate(R.layout.fragment_fragment0_extended, container, false);

            // init switches at extended layout
            Switch switchVibrationExtendedMode = rootView.findViewById(R.id.f0_extended_switch_vibration);
            Switch switchModeExtendedMode = rootView.findViewById(R.id.f0_extended_switch_extended_mode);
            Switch switchCloseExtendedMode = rootView.findViewById(R.id.f0_extended_switch_close_after_activation);
            // required
            switchVibrationExtendedMode.setChecked(false);
            switchModeExtendedMode.setChecked(false);
            switchCloseExtendedMode.setChecked(false);
            // set by settings
            switchVibrationExtendedMode.setChecked( (Boolean) settings.getAll().get("vibration") );
            switchModeExtendedMode.setChecked( (Boolean) settings.getAll().get("extended_mode") );
            switchCloseExtendedMode.setChecked( (Boolean) settings.getAll().get("close_after_activation") );

            switchExtendedMode = rootView.findViewById(R.id.f0_extended_switch_extended_mode);
            switchExtendedMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.d("SETTINGS", "extended mode -> extended mode (#1): " + settings.getAll().get("extended_mode") + " // new value should be: " + isChecked );
                    settingsEditor.putBoolean("extended_mode", isChecked);  // set the setting by the isChecked variable
                    settingsEditor.apply();
                    Log.d("SETTINGS", "extended mode -> extended mode (#2): " + settings.getAll().get("extended_mode"));
                    Toast.makeText( getContext(), "Changes will be effected after restart of the App", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            // normal mode
            rootView = inflater.inflate(R.layout.fragment_fragment0, container, false);

            // init switches at normal layout
            Switch switchVibrationNormalMode = rootView.findViewById(R.id.f0_normal_switch_vibration);
            Switch switchModeNormalMode = rootView.findViewById(R.id.f0_normal_switch_extended_mode);
            Switch switchCloseNormalMode = rootView.findViewById(R.id.f0_normal_switch_close_after_activation);
            // required
            switchVibrationNormalMode.setChecked(false);
            switchModeNormalMode.setChecked(false);
            switchCloseNormalMode.setChecked(false);
            // set by settings
            switchVibrationNormalMode.setChecked( (Boolean) settings.getAll().get("vibration") );
            switchModeNormalMode.setChecked( (Boolean) settings.getAll().get("extended_mode") );
            switchCloseNormalMode.setChecked( (Boolean) settings.getAll().get("close_after_activation") );

            switchExtendedMode = rootView.findViewById(R.id.f0_normal_switch_extended_mode);
            switchExtendedMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.d("SETTINGS", "normal mode -> extended mode (#1): " + settings.getAll().get("extended_mode")  + " // new value should be: " + isChecked);
                    settingsEditor.putBoolean("extended_mode", isChecked);  // set the setting by the isChecked variable
                    settingsEditor.apply();
                    Log.d("SETTINGS", "normal mode -> extended mode (#2): " + settings.getAll().get("extended_mode"));
                    Toast.makeText( getContext(), "Changes will be effected after restart of the App", Toast.LENGTH_LONG).show();
                }
            });
        }

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

    private void changeFragment0ToNormalMode(){

    }

    private void changeFragment0ToExtendedMode() {

    }

}
