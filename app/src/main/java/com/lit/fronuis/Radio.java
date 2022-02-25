package com.lit.fronuis;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;

/**
 * WDR 2: https://wdr-wdr2-muensterland.icecastssl.wdr.de/wdr/wdr2/muensterland/mp3/128/stream.mp3
 *
 * 1Live: https://wdr-1live-live.icecastssl.wdr.de/wdr/1live/live/mp3/128/stream.mp3
 *
 * DLF Nova: https://st03.sslstream.dlf.de/dlf/03/128/mp3/stream.mp3
 *
 * WDR 5: https://wdr-wdr5-live.icecastssl.wdr.de/wdr/wdr5/live/mp3/128/stream.mp3
 *
 * Antenne Münster: https://antennemuenster--di--nacs-ais-lgc--06--cdn.cast.addradio.de/antennemuenster/live/mp3/high
 *
 */

public class Radio extends AppCompatActivity {
    private Button btn, btn2, btn3, btn4, btn5;
    ArrayList<Button> btnList;

    private boolean playPause;
    private MediaPlayer mediaPlayer;
    private boolean intialStage = true;
    private int cur = 0;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_radio);
        btn = findViewById(R.id.button1);
        btn2 = findViewById(R.id.button2);
        btn3 = findViewById(R.id.button3);
        btn4 = findViewById(R.id.button4);
        btn5 = findViewById(R.id.button5);
        btnList = new ArrayList<>();
        btnList.add(btn);
        btnList.add(btn2);
        btnList.add(btn3);
        btnList.add(btn4);
        btnList.add(btn5);
        for (Button btn: btnList) {
            btn.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_round_play_arrow_24,0,0,0);
            btn.setBackgroundColor(getResources().getColor(R.color.purple_500));
        }
        for(int i = 0; i < btnList.size();i++)
        {
            int finalI = i;
            btnList.get(i).setOnClickListener(v -> {
                if(cur == finalI +1)
                    btnList.get(finalI).setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_round_play_arrow_24,0,0,0);
                else
                {
                    btnList.get(finalI).setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_round_pause_24,0,0,0);
                    btnList.get(finalI).setBackgroundColor(getResources().getColor(R.color.purple_700));
                    if(cur != 0)
                    {
                        btnList.get(cur-1).setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_round_play_arrow_24,0,0,0);
                        btnList.get(cur-1).setBackgroundColor(getResources().getColor(R.color.purple_500));
                    }
                }
                click(finalI +1);
            });
        }
        FloatingActionButton fab = findViewById(R.id.fabR);
        fab.setOnClickListener(view ->
        {
            Intent intent = new Intent(Radio.this, MainActivity.class);
            startActivity(intent);
        });

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    public void click(int num)
    {
        if(cur != num)
        {
            intialStage = true;
            playPause = false;
        }

        if (!playPause) {
            //btn.setBackgroundResource(R.drawable.button_pause);
            if (intialStage) {
                mediaPlayer.reset();
                mediaPlayer.release();
                mediaPlayer = new MediaPlayer();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                switch (num) {
                    case (1):
                        new Player().execute("https://wdr-wdr2-muensterland.icecastssl.wdr.de/wdr/wdr2/muensterland/mp3/128/stream.mp3");
                        break;
                    case (2):
                        new Player().execute("https://wdr-1live-live.icecastssl.wdr.de/wdr/1live/live/mp3/128/stream.mp3");
                        break;
                    case (3):
                        new Player().execute("https://wdr-wdr5-live.icecastssl.wdr.de/wdr/wdr5/live/mp3/128/stream.mp3");
                        break;
                    case (4):
                        new Player().execute("https://st03.sslstream.dlf.de/dlf/03/128/mp3/stream.mp3");
                        break;
                    case (5):
                        new Player().execute("https://antennemuenster--di--nacs-ais-lgc--06--cdn.cast.addradio.de/antennemuenster/live/mp3/high");
                        break;
                }
            }
            else {
                if (!mediaPlayer.isPlaying())
                {
                    btnList.get(num - 1).setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_round_pause_24, 0, 0, 0);
                    btnList.get(num - 1).setBackgroundColor(getResources().getColor(R.color.purple_700));
                    mediaPlayer.start();
                }

            }
            playPause = true;
        } else {
            //btn.setBackgroundResource(R.drawable.button_play);
            if (mediaPlayer.isPlaying())
            {
                btnList.get(num-1).setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_round_play_arrow_24,0,0,0);
                mediaPlayer.pause();
            }
            playPause = false;
        }
        cur = num;
    }

    /**
     * preparing mediaplayer will take sometime to buffer the content so prepare it inside the background thread and starting it on UI thread.
     *
     * @author piyush
     */

    class Player extends AsyncTask<String, Void, Boolean> {
        private ProgressDialog progress;

        @Override
        protected Boolean doInBackground(String... params) {
            // TODO Auto-generated method stub
            Boolean prepared;
            try {

                mediaPlayer.setDataSource(params[0]);

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        // TODO Auto-generated method stub
                        intialStage = true;
                        playPause = false;
                        //btn.setBackgroundResource(R.drawable.button_play);
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                    }
                });
                mediaPlayer.prepare();
                prepared = true;
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                Log.d("IllegarArgument", e.getMessage());
                prepared = false;
                e.printStackTrace();
            } catch (SecurityException e) {
                // TODO Auto-generated catch block
                prepared = false;
                e.printStackTrace();
            } catch (IllegalStateException e) {
                // TODO Auto-generated catch block
                prepared = false;
                e.printStackTrace();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                prepared = false;
                e.printStackTrace();
            }
            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            if (progress.isShowing()) {
                progress.cancel();
            }
            Log.d("Prepared", "//" + result);
            mediaPlayer.start();

            intialStage = false;
        }

        public Player() {
            progress = new ProgressDialog(Radio.this);
        }

        @Override
        protected void onPreExecute() {
            // TODO Auto-generated method stub
            super.onPreExecute();
            this.progress.setMessage("Buffering...");
            this.progress.show();

        }
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        if (mediaPlayer != null) {
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        playPause = false;
        intialStage = true;
        cur = 0;
    }
}