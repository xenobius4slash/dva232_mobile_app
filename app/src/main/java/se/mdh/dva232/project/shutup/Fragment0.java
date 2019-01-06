package se.mdh.dva232.project.shutup;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Build;
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

        final EventController EC = new EventController( getContext() );
        final SharedPreferences settings = getContext().getSharedPreferences("UserInfo", 0);
        final SharedPreferences.Editor settingsEditor = settings.edit();
        final View rootView;

        if( settings.getBoolean("extended_mode", false)) {
            /* ###################
             * ## EXTENDED mode ##
             * ###################
             */
            rootView = inflater.inflate(R.layout.fragment_fragment0_extended, container, false);
            final TimePicker timePicker = rootView.findViewById(R.id.f0_extended_timepicker);
            timePicker.setIs24HourView(true);

            /*
             *  Button: deactivate an active silent mode manually
             */
            final Button btnDeactivateSilentMode = rootView.findViewById(R.id.f0_extended_btn_deactivate);
            Log.d("SETTINGS", "silent_mode_active: " + settings.getAll().get("silent_mode_active"));
            btnDeactivateSilentMode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ( settings.getBoolean("silent_mode_active", true) ) {
                        doDeactivateSilentModeByButton();
                    } else {
                        Toast.makeText(getContext(), "no silent mode active", Toast.LENGTH_SHORT).show();
                    }
                }
            });

            /*
             *  Buttons: below the TimePicker
             */
            Button btnDurationByTimePicker = rootView.findViewById(R.id.f0_extended_btn_duration);
            btnDurationByTimePicker.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    Integer hour;
                    Integer minute;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        hour = timePicker.getHour();
                        minute = timePicker.getMinute();
                    } else {
                        hour = timePicker.getCurrentHour();
                        minute = timePicker.getCurrentMinute();
                    }
                    doActivateSilentModeFromNowByButton(EC, hour+":"+minute, "duration");
                }
            } );
            Button btnTimeByTimePicker = rootView.findViewById(R.id.f0_extended_btn_time);
            btnTimeByTimePicker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Integer hour;
                    Integer minute;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        hour = timePicker.getHour();
                        minute = timePicker.getMinute();
                    } else {
                        hour = timePicker.getCurrentHour();
                        minute = timePicker.getCurrentMinute();
                    }
                    doActivateSilentModeFromNowByButton(EC, hour+":"+minute, "time");
                }
            });

            /*
             *  Switches
             */
            // init switches at extended layout
            Switch switchVibrationExtendedMode = rootView.findViewById(R.id.f0_extended_switch_vibration);
            Switch switchModeExtendedMode = rootView.findViewById(R.id.f0_extended_switch_extended_mode);
            Switch switchCloseExtendedMode = rootView.findViewById(R.id.f0_extended_switch_close_after_activation);
            // required
            switchVibrationExtendedMode.setChecked(false);
            switchModeExtendedMode.setChecked(false);
            switchCloseExtendedMode.setChecked(false);
            // set by settings
            switchVibrationExtendedMode.setChecked( settings.getBoolean("vibration", false) );
            switchModeExtendedMode.setChecked( settings.getBoolean("extended_mode", false) );
            switchCloseExtendedMode.setChecked( settings.getBoolean("close_after_activation", false) );
            // Listener
            switchVibrationExtendedMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    settingsEditor.putBoolean("vibration", isChecked);  // set the setting by the isChecked variable
                    settingsEditor.apply();
                }
            });
            switchModeExtendedMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    settingsEditor.putBoolean("extended_mode", isChecked);  // set the setting by the isChecked variable
                    settingsEditor.apply();
                    Toast.makeText( getContext(), "Changes will be effected after restart of the App", Toast.LENGTH_LONG).show();
                }
            });
            switchCloseExtendedMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    settingsEditor.putBoolean("close_after_activation", isChecked);  // set the setting by the isChecked variable
                    settingsEditor.apply();
                }
            });
        } else {
            /* #################
             * ## NORMAL mode ##
             * #################
             */
            rootView = inflater.inflate(R.layout.fragment_fragment0, container, false);


            /*
             *  Button: deactivate an active silent mode manually
             */
            final Button btnDeactivateSilentMode = rootView.findViewById(R.id.f0_normal_btn_deactivate);
            Log.d("SETTINGS", "silent_mode_active: " + settings.getAll().get("silent_mode_active"));
            btnDeactivateSilentMode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ( settings.getBoolean("silent_mode_active", true) ) {
                        doDeactivateSilentModeByButton();
                    } else {
                        Toast.makeText(getContext(), "no silent mode active", Toast.LENGTH_SHORT).show();
                    }
                }
            });


            /*
             *  Buttons: for duration
             */
            // activate silent mode by duration button & enable button deactivate running silent mode
            final Button btnDuration1 = rootView.findViewById(R.id.f0_normal_btn_duration_1);
            btnDuration1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doActivateSilentModeFromNowByButton(EC, btnDuration1.getText().toString(),"duration");
                }
            });


            /*
             *  Switches
             */
            // init switches at normal layout
            Switch switchVibrationNormalMode = rootView.findViewById(R.id.f0_normal_switch_vibration);
            Switch switchModeNormalMode = rootView.findViewById(R.id.f0_normal_switch_extended_mode);
            Switch switchCloseNormalMode = rootView.findViewById(R.id.f0_normal_switch_close_after_activation);
            // required
            switchVibrationNormalMode.setChecked(false);
            switchModeNormalMode.setChecked(false);
            switchCloseNormalMode.setChecked(false);
            // set by settings
            switchVibrationNormalMode.setChecked( settings.getBoolean("vibration", false) );
            switchModeNormalMode.setChecked( settings.getBoolean("extended_mode", false) );
            switchCloseNormalMode.setChecked( settings.getBoolean("close_after_activation", false) );
            // Listener
            switchVibrationNormalMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    settingsEditor.putBoolean("vibration", isChecked);  // set the setting by the isChecked variable
                    settingsEditor.apply();
                }
            });
            switchModeNormalMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    settingsEditor.putBoolean("extended_mode", isChecked);  // set the setting by the isChecked variable
                    settingsEditor.apply();
                    Toast.makeText( getContext(), "Changes will be effected after restart of the App", Toast.LENGTH_LONG).show();
                }
            });
            switchCloseNormalMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    settingsEditor.putBoolean("close_after_activation", isChecked);  // set the setting by the isChecked variable
                    settingsEditor.apply();
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

    private void doDeactivateSilentModeByButton() {
        SharedPreferences settings = getContext().getSharedPreferences("UserInfo", 0);
        Toast.makeText(getContext(), "Current silent mode deactivated", Toast.LENGTH_SHORT).show();
        SharedPreferences.Editor settingsEditor = settings.edit();
        settingsEditor.putBoolean("silent_mode_active", false);
        settingsEditor.apply();
    }

    /**
     * prepare the inputs for calling the activate silent mode function regarding the mode (for duration or until time)
     * @param EC            EventController         Object
     * @param btnText       String                  Format: "HH:mm"
     * @param mode          String                  ["duration", "time"]
     */
    private void doActivateSilentModeFromNowByButton(EventController EC, String btnText, String mode) {
        Calendar nowCal = Calendar.getInstance();
        Date now = nowCal.getTime();
        Boolean addDay = false;
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdfTimeShort = new SimpleDateFormat("HH:mm");

        String[] match = btnText.split(":");
        Integer hour = Integer.parseInt(match[0]);
        Integer minute = Integer.parseInt(match[1]);
        Log.d("F0_ASM", "duration: " +hour+":"+minute+ " -> minutes: " + (minute + (60 * hour)) );

        Date end = null;
        if (mode.equals("duration")) {
            Calendar endCal = Calendar.getInstance();
//          endCal.setTime(now);
            endCal.add(Calendar.MINUTE, (minute + (60 * hour)));
            end = endCal.getTime();
        } else if (mode.equals("time")) {
            Calendar endCal = Calendar.getInstance();
            endCal.set(nowCal.get(Calendar.YEAR), nowCal.get(Calendar.MONTH), nowCal.get(Calendar.DAY_OF_MONTH), hour, minute, 0);
            end = endCal.getTime();
            if (!end.after(now)) {
                // selected time by TimePicker is less than the current time, therefore add one day
                Log.d("F0_ASM", "selected time by TimePicker is less than the current time -> add one day (current end date: "+end.toString()+")");
                addDay = true;
                endCal.add(Calendar.DAY_OF_MONTH, 1);
                end = endCal.getTime();
            }
        }

        if (end != null) {
            Log.d("F0_ASM", "start: " + now.toString() + " // end: " + end.toString());
            if (mode.equals("time")) {
                if (addDay) {
                    Toast.makeText(getContext(), "Silent until " + sdfTimeShort.format(end) + " next day", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Silent until " + sdfTimeShort.format(end), Toast.LENGTH_SHORT).show();
                }
            } else if (mode.equals("duration")){
                Toast.makeText(getContext(), "Silent until " + sdfTimeShort.format(end), Toast.LENGTH_SHORT).show();
            }
            EC.activateSilentModeFromNow(sdfDate.format(now), sdfTime.format(now), sdfDate.format(end), sdfTime.format(end));
        }
    }
}
