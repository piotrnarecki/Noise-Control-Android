package com.example.noisecontrol;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Initializing all variables..
    private TextView startTV, stopTV, playTV, stopplayTV, statusTV;

    // creating a variable for media recorder object class.
    private MediaRecorder mRecorder;

    // creating a variable for mediaplayer class
    private MediaPlayer mPlayer;

    // string variable is created for storing a file name
    private static String mFileName = null;

    // constant for storing audio permission
    public static final int REQUEST_AUDIO_PERMISSION_CODE = 1;

    ////backgroind task

    private static final String TAG = "MainActivity";

    // threads

    private Button buttonStartThread;

    private Handler mainHandler = new Handler();

    private volatile boolean stopThread = false;


    //my solution



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize all variables with their layout items.
        statusTV = findViewById(R.id.idTVstatus);
        startTV = findViewById(R.id.btnRecord);
        stopTV = findViewById(R.id.btnStop);
        playTV = findViewById(R.id.btnPlay);
        stopplayTV = findViewById(R.id.btnStopPlay);
        stopTV.setBackgroundColor(getResources().getColor(R.color.gray));
        playTV.setBackgroundColor(getResources().getColor(R.color.gray));
        stopplayTV.setBackgroundColor(getResources().getColor(R.color.gray));


        //thread

        buttonStartThread = findViewById(R.id.button_start_thread);

        startTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // start recording method will
                // start the recording of audio.

                //startRecording();
            }
        });
        stopTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pause Recording method will
                // pause the recording of audio.

                //pauseRecording();

            }
        });
        playTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // play audio method will play
                // the audio which we have recorded

                //playAudio();
            }
        });
        stopplayTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // pause play method will
                // pause the play of audio

                //pausePlaying();
            }
        });
    }


//background


    //metody dla przyciskow dla background
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

        playTV.setBackgroundColor(getResources().getColor(R.color.purple_700));
    }

    public void cancelJob(View v) {
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
        Log.d(TAG, "Job cancelled");
    }

/////thread part

    public void startThread(View view) {
        stopThread = false;
        ExampleRunnable runnable = new ExampleRunnable(10);
        new Thread(runnable).start();
        /*
        ExampleThread thread = new ExampleThread(10);
        thread.start();
        */
        /*
        new Thread(new Runnable() {
            @Override
            public void run() {
                //work
            }
        }).start();
        */
    }

    public void stopThread(View view) {
        stopThread = true;
    }

    class ExampleThread extends Thread {
        int seconds;

        ExampleThread(int seconds) {
            this.seconds = seconds;
        }

        @Override
        public void run() {
            for (int i = 0; i < seconds; i++) {
                Log.d(TAG, "startThread: " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class ExampleRunnable implements Runnable {
        int seconds;

        ExampleRunnable(int seconds) {
            this.seconds = seconds;
        }

        @Override
        public void run() {
            for (int i = 0; i < seconds; i++) {
                if (stopThread)
                    return;
                if (i == 5) {
                    /*
                    Handler threadHandler = new Handler(Looper.getMainLooper());
                    threadHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            buttonStartThread.setText("50%");
                        }
                    });
                    */
                    /*
                    buttonStartThread.post(new Runnable() {
                        @Override
                        public void run() {
                            buttonStartThread.setText("50%");
                        }
                    });
                    */
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            buttonStartThread.setText("50%");
                        }
                    });
                }

                if (i == 9) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            buttonStartThread.setText("start");
                        }
                    });
                }

                Log.d(TAG, "startThread: " + i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }






//////////AUDIO PART
//
//    private void startRecording() {
//        // check permission method is used to check
//        // that the user has granted permission
//        // to record and store the audio.
//        if (CheckPermissions()) {
//
//            // setbackgroundcolor method will change
//            // the background color of text view.
//            stopTV.setBackgroundColor(getResources().getColor(R.color.purple_200));
//            startTV.setBackgroundColor(getResources().getColor(R.color.gray));
//            playTV.setBackgroundColor(getResources().getColor(R.color.gray));
//            stopplayTV.setBackgroundColor(getResources().getColor(R.color.gray));
//
//            // we are here initializing our filename variable
//            // with the path of the recorded audio file.
//            mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
//            mFileName += "/AudioRecording.3gp";
//
//            // below method is used to initialize
//            // the media recorder class
//            mRecorder = new MediaRecorder();
//
//            // below method is used to set the audio
//            // source which we are using a mic.
//            mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
//
//            // below method is used to set
//            // the output format of the audio.
//            mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
//
//            // below method is used to set the
//            // audio encoder for our recorded audio.
//            mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
//
//            // below method is used to set the
//            // output file location for our recorded audio
//            mRecorder.setOutputFile(mFileName);
//            try {
//                // below method will prepare
//                // our audio recorder class
//                mRecorder.prepare();
//            } catch (IOException e) {
//                Log.e("TAG", "prepare() failed");
//            }
//            // start method will start
//            // the audio recording.
//            mRecorder.start();
//            statusTV.setText("Recording Started");
//        } else {
//            // if audio recording permissions are
//            // not granted by user below method will
//            // ask for runtime permission for mic and storage.
//            RequestPermissions();
//        }
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
//        // this method is called when user will
//        // grant the permission for audio recording.
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case REQUEST_AUDIO_PERMISSION_CODE:
//                if (grantResults.length > 0) {
//                    boolean permissionToRecord = grantResults[0] == PackageManager.PERMISSION_GRANTED;
//                    boolean permissionToStore = grantResults[1] == PackageManager.PERMISSION_GRANTED;
//                    if (permissionToRecord && permissionToStore) {
//                        Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Permission Denied", Toast.LENGTH_LONG).show();
//                    }
//                }
//                break;
//        }
//    }
//
//    public boolean CheckPermissions() {
//        // this method is used to check permission
//        int result = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
//        int result1 = ContextCompat.checkSelfPermission(getApplicationContext(), RECORD_AUDIO);
//        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED;
//    }
//
//    private void RequestPermissions() {
//        // this method is used to request the
//        // permission for audio recording and storage.
//        ActivityCompat.requestPermissions(MainActivity.this, new String[]{RECORD_AUDIO, WRITE_EXTERNAL_STORAGE}, REQUEST_AUDIO_PERMISSION_CODE);
//    }
//
//
//    public void playAudio() {
//        stopTV.setBackgroundColor(getResources().getColor(R.color.gray));
//        startTV.setBackgroundColor(getResources().getColor(R.color.purple_200));
//        playTV.setBackgroundColor(getResources().getColor(R.color.gray));
//        stopplayTV.setBackgroundColor(getResources().getColor(R.color.purple_200));
//
//        // for playing our recorded audio
//        // we are using media player class.
//        mPlayer = new MediaPlayer();
//        try {
//            // below method is used to set the
//            // data source which will be our file name
//            mPlayer.setDataSource(mFileName);
//
//            // below method will prepare our media player
//            mPlayer.prepare();
//
//            // below method will start our media player.
//            mPlayer.start();
//            statusTV.setText("Recording Started Playing");
//        } catch (IOException e) {
//            Log.e("TAG", "prepare() failed");
//        }
//    }
//
//    public void pauseRecording() {
//        stopTV.setBackgroundColor(getResources().getColor(R.color.gray));
//        startTV.setBackgroundColor(getResources().getColor(R.color.purple_200));
//        playTV.setBackgroundColor(getResources().getColor(R.color.purple_200));
//        stopplayTV.setBackgroundColor(getResources().getColor(R.color.purple_200));
//
//        // below method will stop
//        // the audio recording.
//        mRecorder.stop();
//
//        // below method will release
//        // the media recorder class.
//        mRecorder.release();
//        mRecorder = null;
//        statusTV.setText("Recording Stopped");
//    }
//
//    public void pausePlaying() {
//        // this method will release the media player
//        // class and pause the playing of our recorded audio.
//        mPlayer.release();
//        mPlayer = null;
//        stopTV.setBackgroundColor(getResources().getColor(R.color.gray));
//        startTV.setBackgroundColor(getResources().getColor(R.color.purple_200));
//        playTV.setBackgroundColor(getResources().getColor(R.color.purple_200));
//        stopplayTV.setBackgroundColor(getResources().getColor(R.color.gray));
//        statusTV.setText("Recording Play Stopped");
//    }
}
