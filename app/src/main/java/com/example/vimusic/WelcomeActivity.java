package com.example.vimusic;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.vimusic.dao.BaiHatDAO;
import com.example.vimusic.mainactivity.MainActivity;
import com.example.vimusic.model.BaiHat;

import java.util.ArrayList;
import java.util.List;

public class WelcomeActivity extends AppCompatActivity implements WelcomeView {

    private ImageView logowelcome;
    private ProgressBar progressBar;
    private ObjectAnimator objectAnimator;

    private WelcomePresenter welcomePresenter;

    private BaiHatDAO baiHatDAO;
    private static final int MY_PERMISSION_REQUEST = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcome);
        logowelcome = findViewById(R.id.logowelcome);
        // ẨN THANH ĐIỀU HƯỚNG
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                        View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        welcomePresenter = new WelcomePresenter(this);
        baiHatDAO = new BaiHatDAO(this);

        //----------------------------------------------------

        Moving();
        PcWelcome();

        objectAnimator.setDuration(2000);
        objectAnimator.start();

        objectAnimator.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                startNewActivity(MainActivity.class);
                finish();
            }
        });

    }

    public void startNewActivity(Class target) {
        Intent intent = new Intent(this, target);
        startActivity(intent);
    }

    public void Moving() {
        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(logowelcome, "translationY", 0, -50f);
        objectAnimatorX.setDuration(1000);
        objectAnimatorX.start();
    }

    private void PcWelcome() {
        progressBar = findViewById(R.id.progressBarLoading);
        objectAnimator = ObjectAnimator.ofInt(progressBar, "progress", 0, 100);
    }

}
