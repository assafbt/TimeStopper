package com.assafbt.studyprojects.timestopper;

// public class MainActivity extends AppCompatActivity {
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends Activity {

    private Button leftBtn;
    private Button rightBtn;

    private TextView timerValue;
    private TextView bestValue;

    private boolean isRunning = false;

    Random rn;
    int rand;

    private long startTime = 0;
    private long bestTime = 000000;

    private Handler customHandler = new Handler();

    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;

    int secs, mins, milliseconds;

    int i;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // logs
        Log.e("", "error");

        SharedPreferences prefs = getSharedPreferences("best", MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();

        // Initializes buttons
        leftBtn = (Button) findViewById(R.id.rightButton);
        rightBtn = (Button) findViewById(R.id.leftButton);
        leftBtn.setText("1");
        rightBtn.setText("2");



        // Initializes text view
        timerValue = (TextView) findViewById(R.id.timerValue);
        bestValue = (TextView) findViewById(R.id.bestValue);
        bestValue.setText(prefs.getLong("best", bestTime)+"");
        bestTime = prefs.getLong("best", bestTime);


        secs = (int) (bestTime / 1000);
        mins = secs / 60;
        secs = secs % 60;
        milliseconds = (int) (bestTime % 1000);
        bestValue.setText(" " + mins + ":" + String.format("%02d", secs) + ":" + String.format("%03d", milliseconds));




        // set left button
        leftBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (leftBtn.getText() == "1")
                    start();
                else
                    stop(editor);
            }
        });


        // set right button
        rightBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (rightBtn.getText() == "1")
                    start();
                else
                    stop(editor);
            }


        });




        // click on best record text view
        bestValue.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                if (isRunning) {// running
                    // do nothing
                }
                else { //not running

                    bestTime = 0;
                    editor.putLong("best", bestTime);
                    editor.apply();

                    secs = (int) (bestTime / 1000);
                    mins = secs / 60;
                    secs = secs % 60;
                    milliseconds = (int) (bestTime % 1000);

                    bestValue.setText(" " + mins + ":" + String.format("%02d", secs) + ":" + String.format("%03d", milliseconds));

                }


            }
        });

    }

    // start method
    private void start(){
        if (!isRunning) {
            isRunning = true;
            startTime = SystemClock.uptimeMillis();
            customHandler.postDelayed(updateTimerThread, 0);
        }
        else {
            //do nothing;
        }


    }

    // stop method
    private void stop(SharedPreferences.Editor editor){
        customHandler.removeCallbacks(updateTimerThread);
        if (isRunning) {//running
            isRunning = false;
            if ((bestTime > updatedTime) || (bestTime== 0)) { //check the best
                bestTime = updatedTime;

                // update best time for layout case
                editor.putLong("best", bestTime);
                editor.apply();

                secs = (int) (updatedTime / 1000);
                mins = secs / 60;
                secs = secs % 60;
                milliseconds = (int) (updatedTime % 1000);

                bestValue.setText(" " + mins + ":" + String.format("%02d", secs) + ":" + String.format("%03d", milliseconds));
            }
        }

        // random the buttons
        rn = new Random();
        rand = rn.nextInt(2);
        if(rand==1) {
                leftBtn.setText("1");
                rightBtn.setText("2");
            }
            else {
                leftBtn.setText("2");
                rightBtn.setText("1");
            }




    }


    // time running
    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;

            updatedTime = timeSwapBuff + timeInMilliseconds;

           secs = (int) (updatedTime / 1000);
           mins = secs / 60;
            secs = secs % 60;
           milliseconds = (int) (updatedTime % 1000);
            timerValue.setText(" " + mins + ":" + String.format("%02d", secs) + ":" + String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);

        }

    };



}