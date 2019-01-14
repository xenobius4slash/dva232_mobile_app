package se.mdh.dva232.project.shutup;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.Date;

public class Fragment1 extends Fragment {

    private static Boolean debugLifecycle = false;

    public Fragment1() {
        // Required empty public constructor
    }

    public static Fragment1 newInstance() {
        if (debugLifecycle) { Log.d("LIFECYCLE_F1", "newInstance()"); }
        Fragment1 fragment = new Fragment1();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (debugLifecycle) { Log.d("LIFECYCLE_F1", "onCreate"); }
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        if (debugLifecycle) { Log.d("LIFECYCLE_F1", "onCreateView(...)"); }
        // Inflate the layout for this fragment
        final View rootView = inflater.inflate(R.layout.fragment_fragment1, container, false);

        final EventController EC = new EventController( getContext() );

        /*
         * TimePicker
         */
        final TextView textNextDay = rootView.findViewById(R.id.f1_text_end_time_next_day);
        final TimePicker timePickerStart = rootView.findViewById(R.id.f1_timePicker_start);
        final TimePicker timePickerEnd = rootView.findViewById(R.id.f1_timePicker_end);

        timePickerStart.setIs24HourView(true);
        timePickerStart.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                setVisibilityTextByTimePicker(timePickerStart, timePickerEnd, textNextDay);
            }
        });

        timePickerEnd.setIs24HourView(true);
        timePickerEnd.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                setVisibilityTextByTimePicker(timePickerStart, timePickerEnd, textNextDay);
            }
        });


        /*
         * Select the date
         */
        //set initial time to the actual time
        final SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        final Calendar startDateCal = Calendar.getInstance();
        final TextView textStartDate = rootView.findViewById(R.id.f1_text_start_date);
        textStartDate.setText( sdfDate.format(Calendar.getInstance().getTime()) );

        // button for set the start date
        Button btnStartDate = rootView.findViewById(R.id.f1_btn_start_date);
        btnStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(
                        getContext(),                               // context of DatePicker
                        new DatePickerDialog.OnDateSetListener() {  // Listener of DatePicker
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                startDateCal.set(year, month, dayOfMonth);
                                textStartDate.setText( sdfDate.format( startDateCal.getTime() ) );
                            }
                        },
                        Calendar.getInstance().get(Calendar.YEAR),              // default year of DatePicker
                        Calendar.getInstance().get(Calendar.MONTH),             // default month of DatPicker
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)       // default day of DatePicker
                ).show();
            }
        });


        /*
         * Event name
         */
        final EditText editTextEventName = rootView.findViewById(R.id.f1_edittext_event_name);


        /*
         * Button for activation
         */
        Button btnActivate = rootView.findViewById(R.id.f1_btn_activate);
        btnActivate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String startTime;
                String endTime;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if ( timePickerStart.getMinute() < 10 ) {
                        startTime = timePickerStart.getHour()+":0"+timePickerStart.getMinute();
                    } else {
                        startTime = timePickerStart.getHour()+":"+timePickerStart.getMinute();
                    }
                    if ( timePickerEnd.getMinute() < 10 ) {
                        endTime = timePickerEnd.getHour()+":0"+timePickerEnd.getMinute();
                    } else {
                        endTime = timePickerEnd.getHour()+":"+timePickerEnd.getMinute();
                    }
                    Log.d("F1_ACTIVATE", "add event => start_date: " + sdfDate.format(startDateCal.getTime()) + " // start_time: " + timePickerStart.getHour() + ":" + timePickerStart.getMinute() + " // end_time: " + timePickerEnd.getHour() + ":" + timePickerEnd.getMinute());
                } else {
                    if ( timePickerStart.getCurrentMinute() < 10 ) {
                        startTime = timePickerStart.getCurrentHour()+":0"+timePickerStart.getCurrentMinute();
                    } else {
                        startTime = timePickerStart.getCurrentHour()+":"+timePickerStart.getCurrentMinute();
                    }
                    if ( timePickerEnd.getCurrentMinute() < 10 ) {
                        endTime = timePickerEnd.getCurrentHour()+":0"+timePickerEnd.getCurrentMinute();
                    } else {
                        endTime = timePickerEnd.getCurrentHour()+":"+timePickerEnd.getCurrentMinute();
                    }
                    Log.d("F1_ACTIVATE", "add event => start_date: " + sdfDate.format(startDateCal.getTime()) + " // start_time: " + timePickerStart.getCurrentHour() + ":" + timePickerStart.getCurrentMinute() + " // end_time: " + timePickerEnd.getCurrentHour() + ":" + timePickerEnd.getCurrentMinute());
                }
                doActivateSilentMode(EC, startDateCal.getTime(), startTime, endTime, editTextEventName.getText().toString() );
            }
        });

        return rootView;
    }



    @Override
    public void onStart() {
        if (debugLifecycle) { Log.d("LIFECYCLE_F1", "onStart"); }
        super.onStart();
    }

    @Override
    public void onResume() {
        if (debugLifecycle) { Log.d("LIFECYCLE_F1", "onResume"); }
        super.onResume();
    }

    @Override
    public void onPause() {
        if (debugLifecycle) { Log.d("LIFECYCLE_F1", "onPause"); }
        super.onPause();
    }

    @Override
    public void onStop() {
        if (debugLifecycle) { Log.d("LIFECYCLE_F1", "onStop"); }
        super.onStop();
    }

    @Override
    public void onDestroy() {
        if (debugLifecycle) { Log.d("LIFECYCLE_F1", "onDestroy"); }
        super.onDestroy();
    }

    /**
     * show or hide the text which indicated that the end time is on the next day
     * @param timePickerStart   TimePicker      for the start time
     * @param timePickerEnd     TimePicker      for the end time
     * @param textNextDay       TextView        hint "next day"
     */
    void setVisibilityTextByTimePicker(TimePicker timePickerStart, TimePicker timePickerEnd, TextView textNextDay) {
        Integer startTimeHour, startTimeMinute, endTimeHour, endTimeMinute;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            startTimeHour = timePickerStart.getHour();
            startTimeMinute = timePickerStart.getMinute();
            endTimeHour = timePickerEnd.getHour();
            endTimeMinute = timePickerEnd.getMinute();
        } else {
            startTimeHour = timePickerStart.getCurrentHour();
            startTimeMinute = timePickerStart.getCurrentMinute();
            endTimeHour = timePickerEnd.getCurrentHour();
            endTimeMinute = timePickerEnd.getCurrentMinute();
        }
        if ( startTimeHour > endTimeHour ) {
            textNextDay.setText(R.string.f1_text_end_time_next_day_string);
            textNextDay.setVisibility(View.VISIBLE);
        } else if( (startTimeHour == endTimeHour) && (startTimeMinute > endTimeMinute) ) {
            textNextDay.setText(R.string.f1_text_end_time_next_day_string);
            textNextDay.setVisibility(View.VISIBLE);
        } else {
            textNextDay.setVisibility(View.GONE);
        }
    }

    /**
     * prepare the inputs for calling the activate silent mode function regarding the mode (for duration or until time)
     * @param EC                EventController
     * @param startDate         Date                    Date object
     * @param startTime         String                  String of start time
     * @param endTime           String                  String of end time
     * @param eventName         String                  name of the event
     */
    private void doActivateSilentMode(EventController EC, Date startDate, String startTime, String endTime, String eventName) {
        Log.d("F1_ACTIVATE","doActivateSilentMode(..., "+startDate+", "+startTime+", "+endTime+", "+eventName+")");

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdfDateTimeOutput = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        try {
            Date startDateTime = sdfDateTime.parse( sdfDate.format(startDate)+" "+startTime+":00");
            Date endDateTime = sdfDateTime.parse( sdfDate.format(startDate)+" "+endTime+":00");

            if (startDateTime.before(Calendar.getInstance().getTime()) || startDateTime.equals(Calendar.getInstance().getTime())) {
                Toast.makeText(getContext(), "Please select a later start time", Toast.LENGTH_SHORT).show();
            } else {
                // check for selected end time is less than the start time -> add one day to end date
                if (startDateTime.equals(endDateTime) || startDateTime.after(endDateTime)) {
                    Calendar temp = Calendar.getInstance();
                    temp.setTime(endDateTime);
                    temp.add(Calendar.DAY_OF_MONTH, 1);
                    endDateTime = temp.getTime();
                    Log.d("F1_ACTIVATE", "add one day -> start: " + sdfDateTime.format(startDateTime) + " // end: " + sdfDateTime.format(endDateTime));
                } else {
                    Log.d("F1_ACTIVATE", "start: " + sdfDateTime.format(startDateTime) + " // end: " + sdfDateTime.format(endDateTime));
                }

                Log.d("F1_ACTIVATE", "EC.activateSilentModeByStartAndEnd( " + sdfDate.format(startDateTime) + ", " + sdfTime.format(startDateTime) + ", " + sdfDate.format(endDateTime) + ", " + sdfTime.format(endDateTime) + ")");
                if (EC.activateSilentModeByStartAndEnd(sdfDate.format(startDateTime), sdfTime.format(startDateTime), sdfDate.format(endDateTime), sdfTime.format(endDateTime), eventName)) {
                    Toast.makeText(getContext(), "Event is added and starts on " + sdfDateTimeOutput.format(startDateTime), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getContext(), "Event is not added, because there are collisions", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
