package com.example.vimusic;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.vimusic.mainactivity.MainActivity;

import java.util.Timer;
import java.util.TimerTask;

public class WellcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome);
        // ẨN THANH ĐIỀU HƯỚNG

        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION|
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                startNewActivity(MainActivity.class);
                finish();
            }
        },2000);
    }

    public void startNewActivity(Class target) {
        Intent intent = new Intent(this, target);
        startActivity(intent);
    }
}
