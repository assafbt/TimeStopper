package com.assafbt.studyprojects.timestopper;

// public class MainActivity extends AppCompatActivity {
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private Button startButton;
    private Button stopButton;

    private TextView timerValue;
    private TextView bestValue;


    private long startTime = 0;
    private long bestTime = 5555555;

    private Handler customHandler = new Handler();

    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    int i;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.e("", "error");

        timerValue = (TextView) findViewById(R.id.timerValue);
        bestValue = (TextView) findViewById(R.id.bestValue);

        startButton = (Button) findViewById(R.id.startButton);



        startButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
            //    if (timeInMilliseconds == 0) {
                    startTime = SystemClock.uptimeMillis();
                    customHandler.postDelayed(updateTimerThread, 0);
            //    }
            //    else { // timeInMilliseconds
                    /*    if ("is_running") {

                        }
                    */

            //    } // else timeInMilliseconds


            }

        });

        stopButton = (Button) findViewById(R.id.stopButton);

        stopButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {

                // handle on secound click on stop button


                //timeSwapBuff += timeInMilliseconds;
                customHandler.removeCallbacks(updateTimerThread);

                // check the best time and correct time is not zero
                if ( (bestTime > timeInMilliseconds) && (timeInMilliseconds != 0)) {
                        //  i= i+1;
                        bestTime = timeInMilliseconds;
                int secs = (int) (updatedTime / 1000);
                int mins = secs / 60;
                secs = secs % 60;
                int milliseconds = (int) (updatedTime % 1000);
                bestValue.setText(" " + mins + ":" + String.format("%02d", secs) + ":" + String.format("%03d", milliseconds));




                // bestValue.setText("best " + i);
                }


            }
        });

    }

    private Runnable updateTimerThread = new Runnable() {

        public void run() {

            timeInMilliseconds = SystemClock.uptimeMillis() - startTime;
            

            updatedTime = timeSwapBuff + timeInMilliseconds;

            int secs = (int) (updatedTime / 1000);
            int mins = secs / 60;
            secs = secs % 60;
            int milliseconds = (int) (updatedTime % 1000);
            timerValue.setText(" " + mins + ":" + String.format("%02d", secs) + ":" + String.format("%03d", milliseconds));
            customHandler.postDelayed(this, 0);
           // bestValue.setText("" + mins + ":" + String.format("%02d", secs) + ":" + String.format("%03d", milliseconds));
        }

    };

}