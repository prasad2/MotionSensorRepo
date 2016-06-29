package com.coen268.arttherapy;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.widget.Toast;

import java.io.InputStream;

public class SongPlay extends IntentService {


    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
   //  * @param name Used to name the worker thread, important only for debugging.
     */
    public SongPlay() {
        super("Song play");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        MediaPlayer  mp = MediaPlayer.create(SongPlay.this, R.raw.eraser);
        mp.start();

    }




}
