package com.example.noisecontrol;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Environment;
import android.util.Log;

import com.example.noisecontrol.model.SoundManager;
import com.example.noisecontrol.model.WavRecorder;

///Ta klasa steruje watkami do nagrywania dzieku
public class BackgroundThreadsService extends JobService {
    private static final String TAG = "ExampleJobService";
    private boolean jobCancelled = false;

    private int time = 500; // czas w ms

    private SoundManager soundManager;
    private WavRecorder wavRecorder;

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Job started");

        soundManager=new SoundManager(this);
        wavRecorder = new WavRecorder(Environment.getExternalStorageDirectory().getPath());

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

                  //  soundManager.startRecording();

wavRecorder.startRecording();


                    try {
                        Thread.sleep(time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                  //  soundManager.pauseRecording();

                    wavRecorder.stopRecording();


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

//////////

//    private boolean checkWritePermission() {
//        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
//        int result2 = ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.RECORD_AUDIO);
//        return result1 == PackageManager.PERMISSION_GRANTED && result2 == PackageManager.PERMISSION_GRANTED ;
//    }
//    private void requestWritePermission() {
//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.RECORD_AUDIO,Manifest.permission.MODIFY_AUDIO_SETTINGS,WRITE_EXTERNAL_STORAGE},1);
//    }


}

