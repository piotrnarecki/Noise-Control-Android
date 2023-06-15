package com.example.noisecontrol.model;

import static android.Manifest.permission.RECORD_AUDIO;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.example.noisecontrol.MainActivity;
import com.example.noisecontrol.R;

import java.io.IOException;

public class SoundManager {

    private MediaPlayer mp1;
    private MediaPlayer mp2;

    private Context context;

    public MediaRecorder getMediaRecorder() {
        return mediaRecorder;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }


    //RECORDING

    // creating a variable for media recorder object class.
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;

    private static String recordingFilename = null;
    public static final int REQUEST_AUDIO_PERMISSION_CODE = 1;


    public SoundManager(Context context) {
        this.context = context;
        mp1 = MediaPlayer.create(context, R.raw.piano1);
        mp2 = MediaPlayer.create(context, R.raw.piano2);

//        mediaRecorder=new MediaRecorder();
//        mediaPlayer=new MediaPlayer();

    }


    public void stopSounds() {
        if (mp1.isPlaying() || mp2.isPlaying()) {
            mp1.stop();
            mp2.stop();
        }
    }


    public void playSound1() {

        if (!mp1.isPlaying()) {
            mp1.start();
        }

    }

    public void playSound2() {

        if (!mp2.isPlaying()) {
            mp2.start();
        }

    }


    public void startRecording() {

//        if (CheckPermissions()) {

        if (true) {
            recordingFilename = Environment.getExternalStorageDirectory().getAbsolutePath();
            recordingFilename += "/AudioRecording.3gp";

            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);

            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

            mediaRecorder.setOutputFile(recordingFilename);
            try {
                mediaRecorder.prepare();
            } catch (IOException e) {
                Log.e("TAG", "prepare() failed");
            }

            mediaRecorder.start();


        } else {
            RequestPermissions();
        }
    }

    public void RequestPermissions() {

        ActivityCompat.requestPermissions(MainActivity.getInstance(), new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, REQUEST_AUDIO_PERMISSION_CODE);
    }

//    private boolean CheckPermissions() {
//
//        return MainActivity.getInstance().CheckPermissions();
//
//    }


    public void playAudio() {

        mediaPlayer = new MediaPlayer();
        try {

            mediaPlayer.setDataSource(recordingFilename);

            mediaPlayer.prepare();

            mediaPlayer.start();

        } catch (IOException e) {
            Log.e("TAG", "prepare() failed");
        }
    }

    public void pauseRecording() {

        mediaRecorder.stop();

        mediaRecorder.release();

        mediaRecorder = null;
    }

    public void pausePlaying() {

        mediaPlayer.release();
        mediaPlayer = null;

    }


}
