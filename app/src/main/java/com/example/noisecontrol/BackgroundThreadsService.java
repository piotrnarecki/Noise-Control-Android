package com.example.noisecontrol;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Environment;
import android.util.Log;

import com.example.noisecontrol.model.SoundPlayer;
import com.example.noisecontrol.model.SoundRecorder;

public class BackgroundThreadsService extends JobService {
    private static final String TAG = "ExampleJobService";
    private boolean jobCancelled = false;

    private int time = 400;

    private SoundPlayer soundPlayer;
    private SoundRecorder soundRecorder;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Job started");

        soundPlayer = new SoundPlayer(this);
        soundRecorder = new SoundRecorder(Environment.getExternalStorageDirectory().getPath());

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

                    soundRecorder.startRecording();

                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    soundRecorder.stopRecording();
                    soundPlayer.playAudio();

                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    soundPlayer.pausePlaying();
                }

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

