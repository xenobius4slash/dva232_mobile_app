package se.mdh.dva232.project.shutup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

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

    /**
     * constructor with no parameter
     */
    Event() {
        id = null;
        startDate = null;
        startTime = null;
        endDate = null;
        endTime = null;
    }

    /**
     * constructor with parameter
     * @param newId             String  Format: "yyyy-MM-dd_HHmmss"     ID for the XML
     * @param newStartDate      String  Format: "yyyy-MM-dd"            start date of the event
     * @param newStartTime      String  Format: "HH:mm:ss"              start time of the event
     * @param newEndDate        String  Format: "yyyy-MM-dd"            end date of the event
     * @param newEndTime        String  Format: "HH:mm:ss"              end time of the event
     */
    Event(String newId, String newStartDate, String newStartTime, String newEndDate, String newEndTime) {
        id = newId;
        startDate = newStartDate;
        startTime = newStartTime;
        endDate = newEndDate;
        endTime = newEndTime;
    }

    String getId() { return id; }
    private String getStartDate() { return startDate; }
    private String getStartTime() { return startTime; }
    private String getEndDate() { return endDate; }
    private String getEndTime() { return endTime; }

    Date getEndDateTime() throws ParseException {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(getEndDate() + " " + getEndTime());
    }

    Date getStartDateTime() throws ParseException {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.parse(getStartDate() + " " + getStartTime());
    }

    String getContent() {
        return "id: "+id+" // start: "+startDate+" " +startTime+" // end: "+endDate+" "+endTime;
    }

    Boolean existEvent() {
        if ( id == null && startDate == null && startTime == null && endDate == null && endTime == null) {
            return false;
        } else {
            return true;
        }
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
    private SharedPreferences settings;
    private XmlController XC = null;

    /**
     * Constructor for these class
     * @param c     Context     context of that application for access to the AudioManager
     */
    EventController(Context c) {
        if(debug) { Log.d("EVENTC", "constructor"); }
        context = c;
        settings = context.getSharedPreferences("UserInfo", 0);
    }

    //
    //  public methods
    //

    /**
     * Activate the silent mode from now --> ONLY for Fragment 0
     * @param newStartDate     String  Format: "yyyy-MM-dd"        start date of the event
     * @param newStartTime     String  Format: "hh:mm:ss"          start time of the event
     * @param newEndDate       String  Format: "yyyy-MM-dd"        end date of the event
     * @param newEndTime       String  Format: "hh:mm:ss"          end time of the event
     */
    void activateSilentModeFromNow(String newStartDate, String newStartTime, String newEndDate, String newEndTime) {
        if(debug) { Log.d("EVENTC", "activateSilentModeFromNow("+newStartDate+", "+newStartTime+", "+newEndDate+", "+newEndTime+")"); }

        if ( settings.getBoolean("silent_mode_active", false) ) {
            Toast.makeText(context, "currently the silent mode is active", Toast.LENGTH_SHORT).show();
        } else {
            // create objects
            AudioController AC = new AudioController(context);
            XC = new XmlController(context);

            // first step: switch device to silent mode and set the global setting
            AC.setPhoneToSilentMode((Boolean) settings.getAll().get("vibration"));
            SharedPreferences.Editor settingsEditor = settings.edit();
            settingsEditor.putBoolean("silent_mode_active", true);
            settingsEditor.apply();

            // load or create XML content
            if (!XC.readXmlFileAndLoad()) {
                Log.d("EVENTC", "XML file doesn't exist and is created");
                XC.createXmlContentSkeleton();
            } else {
                Log.d("EVENTC", "XML file exist and is loaded");
                XC.resetXmlContent();           // TODO: remove
                XC.createXmlContentSkeleton();  // TODO: remove
            }
            XC.logCurrentXmlContent();
            String eventId = newStartDate.concat("_").concat(newStartTime.replace(":", ""));
            Log.d("EVENTC", "ID of the new event: " + eventId);
            if (XC.isCollisionByNewEvent(newStartDate, newStartTime, newEndDate, newEndTime)) {
                if (debug) { Log.d("EVENTC", "activateSilentModeFromNow -> collision detected"); }
            } else {
                if (debug) { Log.d("EVENTC", "activateSilentModeFromNow -> no collision detected"); }
                createCurrentEventAndSave(eventId, newStartDate, newStartTime, newEndDate, newEndTime);
                Log.d("EVENTC", "content currentEvent: " + currentEvent.getContent());
                XC.addEventToXmlContent(eventId, newStartDate, newStartTime, newEndDate, newEndTime, "Current");
                XC.saveXmlContentToFile();
                XC.logCurrentXmlContent();
                Log.d("EVENTC", "currentEvent before check: " + getCurrentEvent().getContent() );
                checkForSwitchToPreviousSoundModeAndDoIt();
                Log.d("EVENTC", "after checkForSwitchToPreviousSoundModeAndDoIt()");
            }
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

    /**
     * get the current event in the class
     * @return      Event       Struct
     */
    private Event getCurrentEvent() {
        return currentEvent;
    }

    /**
     * delete the content of the current event
     */
    private void deleteCurrentEvent() {
        currentEvent = null;
    }

    /**
     * create the current event and save it in the class
     * @param id            String  Format: "yyyy-MM-dd_hhmm"       id of the event
     * @param newStartDate     String  Format: "yyyy-MM-dd"         start date of the event
     * @param newStartTime     String  Format: "hh:mm:ss"           start time of the event
     * @param newEndDate       String  Format: "yyyy-MM-dd"         end date of the event
     * @param newEndTime       String  Format: "hh:mm:ss"           end time of the event
     */
    private void createCurrentEventAndSave(String id, String newStartDate, String newStartTime, String newEndDate, String newEndTime) {
        Event e = new Event(id, newStartDate, newStartTime, newEndDate, newEndTime);
        setCurrentEvent(e);
    }

    /**
     * checks for switching back to the previous sound mode regarding the current event and call the deactivation
     */
    private void checkForSwitchToPreviousSoundModeAndDoIt() {
        if(debug) { Log.d("EVENTC", "checkForSwitchToPreviousSoundModeAndDoIt()"); }
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                final Timer timer = new Timer();
                try {
                    timer.schedule(new TimerTask() {
                        Integer tick = 0;
                        Date eventEnd = getCurrentEvent().getEndDateTime();
                        @Override
                        public void run() {
                            tick = tick + 1;
                            if ( eventEnd.before(Calendar.getInstance().getTime()) || eventEnd.equals(Calendar.getInstance().getTime()) || !settings.getBoolean("silent_mode_active",false)) {
                                timer.cancel();
                                timer.purge();
                                Log.d("TIMER","stop timer by end of event");
                                deactivateSilentMode();
                            } else {
                                Log.d("TIMER","tick: "+tick+" -> timer is running (now: " + Calendar.getInstance().getTime() + " // event-end: " + eventEnd + ")");
                            }
                        }
                    }, 1000, 1000);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });
        Log.d("EVENTC","checkForSwitchToPreviousSoundMode() -> end -> asyncrone process should run");
    }

    /**
     * deactivate a current silent mode
     */
    private void deactivateSilentMode() {
        if (debug) { Log.d("EVENTC", "deactivateSilentMode()"); }
        AudioController AC = new AudioController(context);

        // switch device to the previous sound mode
        AC.setPhoneToPreviousMode();
        SharedPreferences.Editor settingsEditor = settings.edit();
        settingsEditor.putBoolean("silent_mode_active", false);
        settingsEditor.apply();

        //remove event in XML
        Log.d("EVENTC", "deactivateSilentMode() -> id: " + getCurrentEvent().getId() );
        if (getCurrentEvent().existEvent()) {
            XC.logCurrentXmlContent();
            XC.removeEventFromXmlContent(getCurrentEvent().getId());
            XC.saveXmlContentToFile();
            XC.logCurrentXmlContent();

            // delete event in Event Controller
            deleteCurrentEvent();
        }
    }
}
