package se.mdh.dva232.project.shutup;

import android.content.Context;
import android.media.AudioManager;
import android.util.Log;

/**
 * That class includes all methods for handling with the AudioManager.
 * @author Sylvio Ujvari
 */
class AudioController {
    private Context context;
    private Boolean debug = true;   // true: with Log.d output; false: without Log.d output

    /**
     * Constructor for these class
     * @param c     Context     context of that application for access to the AudioManager
     */
    AudioController(Context c) {
        if(debug) { Log.d("AUDIOC", "constructor"); }
        context = c;
    }

    /**
     * Set the device to the silent mode dependent on the vibrate settings.
     * @param vibration     Boolean     true: vibration in silent mode; false: no vibration in silent mode
     */
    void setPhoneToSilentMode(Boolean vibration) {
        if(vibration) {
            setVibrateMode();
        } else {
            setSilentMode();
        }
    }

    /**
     * Set the device to the previous sound mode.
     */
    void setPhoneToPreviousMode() {
        setNormalMode();
    }

    /**
     * Get the current sound mode of the device
     * @return      Integer         [0: silent without vibration; 1: silent with vibration; 2: normal sound mode]
     */
    Integer getCurrentSoundMode() {
        if(debug) { Log.d("AUDIOC","getCurrentSoundMode()"); }
        AudioManager AM = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        return AM.getRingerMode();
    }

    /**
     * Set the silent mode.
     */
    private void setSilentMode() {
        if(debug) { Log.d("AUDIOC","setSilentMode()"); }
        AudioManager AM = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if( AM.getRingerMode() != AudioManager.RINGER_MODE_SILENT ) {
            AM.setRingerMode( AudioManager.RINGER_MODE_SILENT);
            if(debug) { logCurrentAudioMode(); }
        } else {
            if(debug) { Log.d("AUDIOC", "no changes"); }
        }
    }

    /**
     * Set the vibrate mode.
     */
    private void setVibrateMode() {
        if(debug) { Log.d("AUDIOC","setVibrationMode()"); }
        AudioManager AM = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if( AM.getRingerMode() != AudioManager.RINGER_MODE_VIBRATE ) {
            AM.setRingerMode( AudioManager.RINGER_MODE_VIBRATE);
            if(debug) { logCurrentAudioMode(); }
        } else {
            if(debug) { Log.d("AUDIOC", "no changes"); }
        }
    }

    /**
     * Set the normal mode.
     */
    private void setNormalMode() {
        if(debug) { Log.d("AUDIOC","setNormalMode()"); }
        AudioManager AM = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        if( AM.getRingerMode() != AudioManager.RINGER_MODE_NORMAL ) {
            AM.setRingerMode( AudioManager.RINGER_MODE_NORMAL);
            if(debug) { logCurrentAudioMode(); }
        } else {
            if(debug) { Log.d("AUDIOC", "no changes"); }
        }
    }

    /**
     * Log the current audio mode of the device.
     */
    private void logCurrentAudioMode() {
        AudioManager AM = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        Log.d("AUDIOC", "Current RingerMode: " + AM.getRingerMode() + " (normal: "+AudioManager.RINGER_MODE_NORMAL+", vibrate: "+AudioManager.RINGER_MODE_VIBRATE+", silent: "+AudioManager.RINGER_MODE_SILENT+")");
    }

}
