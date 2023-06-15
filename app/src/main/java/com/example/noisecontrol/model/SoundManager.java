package com.example.noisecontrol.model;

import android.content.Context;
import android.media.MediaPlayer;

import com.example.noisecontrol.R;

public class SoundManager {

    private MediaPlayer mp1;
    private MediaPlayer mp2;

   private Context context;


    public SoundManager(Context context) {
        this.context = context;
        mp1 = MediaPlayer.create(context, R.raw.piano1);;
        mp2 = MediaPlayer.create(context, R.raw.piano2);
    }










    public void stopSounds() {
if(mp1.isPlaying()|| mp2.isPlaying()){
    mp1.stop();
    mp2.stop();
}
    }


    public void playSound1() {

if(!mp1.isPlaying()){
    mp1.start();
}

    }

    public void playSound2() {

        if(!mp2.isPlaying()){
            mp2.start();
        }

    }




}
