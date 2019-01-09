package se.mdh.dva232.project.shutup;

import android.annotation.TargetApi;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;

public class Fragment1 extends Fragment {
    //private Activity rootView;

    private int start_hour;
    private int start_minute;
    private int end_hour;
    private int end_minute;


    public Fragment1() {
        // Required empty public constructor
    }

    public static Fragment1 newInstance() {
        Log.d("LIFECYCLE Fragment11111", "newInstance()");
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

//    @TargetApi(Build.VERSION_CODES.M)
//    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        Log.d("LIFECYCLE Fragment1", "onCreateView(...)");
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_fragment1, container, false);
        final Calendar myCalendar = Calendar.getInstance();

        /*
         * TimePicker
         */
        //code to get values of TimePicker
        start_hour = myCalendar.get(Calendar.HOUR_OF_DAY);
        start_minute = myCalendar.get(Calendar.MINUTE);
        end_hour = myCalendar.get(Calendar.HOUR_OF_DAY);
        end_minute = myCalendar.get(Calendar.MINUTE);

        final TimePicker timePickerStart = rootView.findViewById(R.id.f1_timePicker_start);
        timePickerStart.setIs24HourView(true);
        timePickerStart.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Log.d("TIMEPICKER","changing timepicker start");
            }
        });

        final TimePicker timePickerEnd = rootView.findViewById(R.id.f1_timePicker_end);
        timePickerEnd.setIs24HourView(true);
        timePickerEnd.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Log.d("TIMEPICKER","changing timepicker end");
            }
        });


        /*
         * Select the date
         */
        //DatePickerDialog sets the time the user selects
        final DatePickerDialog.OnDateSetListener dialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                TextView text = rootView.findViewById(R.id.f1_text_start_date);
                text.setText(dayOfMonth+"-"+(month+1)+"-"+year);
            }
        };

        //create button for the setOnClickListener
        Button btnStartDate = rootView.findViewById(R.id.f1_btn_start_date);

        //show the DatePicker dialog with the actual time values
        btnStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog( getContext(), dialog, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        //set initial time to the actual time
        final TextView textStartDate = rootView.findViewById(R.id.f1_text_start_date);
        textStartDate.setText(myCalendar.get(Calendar.DAY_OF_MONTH)+"-"+ myCalendar.get(Calendar.MONTH+1)+"-"+ myCalendar.get(Calendar.YEAR));


        /*
         * Button for activation
         */
        Button btnActivate = rootView.findViewById(R.id.f1_btn_activate);
        btnActivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Log.d("F1_ACTIVATE", "add event => start_date: " + textStartDate.getText().toString() + " // start_time: " + timePickerStart.getHour() + ":" + timePickerStart.getMinute() + " // end_time: " + timePickerEnd.getHour() + ":" + timePickerEnd.getMinute());
                } else {
                    Log.d("F1_ACTIVATE", "add event => start_date: " + textStartDate.getText().toString() + " // start_time: " + timePickerStart.getCurrentHour() + ":" + timePickerStart.getCurrentMinute() + " // end_time: " + timePickerEnd.getCurrentHour() + ":" + timePickerEnd.getCurrentMinute());
                }
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
