package com.example.vimusic;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
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
import java.util.Timer;
import java.util.TimerTask;

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
        PhatHanh();

        //------------


        moving();
        pcwelcom();

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

    public void moving() {
        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(logowelcome, "translationY", 0, -50f);
        objectAnimatorX.setDuration(1000);
        objectAnimatorX.start();
    }

    private void pcwelcom() {
        progressBar = findViewById(R.id.progressBarLoading);
        objectAnimator = ObjectAnimator.ofInt(progressBar, "progress", 0, 100);
    }


    @Override
    public void DoStuff() {
        welcomePresenter.getMusic();
    }


    @Override
    public void getMusic() {

        List<BaiHat> baiHatList = new ArrayList<>();

        List<BaiHat> check = baiHatDAO.getAllSong();

        ContentResolver contentResolver = getContentResolver();

        Uri songUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;

        Cursor cursor = contentResolver.query(songUri, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {

            int location = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            int title = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int artist = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int album = cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM);

            do {
                BaiHat baiHat = new BaiHat();
                baiHat.location = cursor.getString(location);
                baiHat.title = cursor.getString(title);
                baiHat.artist = cursor.getString(artist);
                baiHat.album = cursor.getString(album);
                baiHatList.add(baiHat);


                if (check.size() == 0) {
                    long result = baiHatDAO.insertBook(baiHat);
                    if (result > 0) {
                        Log.e("DONE DATABASE", "OK");
                    } else {
                        Log.e("DONE DATABASE", "FAIL");
                    }
                } else if (check.size() > 0) {
                    long result = baiHatDAO.updateSongHi(baiHat);
                    if (result > 0) {
                        Log.e("DONE DATABASE", "OK");
                    } else {
                        Log.e("DONE DATABASE", "FAIL");
                    }
                }


            } while (cursor.moveToNext());
        }

    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSION_REQUEST: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                        Toast.makeText(this, "Permissions Granted", Toast.LENGTH_SHORT).show();
                        welcomePresenter.DoStuff();
                    }
                } else {
                    Toast.makeText(this, "No Permissions", Toast.LENGTH_SHORT).show();
                    finish();
                }
                return;
            }
        }
    }

    public void PhatHanh() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSION_REQUEST);
            }
        } else {
            welcomePresenter.DoStuff();
        }
    }
}
