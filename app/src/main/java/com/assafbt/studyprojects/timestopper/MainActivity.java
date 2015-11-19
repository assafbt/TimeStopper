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
    private boolean isStopped = true;

    Random rn;
    int rand;

    private long startTime = 0;
    private long bestTime = 5555555;

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

        Log.e("", "error");

        SharedPreferences prefs = getSharedPreferences("best", MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();


        leftBtn = (Button) findViewById(R.id.rightButton);
        rightBtn = (Button) findViewById(R.id.leftButton);
        leftBtn.setText("1");
        rightBtn.setText("2");




        timerValue = (TextView) findViewById(R.id.timerValue);
        bestValue = (TextView) findViewById(R.id.bestValue);
        bestValue.setText(prefs.getLong("best", bestTime)+"");
        bestTime = prefs.getLong("best", bestTime);

        secs = (int) (bestTime / 1000);
        mins = secs / 60;
        secs = secs % 60;
        milliseconds = (int) (bestTime % 1000);
        bestValue.setText(" " + mins + ":" + String.format("%02d", secs) + ":" + String.format("%03d", milliseconds));





        leftBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (leftBtn.getText() == "1")
                    start();
                else
                    stop(editor);
            }
        });


        rightBtn.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                if (rightBtn.getText() == "1")
                    start();
                else
                    stop(editor);
            }


        });





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

    private void start(){
        if (!isRunning) {
            isRunning = true;
            startTime = SystemClock.uptimeMillis();
            customHandler.postDelayed(updateTimerThread, 0);
            //    }
            //    else { // timeInMilliseconds
                    /*    if ("is_running") {

                        }
                    */

            //    } // else timeInMilliseconds
        } else {
            //do nothing;
        }


    }

    private void stop(SharedPreferences.Editor editor){
        customHandler.removeCallbacks(updateTimerThread);
        if (isRunning) {//running
            isRunning = false;
            if ((bestTime > updatedTime) || (bestTime== 0)) { //change best
                bestTime = updatedTime;

                editor.putLong("best", bestTime);
                editor.apply();

                secs = (int) (updatedTime / 1000);
                mins = secs / 60;
                secs = secs % 60;
                milliseconds = (int) (updatedTime % 1000);

                bestValue.setText(" " + mins + ":" + String.format("%02d", secs) + ":" + String.format("%03d", milliseconds));
                // bestValue.setText("best " + i);
            }
        }

        //}
        rn = new Random();
        rand = rn.nextInt(2);
        if(rand==1) {              //t=1: change button, t=2: nothing change
           // if (leftBtn.getId() == R.id.leftButton) {
                //leftBtn = (Button) findViewById(R.id.rightButton);
               // rightBtn = (Button) findViewById(R.id.leftButton);
                leftBtn.setText("1");
                rightBtn.setText("2");
            }
            else {
                //leftBtn = (Button) findViewById(R.id.leftButton);
               // rightBtn = (Button) findViewById(R.id.rightButton);
                leftBtn.setText("2");
                rightBtn.setText("1");
            }




    }



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
           // bestValue.setText("" + mins + ":" + String.format("%02d", secs) + ":" + String.format("%03d", milliseconds));
        }

    };



}