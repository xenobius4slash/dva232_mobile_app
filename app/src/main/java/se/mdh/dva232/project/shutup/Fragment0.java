package se.mdh.dva232.project.shutup;

import android.arch.lifecycle.Lifecycle;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

        final SharedPreferences settings = getContext().getSharedPreferences("UserInfo", 0);
        View rootView;

        Switch switchExtendedMode;
        final SharedPreferences.Editor settingsEditor = settings.edit();
        if( (Boolean) settings.getAll().get("extended_mode") ) {
            // extended mode
            rootView = inflater.inflate(R.layout.fragment_fragment0_extended, container, false);
            TimePicker timePicker = rootView.findViewById(R.id.f0_extended_timepicker);
            timePicker.setIs24HourView(true);

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

            final EventController EC = new EventController( getContext() );

            // enable/disable button for deactivating an active silent mode manually
            final Button btnDeactivateSilentMode = rootView.findViewById(R.id.f0_normal_btn_deactivate);
            Log.d("SETTINGS", "silent_mode_active: " + settings.getAll().get("silent_mode_active"));
            if ( (Boolean) settings.getAll().get("silent_mode_active") ) {
                Log.d("SETTINGS", "silent_mode_active -> true -> Button clickable");
                btnDeactivateSilentMode.setEnabled(true);
            } else {
                Log.d("SETTINGS", "silent_mode_active -> false -> Button not clickable");
              btnDeactivateSilentMode.setEnabled(false);
            }

            // deactivate an active silent mode manually
            btnDeactivateSilentMode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "I'm the button for deactivate a active silent mode", Toast.LENGTH_SHORT).show();
                }
            });

            // activate silent mode by duration button
            final Date now = Calendar.getInstance().getTime();
            final SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
            final SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm");
            final Button btnDuration1 = rootView.findViewById(R.id.f0_normal_btn_duration_1);
            btnDuration1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getContext(), "I was hurt from " + btnDuration1.getText().toString(), Toast.LENGTH_SHORT).show();

//                    String[] match = "2:30".split(":");
                    String[] match = btnDuration1.getText().toString().split(":");
                    Integer hour = Integer.parseInt(match[0]);
                    Integer minute = Integer.parseInt(match[1]);
                    Log.d("F0_ASM", "duration: " +hour+":"+minute+ " -> minutes: " + (minute + (60 * hour)) );

                    Calendar endCal = Calendar.getInstance();
                    endCal.setTime(now);
                    endCal.add( Calendar.MINUTE, (minute + (60 * hour)) );
                    Date end = endCal.getTime();

                    Log.d("F0_ASM", "start: "+now.toString()+" // end: " + end.toString() );
                    EC.activateSilentModeFromNow(sdfDate.format(now), sdfTime.format(now), sdfDate.format(end), sdfTime.format(end), "Current");

                }
            });

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

}
