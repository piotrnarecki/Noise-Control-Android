package com.example.noisecontrol;


import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView settingsButton, startButton, stopButton, statusText;
    private ImageView statusDot;
    public static final int REQUEST_AUDIO_PERMISSION_CODE = 1;
    private static final String TAG = "MainActivity";

    private boolean isRunning = false;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        statusDot = findViewById(R.id.statusDot);
        statusText = findViewById(R.id.statusText);
        startButton = findViewById(R.id.startBtn);
        stopButton = findViewById(R.id.stopBtn);
        settingsButton = findViewById(R.id.settingsBtn);


        refreshUI(isRunning);


        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!isRunning) {
                    isRunning = true;
                    scheduleJob(v);
                }

                refreshUI(isRunning);


            }
        });
        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isRunning) {
                    isRunning = false;
                    cancelJob(v);
                }

                refreshUI(isRunning);


            }
        });

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//open settings

                Toast.makeText(getApplicationContext(), "Settings in construction", Toast.LENGTH_LONG).show();


                refreshUI(isRunning);


            }
        });

    }


    public void scheduleJob(View v) {
        ComponentName componentName = new ComponentName(this, BackgroundThreadsService.class);
        JobInfo info = new JobInfo.Builder(123, componentName)
                .setPersisted(true)
                .setPeriodic(15 * 60 * 1000)
                .build();

        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job scheduled");

            System.out.println("WORKING");


        } else {
            Log.d(TAG, "Job scheduling failed");
        }


    }

    public void cancelJob(View v) {
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
        Log.d(TAG, "Job cancelled");

    }


    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // this method is called when user will
        // grant the permission for audio recording.
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_AUDIO_PERMISSION_CODE:
                if (grantResults.length > 0) {
                    boolean permissionToRecord = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean permissionToStore = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (permissionToRecord && permissionToStore) {
                        Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_LONG).show();
                    }
                }
                break;
        }
    }


    public void refreshUI(boolean isRunning) {

        if (isRunning) {
            startButton.setBackground(getDrawable(R.drawable.rounded_corners_transparent));
            startButton.setEnabled(false);

            stopButton.setBackground(getDrawable(R.drawable.rounded_corners_red));
            stopButton.setEnabled(true);

            settingsButton.setBackground(getDrawable(R.drawable.rounded_corners_transparent));
            settingsButton.setEnabled(false);

            statusDot.setBackground(getDrawable(R.drawable.dot_on));

            statusText.setText(getResources().getText(R.string.status_running));

            startStatusDotAnimation();

        } else {

            startButton.setBackground(getDrawable(R.drawable.rounded_corners_green));
            startButton.setEnabled(true);

            stopButton.setBackground(getDrawable(R.drawable.rounded_corners_transparent));
            stopButton.setEnabled(false);

            settingsButton.setBackground(getDrawable(R.drawable.rounded_corners_grey));

            settingsButton.setEnabled(true);

            statusDot.setBackground(getDrawable(R.drawable.dot_off));

            statusText.setText(getResources().getText(R.string.status_cancelled));


            statusDot.clearAnimation();


        }


    }


    public void startStatusDotAnimation() {

        Animation animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(400); //You can manage the blinking time with this parameter
        animation.setStartOffset(20);
        animation.setRepeatMode(Animation.REVERSE);
        animation.setRepeatCount(Animation.INFINITE);

        if (isRunning) {
            statusDot.startAnimation(animation);
        } else {
            statusDot.animate().cancel();
        }

    }
}
