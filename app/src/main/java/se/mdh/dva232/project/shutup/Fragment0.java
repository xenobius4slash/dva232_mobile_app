package se.mdh.dva232.project.shutup;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Fragment0 extends Fragment {

    private static Boolean debugLifecycle = false;

    public Fragment0() {
        // Required empty public constructor
    }

    public static Fragment0 newInstance() {
        if (debugLifecycle) { Log.d("LIFECYCLE Fragment0", "newInstance()"); }
        Fragment0 fragment = new Fragment0();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (debugLifecycle) { Log.d("LIFECYCLE Fragment0", "onCreate"); }
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (debugLifecycle) { Log.d("LIFECYCLE Fragment0", "onCreateView(...)"); }
        // Inflate the layout for this fragment

        final EventController EC = new EventController( getContext() );
        final SharedPreferences settings = getContext().getSharedPreferences("UserInfo", 0);
        final SharedPreferences.Editor settingsEditor = settings.edit();
        final Calendar myCalendar = Calendar.getInstance();
        final View rootView;









        if( settings.getBoolean("extended_mode", false)) {
            /* ###################
             * ## EXTENDED mode ##
             * ###################
             */
            rootView = inflater.inflate(R.layout.fragment_fragment0_extended, container, false);


            /*
             *  TimePicker
             */
            final TimePicker timePicker = rootView.findViewById(R.id.f0_extended_timepicker);
            timePicker.setIs24HourView(true);


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
             *  Buttons: silent for duration
             */
            final Button btnDuration1 = rootView.findViewById(R.id.f0_extended_btn_duration_1);
            final Button btnDuration2 = rootView.findViewById(R.id.f0_extended_btn_duration_2);
            final Button btnDuration3 = rootView.findViewById(R.id.f0_extended_btn_duration_3);
            final Button btnDuration4 = rootView.findViewById(R.id.f0_extended_btn_duration_4);
            final Button btnDuration5 = rootView.findViewById(R.id.f0_extended_btn_duration_5);
            final Button btnDuration6 = rootView.findViewById(R.id.f0_extended_btn_duration_6);

            // set value for buttons from the settings
            btnDuration1.setText( settings.getString("btn_duration_1", "0:30") );
            btnDuration2.setText( settings.getString("btn_duration_2", "1:00") );
            btnDuration3.setText( settings.getString("btn_duration_3", "1:30") );
            btnDuration4.setText( settings.getString("btn_duration_4", "2:00") );
            btnDuration5.setText( settings.getString("btn_duration_5", "2:30") );
            btnDuration6.setText( settings.getString("btn_duration_6", "3:00") );

            // Button for duration 1 -> click: activate the silent mode & if activated move app to background
            btnDuration1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doActivateSilentModeFromNowByButton(EC, btnDuration1.getText().toString(),"duration");
                    goToBackgroundIfIsActivated();
                }
            });
            // Button for duration 1 -> hold: show TimePickerDialog
            btnDuration1.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    openTimePickerDialog(btnDuration1, 1);
                    return false;
                }
            });

            // Button for duration 2 -> click: activate the silent mode & if activated move app to background
            btnDuration2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doActivateSilentModeFromNowByButton(EC, btnDuration2.getText().toString(),"duration");
                    goToBackgroundIfIsActivated();
                }
            });
            // Button for duration 2 -> hold: show TimePickerDialog
            btnDuration2.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    openTimePickerDialog(btnDuration2, 2);
                    return false;
                }
            });

            // Button for duration 3 -> click: activate the silent mode & if activated move app to background
            btnDuration3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doActivateSilentModeFromNowByButton(EC, btnDuration3.getText().toString(),"duration");
                    goToBackgroundIfIsActivated();
                }
            });
            // Button for duration 3 -> hold: show TimePickerDialog
            btnDuration3.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    openTimePickerDialog(btnDuration3, 3);
                    return false;
                }
            });

            // Button for duration 4 -> click: activate the silent mode & if activated move app to background
            btnDuration4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doActivateSilentModeFromNowByButton(EC, btnDuration4.getText().toString(),"duration");
                    goToBackgroundIfIsActivated();
                }
            });
            // Button for duration 4 -> hold: show TimePickerDialog
            btnDuration4.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    openTimePickerDialog(btnDuration4, 4);
                    return false;
                }
            });

            // Button for duration 5 -> click: activate the silent mode & if activated move app to background
            btnDuration5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doActivateSilentModeFromNowByButton(EC, btnDuration5.getText().toString(),"duration");
                    goToBackgroundIfIsActivated();
                }
            });
            // Button for duration 5 -> hold: show TimePickerDialog
            btnDuration5.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    openTimePickerDialog(btnDuration5, 5);
                    return false;
                }
            });

            // Button for duration 6 -> click: activate the silent mode & if activated move app to background
            btnDuration6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doActivateSilentModeFromNowByButton(EC, btnDuration6.getText().toString(),"duration");
                    goToBackgroundIfIsActivated();
                }
            });
            // Button for duration 6 -> hold: show TimePickerDialog
            btnDuration6.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    openTimePickerDialog(btnDuration6, 6);
                    return false;
                }
            });


            /*
             *  Button: deactivate an active silent mode manually
             */
            final Button btnDeactivateSilentMode = rootView.findViewById(R.id.f0_extended_btn_deactivate);
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
                    settingsEditor.putBoolean("close_after_activation",isChecked);
                    settingsEditor.apply();
                }
            });
        } else {
            /* #################
             * ## NORMAL mode ##
             * #################
             */
            rootView = inflater.inflate(R.layout.fragment_fragment0, container, false);

            //imageviews
            ImageView silent_image = rootView.findViewById(R.id.silent_image);
            ImageView normal_image = rootView.findViewById(R.id.normal_image);
            ImageView vibration_image = rootView.findViewById(R.id.vibration_image);


            selectAudioModeForImage(silent_image,normal_image,vibration_image);

            /*
             *  Buttons: silent for duration
             */
            final Button btnDuration1 = rootView.findViewById(R.id.f0_normal_btn_duration_1);
            final Button btnDuration2 = rootView.findViewById(R.id.f0_normal_btn_duration_2);
            final Button btnDuration3 = rootView.findViewById(R.id.f0_normal_btn_duration_3);
            final Button btnDuration4 = rootView.findViewById(R.id.f0_normal_btn_duration_4);
            final Button btnDuration5 = rootView.findViewById(R.id.f0_normal_btn_duration_5);
            final Button btnDuration6 = rootView.findViewById(R.id.f0_normal_btn_duration_6);

            // set value for buttons from the settings
            btnDuration1.setText( settings.getString("btn_duration_1", "0:30") );
            btnDuration2.setText( settings.getString("btn_duration_2", "1:00") );
            btnDuration3.setText( settings.getString("btn_duration_3", "1:30") );
            btnDuration4.setText( settings.getString("btn_duration_4", "2:00") );
            btnDuration5.setText( settings.getString("btn_duration_5", "2:30") );
            btnDuration6.setText( settings.getString("btn_duration_6", "3:00") );

            // Button for duration 1 -> click: activate the silent mode & if activated move app to background
            btnDuration1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doActivateSilentModeFromNowByButton(EC, btnDuration1.getText().toString(),"duration");
                    goToBackgroundIfIsActivated();
                }
            });
            // Button for duration 1 -> hold: show TimePickerDialog
            btnDuration1.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    openTimePickerDialog(btnDuration1, 1);
                    return false;
                }
            });

            // Button for duration 2 -> click: activate the silent mode & if activated move app to background
            btnDuration2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doActivateSilentModeFromNowByButton(EC, btnDuration2.getText().toString(),"duration");
                    goToBackgroundIfIsActivated();
                }
            });
            // Button for duration 2 -> hold: show TimePickerDialog
            btnDuration2.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    openTimePickerDialog(btnDuration2, 2);
                    return false;
                }
            });

            // Button for duration 3 -> click: activate the silent mode & if activated move app to background
            btnDuration3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doActivateSilentModeFromNowByButton(EC, btnDuration3.getText().toString(),"duration");
                    goToBackgroundIfIsActivated();
                }
            });
            // Button for duration 3 -> hold: show TimePickerDialog
            btnDuration3.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    openTimePickerDialog(btnDuration3, 3);
                    return false;
                }
            });

            // Button for duration 4 -> click: activate the silent mode & if activated move app to background
            btnDuration4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doActivateSilentModeFromNowByButton(EC, btnDuration4.getText().toString(),"duration");
                    goToBackgroundIfIsActivated();
                }
            });
            // Button for duration 4 -> hold: show TimePickerDialog
            btnDuration4.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    openTimePickerDialog(btnDuration4, 4);
                    return false;
                }
            });

            // Button for duration 5 -> click: activate the silent mode & if activated move app to background
            btnDuration5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doActivateSilentModeFromNowByButton(EC, btnDuration5.getText().toString(),"duration");
                    goToBackgroundIfIsActivated();
                }
            });
            // Button for duration 5 -> hold: show TimePickerDialog
            btnDuration5.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    openTimePickerDialog(btnDuration5, 5);
                    return false;
                }
            });

            // Button for duration 6 -> click: activate the silent mode & if activated move app to background
            btnDuration6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doActivateSilentModeFromNowByButton(EC, btnDuration6.getText().toString(),"duration");
                    goToBackgroundIfIsActivated();
                }
            });
            // Button for duration 6 -> hold: show TimePickerDialog
            btnDuration6.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    openTimePickerDialog(btnDuration6, 6);
                    return false;
                }
            });


            /*
             *  Button: deactivate an active silent mode manually
             */
            final Button btnDeactivateSilentMode = rootView.findViewById(R.id.f0_normal_btn_deactivate);
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
        if (debugLifecycle) { Log.d("LIFECYCLE Fragment0", "onStart"); }
        super.onStart();
    }

    @Override
    public void onResume() {
        if (debugLifecycle) { Log.d("LIFECYCLE Fragment0", "onResume"); }
        super.onResume();
    }

    @Override
    public void onPause() {
        if (debugLifecycle) { Log.d("LIFECYCLE Fragment0", "onPause"); }
        super.onPause();
    }

    @Override
    public void onStop() {
        if (debugLifecycle) { Log.d("LIFECYCLE Fragment0", "onStop"); }
        super.onStop();
    }

    @Override
    public void onDestroy() {
        if (debugLifecycle) { Log.d("LIFECYCLE Fragment0", "onDestroy"); }
        super.onDestroy();
    }

    /**
     * open a dialog with a TimePicker
     * @param btnDuration       Button      Object
     * @param btnNumber         Integer     Button number
     */
    private void openTimePickerDialog(final Button btnDuration, final Integer btnNumber) {
        String[] match = btnDuration.getText().toString().split(":");
        new TimePickerDialog(
                getContext(),                                   // Context of the TimePickerDialog
                new TimePickerDialog.OnTimeSetListener() {      // Listener of the TimePickerDialog
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if (hourOfDay == 0 && minute == 0){
                            Toast.makeText(getContext(), "Select a time greater than 0:00",Toast.LENGTH_SHORT).show();
                        } else {
                            String minuteString;
                            if (minute < 10) {
                                minuteString = "0" + minute;
                            } else {
                                minuteString = String.valueOf(minute);
                            }
                            btnDuration.setText(hourOfDay+":"+minuteString);
                            SharedPreferences settings = getContext().getSharedPreferences("UserInfo", 0);
                            SharedPreferences.Editor settingsEditor = settings.edit();
                            switch(btnNumber) {
                                case 1: settingsEditor.putString("btn_duration_1", hourOfDay + ":" + minuteString); break;
                                case 2: settingsEditor.putString("btn_duration_2", hourOfDay + ":" + minuteString); break;
                                case 3: settingsEditor.putString("btn_duration_3", hourOfDay + ":" + minuteString); break;
                                case 4: settingsEditor.putString("btn_duration_4", hourOfDay + ":" + minuteString); break;
                                case 5: settingsEditor.putString("btn_duration_5", hourOfDay + ":" + minuteString); break;
                                case 6: settingsEditor.putString("btn_duration_6", hourOfDay + ":" + minuteString); break;
                            }
                            settingsEditor.apply();
                        }
                    }
                },
                Integer.parseInt(match[0]),     // Hour of the TimePickerDialog
                Integer.parseInt(match[1]),     // Minute of the TimePickerDialog
                true               // 24 hours of the TimePickerDialog
        ).show();
    }

    /**
     * Deactivate an active silent mode
     */
    private void doDeactivateSilentModeByButton() {
        SharedPreferences settings = getContext().getSharedPreferences("UserInfo", 0);
        Toast.makeText(getContext(), "Current silent mode deactivated", Toast.LENGTH_SHORT).show();
        SharedPreferences.Editor settingsEditor = settings.edit();
        settingsEditor.putBoolean("silent_mode_active", false);
        settingsEditor.apply();
    }

    /**
     * The app move to background if activated
     */
    private void goToBackgroundIfIsActivated() {
        SharedPreferences settings = getContext().getSharedPreferences("UserInfo", 0);
        if( settings.getBoolean("close_after_activation", false) ) {
            getActivity().moveTaskToBack(true);
        }
    }

    /**
     * prepare the inputs for calling the activate silent mode function regarding the mode (for duration or until time)
     * @param EC            EventController         Object
     * @param btnText       String                  Format: "HH:mm"
     * @param mode          String                  ["duration", "time"]
     */
    private void doActivateSilentModeFromNowByButton(EventController EC, String btnText, String mode) {
        SharedPreferences settings = getContext().getSharedPreferences("UserInfo", 0);
        if( settings.getBoolean("silent_mode_active", true) ) {
            Toast.makeText(getContext(), "Currently a silent mode is active", Toast.LENGTH_SHORT).show();
        } else {
            Calendar nowCal = Calendar.getInstance();
            Date now = nowCal.getTime();
            Boolean addDay = false;
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdfTimeShort = new SimpleDateFormat("HH:mm");

            String[] match = btnText.split(":");
            Integer hour = Integer.parseInt(match[0]);
            Integer minute = Integer.parseInt(match[1]);

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
                    addDay = true;
                    endCal.add(Calendar.DAY_OF_MONTH, 1);
                    end = endCal.getTime();
                }
            }

            if (end != null) {
                if (mode.equals("time")) {
                    if (addDay) {
                        Toast.makeText(getContext(), "Silent until " + sdfTimeShort.format(end) + " next day", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Silent until " + sdfTimeShort.format(end), Toast.LENGTH_SHORT).show();
                    }
                } else if (mode.equals("duration")) {
                    Toast.makeText(getContext(), "Silent until " + sdfTimeShort.format(end), Toast.LENGTH_SHORT).show();
                }
                EC.activateSilentModeFromNow(sdfDate.format(now), sdfTime.format(now), sdfDate.format(end), sdfTime.format(end));
            }
        }
    }



    private void selectAudioModeForImage(ImageView silent, ImageView normal, ImageView vibration)
    {
        AudioManager AM = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);
        int current_mode = AM.getRingerMode();
        switch(current_mode)
        {
            case 0: Log.d("checking_images", "silent  "+current_mode);
                silent.setImageResource(R.drawable.baseline_volume_off_black_18dp);
                break;
            case 1: Log.d("checking_images", "vibrate  "+current_mode);
                vibration.setImageResource(R.drawable.baseline_vibration_black_18dp);
                break;
            case 2: Log.d("checking_images", "normal  "+current_mode);
                normal.setImageResource(R.drawable.baseline_volume_up_black_18dp);
                break;
            default: Log.d("checking_images", "default  "+current_mode);
                break;

        }

    }


}
