package com.ceibalabs.alerttimer;

import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    SeekBar timerSeekBar;
    TextView timerTextView;
    Button controllerButton;
    Boolean counterIsActive = false;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.timerSeekBar = (SeekBar) findViewById(R.id.timerSeekBar);
        this.timerTextView = (TextView) findViewById(R.id.timerTextView);
        this.controllerButton = (Button) findViewById(R.id.controllerButton);

        this.timerSeekBar.setMax(600);
        this.timerSeekBar.setProgress(30);

        this.timerSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                updateTimer(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public void updateTimer(int secondsLeft){
        int minutes = (int) secondsLeft / 60;
        int seconds = secondsLeft - minutes*60;

        String secondString = Integer.toString(seconds);
        if (seconds <= 9) {
            secondString = "0" + secondString;
        }

        this.timerTextView.setText(Integer.toString(minutes) + ":" + secondString);
    }

    public void resetTimer(){
        this.timerTextView.setText("0:30");
        this.timerSeekBar.setProgress(30);
        this.countDownTimer.cancel();
        this.timerSeekBar.setEnabled(true);
        this.controllerButton.setText("Start");
        this.counterIsActive = false;
    }

    public void controlTimer(View view){

        if(this.counterIsActive == false){ //Start button
            this.counterIsActive = true;
            this.timerSeekBar.setEnabled(false);
            this.controllerButton.setText("Stop");

            this.countDownTimer = new CountDownTimer(this.timerSeekBar.getProgress() * 1000 + 100, 1000){

                @Override
                public void onTick(long millisUntilFinished) {
                    updateTimer((int) millisUntilFinished / 1000);
                }

                @Override
                public void onFinish() {
                    //Log.i("finished", "timer done");
                    resetTimer();
                    timerTextView.setText("0:00");
                    MediaPlayer mplayer = MediaPlayer.create(getApplicationContext(), R.raw.bells); //we use getApplicationContext() instead of this, because
                    //this makes reference of the  object in which we are "CountDownTimer"
                    mplayer.start();
                }
            }.start();

        } else { //Stop button
            this.resetTimer();
        }
    }
}
