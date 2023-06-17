package com.example.noisecontrol;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;

import com.example.noisecontrol.model.SoundManager;

///Ta klasa steruje watkami do nagrywania dzieku
public class BackgroundThreadsService extends JobService {
    private static final String TAG = "ExampleJobService";
    private boolean jobCancelled = false;

    private int time = 500; // czas w ms

    private SoundManager soundManager;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Job started");

        soundManager=new SoundManager(this);

        doBackgroundWork(params);


        return true;
    }



    private void doBackgroundWork(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    Log.d(TAG, "run: " + i);

                    if (jobCancelled) {
                        return;
                    }

                    //soundManager.playSound1();

                    soundManager.startRecording();

                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    soundManager.pauseRecording();
                    //soundManager.playSound2();
                    soundManager.playAudio();
                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    soundManager.pausePlaying();
                }

                //soundManager.stopSounds();

                Log.d(TAG, "Job finished");
                jobFinished(params, false);
            }
        }).start();
    }




    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job cancelled before completion");
        jobCancelled = true;
        return true;
    }




}

