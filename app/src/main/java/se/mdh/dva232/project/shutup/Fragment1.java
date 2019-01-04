package se.mdh.dva232.project.shutup;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.LoginFilter;
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

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        Log.d("LIFECYCLE Fragment1", "onCreateView(...)");
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_fragment1, container, false);
        final Calendar myCalendar = Calendar.getInstance();

        Spinner spinner = rootView.findViewById(R.id.spinner);
        ArrayAdapter <CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),R.array.Select,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //set initial time to the actual time
        TextView text = rootView.findViewById(R.id.textView8);
        text.setText(myCalendar.get(Calendar.DAY_OF_MONTH)+"-"+ myCalendar.get(Calendar.MONTH+1)+"-"+ myCalendar.get(Calendar.YEAR));


        //DatePickerDialog sets the time the user selects
        final DatePickerDialog.OnDateSetListener dialog = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                TextView text = rootView.findViewById(R.id.textView8);

                text.setText(dayOfMonth+"-"+(month+1)+"-"+year);

            }
        };

        //create button for the setOnClickListener
        Button button = rootView.findViewById(R.id.button_date);

        //show the DatePicker dialog with the actual time values
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new DatePickerDialog( getContext(), dialog, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });



        //code to get values of TimePicker
        start_hour = myCalendar.get(Calendar.HOUR_OF_DAY);
        start_minute = myCalendar.get(Calendar.MINUTE);

        end_hour = myCalendar.get(Calendar.HOUR_OF_DAY);
        end_minute = myCalendar.get(Calendar.MINUTE);


        TimePicker timePicker1 = rootView.findViewById(R.id.timePicker);
        timePicker1.setHour(this.start_hour);
        timePicker1.setMinute(this.start_minute);
        timePicker1.setIs24HourView(true);


        timePicker1.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Log.d("timepicker1","changing timepicker 1");
            }
        });

        TimePicker timePicker2 = rootView.findViewById(R.id.timePicker2);
        timePicker2.setHour(this.end_hour);
        timePicker2.setMinute(this.end_minute);
        timePicker2.setIs24HourView(true);

        timePicker2.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                Log.d("timepicker2","changing timepicker 2");
            }
        });





        timePicker1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("timePicker_1","setting datePicker 1: ");
            }
        });

        timePicker2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("timePicker_2","setting datePicker 2: ");
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
