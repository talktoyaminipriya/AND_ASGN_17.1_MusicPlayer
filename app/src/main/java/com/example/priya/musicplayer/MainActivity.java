package com.example.priya.musicplayer;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements Runnable {

    private Button play,stop,pause;
    private SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    private Thread thread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        play = (Button) findViewById(R.id.play);
        stop= (Button) findViewById(R.id.stop);
       pause = (Button) findViewById(R.id.pause);
        seekBar = (SeekBar) findViewById(R.id.seekbar);

        // returns an instance of MediaPlayer class
        mediaPlayer = MediaPlayer.create(this.getBaseContext(),R.raw.charlie);

        setupListeners();
        thread = new Thread(this);
        thread.start();
    }

    private void setupListeners(){

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // to start music
                mediaPlayer.start();
                Toast.makeText(getApplicationContext(),"Music Started",Toast.LENGTH_SHORT).show();


            }
        });

        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // to pause the music
                mediaPlayer.pause();
                Toast.makeText(getApplicationContext(),"Music Paused",Toast.LENGTH_SHORT).show();

            }
        });

        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // to stop music
                mediaPlayer.stop();
                mediaPlayer = MediaPlayer.create(getBaseContext(),R.raw.charlie);
                Toast.makeText(getApplicationContext(),"Music Stopped",Toast.LENGTH_SHORT).show();

            }
        });

        // seekbar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                if(fromUser){
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                           }
        });

    }

    @Override
    public void run() {
        int currentPosition = 0;
        int soundTotal = mediaPlayer.getDuration();
        seekBar.setMax(soundTotal);

        while (mediaPlayer != null && currentPosition < soundTotal) {
            try {

                Thread.sleep(300);
                currentPosition = mediaPlayer.getCurrentPosition();
            } catch (InterruptedException soundException) {

                return;
            } catch (Exception otherException) {

                return;
            }
            seekBar.setProgress(currentPosition);
        }
    }



}


