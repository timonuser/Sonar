package com.example.michabeczowski.audiosonar;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    Button Graj, Pause;
    TextView Timer;
    Handler Handler = new Handler();
    LinearLayout container;

    long startTime=0L, timeinmiliseconds=0L, timeswapbuff=0L, updatetime=0L;

    Runnable updateTimerThread = new Runnable() {
        @Override
        public void run() {
            timeinmiliseconds = SystemClock.uptimeMillis()-startTime;
            updatetime = timeswapbuff+timeinmiliseconds;
            int secs=(int) (updatetime/1000);
            int mins= secs/60;
            secs%=60;
            int miliseconds=(int)(updatetime%1000);
            Timer.setText(""+mins+":"+String.format("%2d",secs)+":"+String.format("%3d",miliseconds));
            Handler.postDelayed(this,0);
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detect);

        final MediaPlayer mysound = MediaPlayer.create(this, R.raw.chirp2);

        Graj = (Button) this.findViewById(R.id.Graj);
        Pause = (Button) this.findViewById(R.id.Pause);
        Timer = (TextView)findViewById(R.id.timer);
        container = (LinearLayout)findViewById(R.id.container);

        Graj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mysound.start();
                startTime = SystemClock.uptimeMillis();
                Handler.postDelayed(updateTimerThread,0);
            }
        });

        Pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timeswapbuff+=timeinmiliseconds;
                Handler.removeCallbacks(updateTimerThread);

            }
        });




    }
}