package se.mdh.dva232.project.shutup;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

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
        final Calendar myCalendar = Calendar.getInstance();

        Switch switchExtendedMode;
        final SharedPreferences.Editor settingsEditor = settings.edit();




        if( (Boolean) settings.getAll().get("extended_mode") ) {
            // extended mode
            rootView = inflater.inflate(R.layout.fragment_fragment0_extended, container, false);
            final Button f0_button_1_extended = rootView.findViewById(R.id.f0_extended_btn_duration_1);
            final Button f0_button_2_extended = rootView.findViewById(R.id.f0_extended_btn_duration_2);
            final Button f0_button_3_extended = rootView.findViewById(R.id.f0_extended_btn_duration_3);
            final Button f0_button_4_extended = rootView.findViewById(R.id.f0_extended_btn_duration_4);
            final Button f0_button_5_extended = rootView.findViewById(R.id.f0_extended_btn_duration_5);
            final Button f0_button_6_extended = rootView.findViewById(R.id.f0_extended_btn_duration_6);

            //initiallize buttons
            f0_button_1_extended.setText((CharSequence) settings.getAll().get("btn_duration_1"));
            f0_button_2_extended.setText((CharSequence) settings.getAll().get("btn_duration_2"));
            f0_button_3_extended.setText((CharSequence) settings.getAll().get("btn_duration_3"));
            f0_button_4_extended.setText((CharSequence) settings.getAll().get("btn_duration_4"));
            f0_button_5_extended.setText((CharSequence) settings.getAll().get("btn_duration_5"));
            f0_button_6_extended.setText((CharSequence) settings.getAll().get("btn_duration_6"));


            //show the time set by the user and show - for f0_button_1_extended.
            final TimePickerDialog.OnTimeSetListener dialog = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    Log.d("check_longclick","we are printing the value to the button");
                    f0_button_1_extended.setText(hourOfDay+":"+minute);
                    settingsEditor.putString("btn_duration_1",hourOfDay+":"+minute);
                    settingsEditor.apply();
                }
            };

            //show the dialog to the user - for f0_button_1
            f0_button_1_extended.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.d("check_longclick","we are inside long click listner");
                    //new TimePickerDialog(getContext(),dialog,myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),DateFormat.is24HourFormat(getContext())).show();
                    //createTimeDialog();
                    new TimePickerDialog(getContext(),dialog,myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),DateFormat.is24HourFormat(getContext())).show();
                    return false;
                }



            });

            //show the time set by the user and show - for f0_button_2_extended
            final TimePickerDialog.OnTimeSetListener dialog2 = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    Log.d("check_longclick","we are printing the value to the button");
                    f0_button_2_extended.setText(hourOfDay+":"+minute);
                    settingsEditor.putString("btn_duration_2",hourOfDay+":"+minute);
                    settingsEditor.apply();
                }
            };

            //show the dialog to the user - for button 2
            f0_button_2_extended.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.d("check_longclick","we are inside long click listner");
                    //new TimePickerDialog(getContext(),dialog,myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),DateFormat.is24HourFormat(getContext())).show();
                    //createTimeDialog();
                    new TimePickerDialog(getContext(),dialog2,myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),DateFormat.is24HourFormat(getContext())).show();
                    return false;
                }



            });

            //show the time set by the user and show - for f0_button_3_extended.
            final TimePickerDialog.OnTimeSetListener dialog3 = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    Log.d("check_longclick","we are printing the value to the button");
                    f0_button_3_extended.setText(hourOfDay+":"+minute);
                    settingsEditor.putString("btn_duration_3",hourOfDay+":"+minute);
                    settingsEditor.apply();
                }
            };

            //show the dialog to the user - for f0_button_3
            f0_button_3_extended.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.d("check_longclick","we are inside long click listner");
                    //new TimePickerDialog(getContext(),dialog,myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),DateFormat.is24HourFormat(getContext())).show();
                    //createTimeDialog();
                    new TimePickerDialog(getContext(),dialog3,myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),DateFormat.is24HourFormat(getContext())).show();
                    return false;
                }



            });

            //show the time set by the user and show - for f0_button_4_extended.
            final TimePickerDialog.OnTimeSetListener dialog4 = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    Log.d("check_longclick","we are printing the value to the button");
                    f0_button_4_extended.setText(hourOfDay+":"+minute);
                    settingsEditor.putString("btn_duration_4",hourOfDay+":"+minute);
                    settingsEditor.apply();
                }
            };

            //show the dialog to the user - for f0_button_4
            f0_button_4_extended.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.d("check_longclick","we are inside long click listner");
                    //new TimePickerDialog(getContext(),dialog,myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),DateFormat.is24HourFormat(getContext())).show();
                    //createTimeDialog();
                    new TimePickerDialog(getContext(),dialog4,myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),DateFormat.is24HourFormat(getContext())).show();
                    return false;
                }



            });

            //show the time set by the user and show - for f0_button_5_extended.
            final TimePickerDialog.OnTimeSetListener dialog5 = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    Log.d("check_longclick","we are printing the value to the button");
                    f0_button_5_extended.setText(hourOfDay+":"+minute);
                    settingsEditor.putString("btn_duration_5",hourOfDay+":"+minute);
                    settingsEditor.apply();
                }
            };

            //show the dialog to the user - for f0_button_5
            f0_button_5_extended.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.d("check_longclick","we are inside long click listner");
                    //new TimePickerDialog(getContext(),dialog,myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),DateFormat.is24HourFormat(getContext())).show();
                    //createTimeDialog();
                    new TimePickerDialog(getContext(),dialog5,myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),DateFormat.is24HourFormat(getContext())).show();
                    return false;
                }



            });

            //show the time set by the user and show - for f0_button_6_extended.
            final TimePickerDialog.OnTimeSetListener dialog6 = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    Log.d("check_longclick","we are printing the value to the button");
                    f0_button_6_extended.setText(hourOfDay+":"+minute);
                    settingsEditor.putString("btn_duration_6",hourOfDay+":"+minute);
                    settingsEditor.apply();
                }
            };

            //show the dialog to the user - for f0_button_6
            f0_button_6_extended.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.d("check_longclick","we are inside long click listner");
                    //new TimePickerDialog(getContext(),dialog,myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),DateFormat.is24HourFormat(getContext())).show();
                    //createTimeDialog();
                    new TimePickerDialog(getContext(),dialog,myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),DateFormat.is24HourFormat(getContext())).show();
                    return false;
                }



            });



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

            switchCloseExtendedMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.d("SETTINGS", "close after activation mode");
                    settingsEditor.putBoolean("close_after_activation",isChecked);
                    settingsEditor.apply();
                    //moveTaskToBack(true);
                    //finishAndRemoveTask();
                    getActivity().moveTaskToBack(true);
                    System.exit(1);

                }
            });

        } else {
            // normal mode
            rootView = inflater.inflate(R.layout.fragment_fragment0, container, false);

            final Button f0_button_1 = rootView.findViewById(R.id.f0_normal_btn_duration_1);
            final Button f0_button_2 = rootView.findViewById(R.id.f0_normal_btn_duration_2);
            final Button f0_button_3 = rootView.findViewById(R.id.f0_normal_btn_duration_3);
            final Button f0_button_4 = rootView.findViewById(R.id.f0_normal_btn_duration_4);
            final Button f0_button_5 = rootView.findViewById(R.id.f0_normal_btn_duration_5);
            final Button f0_button_6 = rootView.findViewById(R.id.f0_normal_btn_duration_6);

            //initiallize buttons
            f0_button_1.setText((CharSequence) settings.getAll().get("btn_duration_1"));
            f0_button_2.setText((CharSequence) settings.getAll().get("btn_duration_2"));
            f0_button_3.setText((CharSequence) settings.getAll().get("btn_duration_3"));
            f0_button_4.setText((CharSequence) settings.getAll().get("btn_duration_4"));
            f0_button_5.setText((CharSequence) settings.getAll().get("btn_duration_5"));
            f0_button_6.setText((CharSequence) settings.getAll().get("btn_duration_6"));



            f0_button_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( (Boolean) settings.getAll().get("close_after_activation") )

                        getActivity().moveTaskToBack(true);
                }
            });
            //show the time set by the user and show - for f0_button_1.
            final TimePickerDialog.OnTimeSetListener dialog1 = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    Log.d("check_longclick","we are printing the value to the button");
                    f0_button_1.setText(hourOfDay+":"+minute);
                    settingsEditor.putString("btn_duration_1",hourOfDay+":"+minute);
                    settingsEditor.apply();
                }
            };

            //show the dialog to the user - for f0_button_1
            f0_button_1.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.d("check_longclick","we are inside long click listner");
                    //new TimePickerDialog(getContext(),dialog,myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),DateFormat.is24HourFormat(getContext())).show();
                    //createTimeDialog();
                    new TimePickerDialog(getContext(),dialog1,myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),DateFormat.is24HourFormat(getContext())).show();
                    return false;
                }



            });

            //show the time set by the user and show - for f0_button_2.
            final TimePickerDialog.OnTimeSetListener dialog2 = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    Log.d("check_longclick","we are printing the value to the button");
                    f0_button_2.setText(hourOfDay+":"+minute);
                    settingsEditor.putString("btn_duration_2",hourOfDay+":"+minute);
                    settingsEditor.apply();
                }
            };

            //show the dialog to the user - for f0_button_2
            f0_button_2.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.d("check_longclick","we are inside long click listner");
                    //new TimePickerDialog(getContext(),dialog,myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),DateFormat.is24HourFormat(getContext())).show();
                    //createTimeDialog();
                    new TimePickerDialog(getContext(),dialog2,myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),DateFormat.is24HourFormat(getContext())).show();
                    return false;
                }

            });

            //show the time set by the user and show - for f0_button_3.
            final TimePickerDialog.OnTimeSetListener dialog3 = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    Log.d("check_longclick","we are printing the value to the button");
                    f0_button_3.setText(hourOfDay+":"+minute);
                    settingsEditor.putString("btn_duration_3",hourOfDay+":"+minute);
                    settingsEditor.apply();
                }
            };

            //show the dialog to the user - for f0_button_3
            f0_button_3.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.d("check_longclick","we are inside long click listner");
                    //new TimePickerDialog(getContext(),dialog,myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),DateFormat.is24HourFormat(getContext())).show();
                    //createTimeDialog();
                    new TimePickerDialog(getContext(),dialog3,myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),DateFormat.is24HourFormat(getContext())).show();
                    return false;
                }

            });

            //show the time set by the user and show - for f0_button_4.
            final TimePickerDialog.OnTimeSetListener dialog4 = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    Log.d("check_longclick","we are printing the value to the button");
                    f0_button_4.setText(hourOfDay+":"+minute);
                    settingsEditor.putString("btn_duration_4",hourOfDay+":"+minute);
                    settingsEditor.apply();
                }
            };

            //show the dialog to the user - for f0_button_4
            f0_button_4.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.d("check_longclick","we are inside long click listner");
                    //new TimePickerDialog(getContext(),dialog,myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),DateFormat.is24HourFormat(getContext())).show();
                    //createTimeDialog();
                    new TimePickerDialog(getContext(),dialog4,myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),DateFormat.is24HourFormat(getContext())).show();
                    return false;
                }

            });

            //show the time set by the user and show - for f0_button_5.
            final TimePickerDialog.OnTimeSetListener dialog5 = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    Log.d("check_longclick","we are printing the value to the button");
                    f0_button_5.setText(hourOfDay+":"+minute);
                    settingsEditor.putString("btn_duration_5",hourOfDay+":"+minute);
                    settingsEditor.apply();
                }
            };

            //show the dialog to the user - for f0_button_5
            f0_button_5.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.d("check_longclick","we are inside long click listner");
                    //new TimePickerDialog(getContext(),dialog,myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),DateFormat.is24HourFormat(getContext())).show();
                    //createTimeDialog();
                    new TimePickerDialog(getContext(),dialog5,myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),DateFormat.is24HourFormat(getContext())).show();
                    return false;
                }



            });

            //show the time set by the user and show - for f0_button_6.
            final TimePickerDialog.OnTimeSetListener dialog6 = new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    Log.d("check_longclick","we are printing the value to the button");
                    f0_button_6.setText(hourOfDay+":"+minute);
                    settingsEditor.putString("btn_duration_6",hourOfDay+":"+minute);
                    settingsEditor.apply();
                }
            };

            //show the dialog to the user - for f0_button_6
            f0_button_6.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Log.d("check_longclick","we are inside long click listner");
                    //new TimePickerDialog(getContext(),dialog,myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),DateFormat.is24HourFormat(getContext())).show();
                    //createTimeDialog();
                    new TimePickerDialog(getContext(),dialog6,myCalendar.get(Calendar.HOUR_OF_DAY), myCalendar.get(Calendar.MINUTE),DateFormat.is24HourFormat(getContext())).show();
                    return false;
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

            switchCloseNormalMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    Log.d("SETTINGS", "close after activation mode");
                    settingsEditor.putBoolean("close_after_activation",isChecked);
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

}
