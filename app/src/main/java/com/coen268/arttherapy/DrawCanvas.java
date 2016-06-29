package com.coen268.arttherapy;

import java.util.List;
import java.util.UUID;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class DrawCanvas extends ActionBarActivity {

    private DrawView drawView;
    private ShakeListener myShaker;
    float mg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);

        drawView = (DrawView)findViewById(R.id.drawing);

        myShaker = new ShakeListener(this);
        myShaker.setOnShakeListener(new ShakeListener.OnShakeListener(){




            public void onShake(){
                float mg = drawView.checksum();
                if(mg > 0.0){
                Intent intent = new Intent(DrawCanvas.this,SongPlay.class);
                startService(intent);
                drawView.erase();
                Toast.makeText(DrawCanvas.this, "Shaken", Toast.LENGTH_SHORT).show();
                stopService(intent);
                }
                else{
                    Toast.makeText(DrawCanvas.this,"Nothing to erase",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_canvas, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.uninstall) {
            Intent intent = new Intent(Intent.ACTION_DELETE);
            intent.setData(Uri.parse("package:" + "com.coen268.arttherapy"));
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
