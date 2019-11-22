package com.example.vimusic;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.vimusic.ui.thuvien.ThuVienFragment;

public class MediaPlayerActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private ThuVienFragment thuVienFragment;
    private TextView tvPlayerTenBaiHat, tvPlayerTenCaSi;
    private String location ;
    private String title;
    private String artist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);

        tvPlayerTenBaiHat = findViewById(R.id.tvPlayerTenBaiHat);
        tvPlayerTenCaSi = findViewById(R.id.tvPlayerTenCaSi);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        thuVienFragment = new ThuVienFragment();

        Intent intent = this.getIntent();

        location = intent.getStringExtra("location");
        title = intent.getStringExtra("name");
        artist = intent.getStringExtra("artist");


    }

    @Override
    protected void onResume() {
        super.onResume();

        tvPlayerTenBaiHat.setText(title);
        tvPlayerTenCaSi.setText(artist);

        try {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer.reset();
        } catch (Exception e) {
            e.printStackTrace();
        }

        mediaPlayer = MediaPlayer.create(MediaPlayerActivity.this, Uri.parse(location));

        mediaPlayer.start();
    }

    public void btndown(View view) {

    }
}
