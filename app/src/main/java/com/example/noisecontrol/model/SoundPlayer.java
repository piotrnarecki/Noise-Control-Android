package com.example.noisecontrol.model;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Environment;
import android.util.Log;

import java.io.IOException;

public class SoundPlayer {


    private Context context;
    private MediaPlayer mediaPlayer;

    private SoundProcessor soundProcessor;


    public SoundPlayer(Context context) {
        this.context = context;
        this.soundProcessor = new SoundProcessor();
    }

    public void playAudio() {

        mediaPlayer = new MediaPlayer();
        try {

            soundProcessor.processFile(Environment.getExternalStorageDirectory().getPath() + "/final_record.wav");

            mediaPlayer.setDataSource(Environment.getExternalStorageDirectory().getPath() + "/inverted_record.wav");

            mediaPlayer.prepare();

            mediaPlayer.start();

        } catch (IOException e) {
            Log.e("TAG", "prepare() failed");
        }
    }


    public void pausePlaying() {

        mediaPlayer.release();
        mediaPlayer = null;

    }


}
