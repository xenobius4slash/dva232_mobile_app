package se.mdh.dva232.project.shutup;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.util.Log;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Struct for an event
 * @author Sylvio Ujvari
 */
class Event {
    private String id;
    private String idSemaphore;
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
        idSemaphore = newId + "#" + getRandomSalt();
        startDate = newStartDate;
        startTime = newStartTime;
        endDate = newEndDate;
        endTime = newEndTime;
        Log.d("EVENTC_EVENT", "create Event("+id+" ("+idSemaphore+"), "+startDate+", "+startTime+", "+endDate+", "+endTime+")");
    }

    String getId() { return id; }
    String getIdSemaphore() { return idSemaphore; }
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

    /**
     * generates a random salt for the event id which is uses in the semaphore
     * @return      Integer     random salt
     */
    private static Integer getRandomSalt() {
        Random salt = new Random();
        salt.setSeed( Calendar.getInstance().getTimeInMillis() );
        return salt.nextInt(100000);
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
     * Task which have to run at the start
     * 1. reset semaphore
     * 2. remove all past events
     * 3. check for upcoming events and if one is present start the timer (async task)
     */
    void initTask() {
        Boolean delXml = false;

        if(debug) { Log.d("EVENTC", "initTask()"); }
        SharedPreferences.Editor settingsEditor = settings.edit();
        settingsEditor.putString("semaphore_async_task", null);
        settingsEditor.putBoolean("kill_all_async_tasks", true);
        settingsEditor.apply();
        // wait that time all async tasks are notice the kill signal and disable it afterwards
        SystemClock.sleep(2000);
        settingsEditor.putBoolean("kill_all_async_tasks", false);
        settingsEditor.apply();
        Log.d("EVENTC", "[ASYNCTASKS] kill_all_async_tasks -> TRUE -> FALSE");

        if (delXml) {
            XmlController XC = new XmlController(context);
            if (XC.readXmlFileAndLoad()) {
                Log.d("EVENTC","XML files deleted");
                XC.resetXmlContent();             // TODO: remove
                XC.createXmlContentSkeleton();    // TODO: remove
                XC.saveXmlContentToFile();        // TODO: remove
            }
        }
        runOpenTasks();
    }

    private void preemptRunningTaskAndRunOpenTask() {
        Log.d("EVENTC","[ASYNCTASK] preemptRunningTaskAndRunOpenTask() --> current semaphore content: " + settings.getAll().get("semaphore_async_task"));
        SharedPreferences.Editor settingsEditor = settings.edit();
        settingsEditor.putString("semaphore_async_task", null);
        settingsEditor.apply();
        SystemClock.sleep(1000);
        runOpenTasks();
    }

    private void runOpenTasks() {
        if(debug) { Log.d("EVENTC", "runOpenTasks()"); }
        XmlController XC = new XmlController(context);
        if (XC.readXmlFileAndLoad()) {
            XC.logCurrentXmlContent();
            if ( XC.removeAllPastEventsFromXmlContent() ) {
                XC.saveXmlContentToFile();
            }
            setCurrentEvent( XC.getFirstUpcommingEvent() );
            if ( getCurrentEvent().existEvent() ) {
                Log.d("EVENTC", "upcoming events existing");
                checkForSwitchToSilentSoundModeAndDoIt(true);
            }
            else { Log.d("EVENTC", "no events upcoming events are found"); }
            XC.logCurrentXmlContent();
        }
        else { Log.d("EVENTC", "there are no XML file"); }
    }

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
            doSilentMode();
            XC = new XmlController(context);

            // load or create XML content
            if (!XC.readXmlFileAndLoad()) {
                XC.createXmlContentSkeleton();
            }
            XC.logCurrentXmlContent();
            String eventId = newStartDate.concat("_").concat(newStartTime.replace(":", ""));
            if (XC.isCollisionByNewEvent(newStartDate, newStartTime, newEndDate, newEndTime)) {
                if (debug) { Log.d("EVENTC", "activateSilentModeFromNow -> collision detected"); }
                Toast.makeText(context, "Collision exist -> modify upcomming events", Toast.LENGTH_SHORT).show();
                XC.modifySavedEventsRegardingCollisionInXmlContent(newStartDate, newStartTime, newEndDate, newEndTime);
                XC.addEventToXmlContent(eventId, newStartDate, newStartTime, newEndDate, newEndTime, "Current");
                XC.saveXmlContentToFile();
                XC.logCurrentXmlContent();
                checkForSwitchToPreviousSoundModeAndDoIt();     // Async Task
            } else {
                if (debug) { Log.d("EVENTC", "activateSilentModeFromNow -> no collision detected"); }
                createCurrentEventAndSave(eventId, newStartDate, newStartTime, newEndDate, newEndTime);
                XC.addEventToXmlContent(eventId, newStartDate, newStartTime, newEndDate, newEndTime, "Current");
                XC.saveXmlContentToFile();
                XC.logCurrentXmlContent();
                checkForSwitchToPreviousSoundModeAndDoIt();     // Async Task
            }
        }
    }

    /**
     * Activate the silent mode by specified start (not now) and end --> ONLY for Fragment 1
     * @param newStartDate     String  Format: "yyyy-MM-dd"        start date of the event
     * @param newStartTime     String  Format: "hh:mm:ss"          start time of the event
     * @param newEndDate       String  Format: "yyyy-MM-dd"        end date of the event
     * @param newEndTime       String  Format: "hh:mm:ss"          end time of the event
     * @param eventName        String                              name of the event
     * @return      Boolean
     */
    Boolean activateSilentModeByStartAndEnd(String newStartDate, String newStartTime, String newEndDate, String newEndTime, String eventName) {
        if(debug) { Log.d("EVENTC", "activateSilentModeByStartAndEnd("+newStartDate+", "+newStartTime+", "+newEndDate+", "+newEndTime+", "+eventName+")"); }
        XC = new XmlController(context);

        // load or create XML content
        if (!XC.readXmlFileAndLoad()) {
            XC.createXmlContentSkeleton();
        }
        XC.logCurrentXmlContent();
        String eventId = newStartDate.concat("_").concat(newStartTime.replace(":", ""));
        if (XC.isCollisionByNewEvent(newStartDate, newStartTime, newEndDate, newEndTime)) {
            if (debug) { Log.d("EVENTC", "activateSilentModeByStartAndEnd -> collision detected"); }
            return false;
        } else {
            if (debug) { Log.d("EVENTC", "activateSilentModeByStartAndEnd -> no collision detected"); }
            createCurrentEventAndSave(eventId, newStartDate, newStartTime, newEndDate, newEndTime);
            XC.addEventToXmlContent(eventId, newStartDate, newStartTime, newEndDate, newEndTime, eventName);
            XC.saveXmlContentToFile();
            XC.logCurrentXmlContent();
            checkForSwitchToSilentSoundModeAndDoIt(false);       // Async Task
            Log.d("EVENTC", "after checkForSwitchToSilentSoundModeAndDoIt()");
            return true;
        }
    }

    /**
     * Get the current sound mode of the device by using the Audio-Controller
     * @return      Integer         [0: silent without vibration; 1: silent with vibration; 2: normal sound mode]
     */
    Integer getCurrentSoundMode() {
        AudioController AC = new AudioController(context);
        return AC.getCurrentSoundMode();
    }

    ArrayList<EventOutput> getAllSavedEvents() {
        if(debug) { Log.d("EVENTC", "getAllSavedEvents()"); }
        XmlController XC = new XmlController(context);
        if (!XC.readXmlFileAndLoad()) {
            Log.d("EVENTC", "there are no saved events");
            return null;
        } else {
            XC.logCurrentXmlContent();
            return XC.getAllEventsFromTheXmlContent();
        }
    }

    void deleteEventById(String eventId) {
        if(debug) { Log.d("EVENTC", "deleteEventById()"); }
        XmlController XC = new XmlController( context );
        // load or create XML content
        if (XC.readXmlFileAndLoad()) {
            XC.logCurrentXmlContent();
            Log.d("EVENTC", "deactivateSilentMode() -> XC.removeEventFromXmlContent(" + eventId + ")");
            XC.removeEventFromXmlContent(eventId);
            XC.saveXmlContentToFile();
            XC.logCurrentXmlContent();
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
     * initialize the current event with NULL and save it in the class
     */
    private void initCurrentEventAndSave() {
        Event e = new Event();
        setCurrentEvent(e);
    }

    private Boolean existCurrentEvent() {
        if (currentEvent == null) {
            return false;
        } else if (getCurrentEvent().existEvent()) {
            return true;
        } else {
            return false;
        }
    }

    private Boolean existUpcomingEvent() {
        if (debug) { Log.d("EVENTC", "existUpcomingEvent()"); }
        XC = new XmlController(context);
        // load or create XML content
        if (!XC.readXmlFileAndLoad()) {
            return false;
        } else {
            setCurrentEvent( XC.getFirstUpcommingEvent() );
            return getCurrentEvent().existEvent();
        }
    }

    /**
     * checks for switching back to the previous sound mode regarding the current event and call the deactivation
     */
    private void checkForSwitchToPreviousSoundModeAndDoIt() {
        if(debug) { Log.d("EVENTC", "checkForSwitchToPreviousSoundModeAndDoIt()"); }
        if (existCurrentEvent()) {
            if ( !getCurrentEvent().getIdSemaphore().equals(settings.getString("semaphore_async_task", null)) && settings.getString("semaphore_async_task", null) != null) {
                // Semaphore in use --> preemption of running task
                Log.d("EVENTC", "[ASYNCTASK] (toPrev) preempt running task by " + getCurrentEvent().getIdSemaphore() );
                Log.d("EVENTC", "[ASYNCTASK] (toPrev) current semaphore content: " + settings.getAll().get("semaphore_async_task"));
                preemptRunningTaskAndRunOpenTask();
            } else if ( settings.getString("semaphore_async_task", null) == null ) {
                // semaphore is free
                Log.d("EVENTC", "[ASYNCTASK] (toPrev) semaphore is free task " + getCurrentEvent().getIdSemaphore() + " can run");
                Log.d("EVENTC", "[ASYNCTASK] (toPrev) set semaphore to: " + getCurrentEvent().getIdSemaphore());
                final SharedPreferences.Editor settingsEditor = settings.edit();
                settingsEditor.putString("semaphore_async_task", getCurrentEvent().getIdSemaphore());
                settingsEditor.apply();

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
                                    if (eventEnd.before(Calendar.getInstance().getTime()) || eventEnd.equals(Calendar.getInstance().getTime()) || !settings.getBoolean("silent_mode_active", false) ) {
                                        // cancel timer and delete running event if end time for silent mode is arrived/past or the silent mode is canceled by the user
                                        timer.cancel();
                                        timer.purge();
                                        Log.d("TIMER", "stop timer by end of the event or canceling by user");
                                        Log.d("EVENTC", "[ASYNCTASK] (toPrev) free semaphore (old: " + getCurrentEvent().getIdSemaphore() + ")");
                                        settingsEditor.putString("semaphore_async_task", null);
                                        settingsEditor.apply();
                                        deactivateSilentMode();
                                        Log.d("EVENTC","[ASYNCTASK] (toPrev) end of the event or canceling by user --> runOpenTasks()");
                                        runOpenTasks();
                                    } else if ( !getCurrentEvent().getIdSemaphore().equals(settings.getString("semaphore_async_task", null)) ) {
                                        // preemption by another task
                                        timer.cancel();
                                        timer.purge();
                                        Log.d("TIMER", " (toPrev) stop timer preemption");
                                        Log.d("EVENTC","[ASYNCTASK] (toPrev) stop by preemption --> runOpenTasks()");
//                                        runOpenTasks();
                                    } else {
                                        Log.d("TIMER", "(toPrev) switch to previous tick: " + tick + " -> timer for event-id '" + getCurrentEvent().getId() + "' is running (now: " + Calendar.getInstance().getTime() + " // event-end: " + eventEnd + ")");
                                    }
                                }
                            }, 1000, 1000);     // TODO: modify period and delay depending on the time till end -> period from big to small (60 sec -> 30 sec -> 10 sec -> 1 sec)
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
        Log.d("EVENTC","checkForSwitchToPreviousSoundMode() -> end -> asyncrone process should run");
    }

    /**
     * Process for deactivating a current silent mode
     */
    private void deactivateSilentMode() {
        if (debug) { Log.d("EVENTC", "deactivateSilentMode()"); }
        doPreviousSoundMode();

        //remove event in XML
        Log.d("EVENTC", "deactivateSilentMode() -> id: " + getCurrentEvent().getId() );
        if (getCurrentEvent().existEvent()) {
            XmlController XC = new XmlController( context );
            // load or create XML content
            if (!XC.readXmlFileAndLoad()) {
                XC.createXmlContentSkeleton();
            }
            XC.logCurrentXmlContent();
            Log.d("EVENTC", "deactivateSilentMode() -> XC.removeEventFromXmlContent("+getCurrentEvent().getId()+")");
            XC.removeEventFromXmlContent(getCurrentEvent().getId());
            XC.saveXmlContentToFile();
            XC.logCurrentXmlContent();

            // delete event in Event Controller
            deleteCurrentEvent();
        }
    }

    /**
     * Deactivate the current silent mode and switch to previous sound mode
     */
    private void doPreviousSoundMode() {
        AudioController AC = new AudioController(context);
        AC.setPhoneToPreviousMode();
        SharedPreferences.Editor settingsEditor = settings.edit();
        settingsEditor.putBoolean("silent_mode_active", false);
        settingsEditor.apply();
    }

    /**
     * checks for switching to the silent sound mode regarding the current event and call the activation
     */
    private void checkForSwitchToSilentSoundModeAndDoIt(final Boolean initTask) {
        if(debug) { Log.d("EVENTC", "checkForSwitchToSilentSoundModeAndDoIt("+initTask+")"); }
        if (existCurrentEvent()) {
            if ( !getCurrentEvent().getIdSemaphore().equals(settings.getString("semaphore_async_task", null)) && settings.getString("semaphore_async_task", null) != null) {
                // Semaphore in use --> preemption of running task
                Log.d("EVENTC", "[ASYNCTASK] (toSilent) preempt running task by " + getCurrentEvent().getIdSemaphore() );
                Log.d("EVENTC", "[ASYNCTASK] (toSilent) current semaphore content: " + settings.getAll().get("semaphore_async_task"));
                preemptRunningTaskAndRunOpenTask();
            } else if ( settings.getString("semaphore_async_task", null) == null ) {
                // semaphore is free
                Log.d("EVENTC", "[ASYNCTASK] (toPrev) semaphore is free task " + getCurrentEvent().getIdSemaphore() + " can run");
                Log.d("EVENTC", "[ASYNCTASK] (toPrev) set semaphore to: " + getCurrentEvent().getIdSemaphore());
                final SharedPreferences.Editor settingsEditor = settings.edit();
                settingsEditor.putString("semaphore_async_task", getCurrentEvent().getIdSemaphore());
                settingsEditor.apply();

                AsyncTask.execute(new Runnable() {
                    @Override
                    public void run() {
                        final Timer timer = new Timer();
                        try {
                            timer.schedule(new TimerTask() {
                                Integer tick = 0;
                                Date eventStart = getCurrentEvent().getStartDateTime();
                                @Override
                                public void run() {
                                    tick = tick + 1;
                                    if (eventStart.equals(Calendar.getInstance().getTime()) || eventStart.before(Calendar.getInstance().getTime())) {
                                        // cancel timer and activate the silent mode if start time for silent mode is arrived/past
                                        timer.cancel();
                                        timer.purge();
                                        Log.d("EVENTC", "[ASYNCTASK] (toSilent) free semaphore (old: " + getCurrentEvent().getIdSemaphore() + ")");
                                        settingsEditor.putString("semaphore_async_task", null);
                                        settingsEditor.apply();
                                        Log.d("TIMER", "stop timer by start of the event");
                                        activateSilentMode();
                                    }else if ( !getCurrentEvent().getIdSemaphore().equals(settings.getString("semaphore_async_task", null)) ) {
                                        // preemption by another task
                                        timer.cancel();
                                        timer.purge();
                                        Log.d("TIMER", "(toSilent) stop timer preemption");
                                        Log.d("EVENTC","[ASYNCTASK] (toSilent) stop by preemption --> runOpenTasks()");
//                                        runOpenTasks();
                                    } else {
                                        Log.d("TIMER", "(toSilent) switch to silent tick: " + tick + " -> timer for event-id '" + getCurrentEvent().getId() + "' is running (now: " + Calendar.getInstance().getTime() + " // event-start: " + eventStart + ")");
                                    }
                                }
                            }, 1000, 1000);     // TODO: modify period and delay depending on the time till start -> period from big to small (60 sec -> 30 sec -> 10 sec -> 1 sec)
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    /**
     * Process for activate silent mode
     */
    private void activateSilentMode() {
        if (debug) { Log.d("EVENTC", "activateSilentMode()"); }
        doSilentMode();
        checkForSwitchToPreviousSoundModeAndDoIt();     // Async Task
    }

    /**
     * Activate silent mode and set setting
     */
    private void doSilentMode() {
        if (debug) { Log.d("EVENTC", "doSilentMode()"); }
        AudioController AC = new AudioController(context);
        AC.setPhoneToSilentMode((Boolean) settings.getAll().get("vibration"));
        SharedPreferences.Editor settingsEditor = settings.edit();
        settingsEditor.putBoolean("silent_mode_active", true);
        settingsEditor.apply();
    }
}
