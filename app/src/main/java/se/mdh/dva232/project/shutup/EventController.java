package se.mdh.dva232.project.shutup;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Struct for an event
 */
class Event {
    private String id;
    private String startDate;
    private String startTime;
    private String endDate;
    private String endTime;

    Event(String newId, String newStartDate, String newStartTime, String newEndDate, String newEndTime) {
        id = newId;
        startDate = newStartDate;
        startTime = newStartTime;
        endDate = newEndDate;
        endTime = newEndTime;
    }

    String getId() { return id; }
    String getStartDate() { return startDate; }
    String getStartTime() { return startTime; }
    String getEndDate() { return endDate; }
    String getEndTime() { return endTime; }

    Date getEndDateTime() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(getEndDate() + " " + getEndTime() + ":00");
    }

    Date getStartDateTime() throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(getStartDate() + " " + getStartTime() + ":00");
    }

    String getContent() {
        return "id: "+id+" // start: "+startDate+" " +startTime+" // end: "+endDate+" "+endTime;
    }
}

/**
 * That class is the master class of the controllers and functioned as the interface. Only that methods should be call by the main code.
 * @author Sylvio Ujvari
 */
class EventController {
    private Context context;
    private Boolean debug = true;   // true: with Log.d output; false: without Log.d output
    private Event currentEvent = null;
    private AsyncTask runningAsyncTask;

    /**
     * Constructor for these class
     * @param c     Context     context of that application for access to the AudioManager
     */
    EventController(Context c) {
        if(debug) { Log.d("EVENTC", "constructor"); }
        context = c;
    }

    //
    //  public methods
    //

    /**
     * Activate the silent mode from now
     * @param newStartDate     String  Format: "yyyy-MM-dd"        start date of the event
     * @param newStartTime     String  Format: "hh:mm"             start time of the event
     * @param newEndDate       String  Format: "yyyy-MM-dd"        end date of the event
     * @param newEndTime       String  Format: "hh:mm"             end time of the event
     * @param newName          String                              name of the event
     */
    void activateSilentModeFromNow(String newStartDate, String newStartTime, String newEndDate, String newEndTime, String newName) {
        if(debug) { Log.d("EVENTC", "activateSilentModeFromNow(...)"); }

        // create objects
        AudioController AC = new AudioController(context);
        XmlController XC = new XmlController(context);

        // load or create XML content
        if( !XC.readXmlFileAndLoad() ) {
            Log.d("EVENTC","XML file doesn't exist and is created");
            XC.createXmlContentSkeleton();
        } else {
            Log.d("EVENTC","XML file exist and is loaded");
            XC.resetXmlContent();
            XC.createXmlContentSkeleton();
        }
        XC.logCurrentXmlContent();
        String eventId = newStartDate.concat("_").concat(newStartTime.replace(":",""));
        Log.d("EVENTC", "ID of the new event: " + eventId);
        AC.setPhoneToSilentMode(true);  // TODO: replace the parameter with vibration setting
        if ( XC.isCollisionByNewEvent(newStartDate, newStartTime, newEndDate, newEndTime) ) {
            if (debug) { Log.d("EVENTC","activateSilentModeFromNow -> collision detected"); }
        } else {
            if (debug) { Log.d("EVENTC","activateSilentModeFromNow -> no collision detected"); }
            createCurrentEventAndSave(eventId, newStartDate, newStartTime, newEndDate, newEndTime);
            Log.d("EVENTC", "content currentEvent: " + currentEvent.getContent() );
            XC.addEventToXmlContent(eventId, newStartDate, newStartTime, newEndDate, newEndTime, newName);
            XC.saveXmlContentToFile();
            XC.logCurrentXmlContent();
            checkForSwitchToPreviousSoundModeAndDoIt();
            Log.d("EVENTC","after checkForSwitchToPreviousSoundModeAndDoIt()");
        }

    }

    //
    //  private methods
    //

    /**
     * set the current event in the class
     * @param e     Event-Object
     */
    private void setCurrentEvent(Event e) {
        currentEvent = e;
    }

    private Event getCurrentEvent() {
        return currentEvent;
    }

    /**
     * create the current event and save it in the class
     * @param id            String  Format: "yyyy-MM-dd_hhmm"   id of the event
     * @param newStartDate     String  Format: "yyyy-MM-dd"        start date of the event
     * @param newStartTime     String  Format: "hh:mm"             start time of the event
     * @param newEndDate       String  Format: "yyyy-MM-dd"        end date of the event
     * @param newEndTime       String  Format: "hh:mm"             end time of the event
     */
    private void createCurrentEventAndSave(String id, String newStartDate, String newStartTime, String newEndDate, String newEndTime) {
        Event e = new Event(id, newStartDate, newStartTime, newEndDate, newEndTime);
        setCurrentEvent(e);
    }

    private void checkForSwitchToPreviousSoundModeAndDoIt() {
        if(debug) { Log.d("XMLC", "checkForSwitchToPreviousSoundMode()"); }

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final Timer timer = new Timer();
                try {
                    timer.schedule(new TimerTask() {
                        Integer count = 0;
                        Date eventEnd = getCurrentEvent().getEndDateTime();
                        @Override
                        public void run() {
                            Log.d("TIMER", "now: " + Calendar.getInstance().getTime() + " // event-end: " + eventEnd );
                            if(count == 3){ count = count + 1;
                            //if ( eventEnd.before(Calendar.getInstance().getTime()) || eventEnd.equals(Calendar.getInstance().getTime()) ) {
                                Log.d("TIMER","past");
                                timer.cancel();
                                timer.purge();
                            } else {
                                Log.d("TIMER","upcoming");
                            }
                        }
                    }, 5000, 5000);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        Log.d("XMLC","checkForSwitchToPreviousSoundMode() -> end");
    }
}
