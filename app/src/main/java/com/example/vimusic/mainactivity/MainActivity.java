package com.example.vimusic.mainactivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.vimusic.R;
import com.example.vimusic.ui.mediaplayer.MediaPlayerFragment;
import com.example.vimusic.ui.khampha.KhamPhaFragment;
import com.example.vimusic.ui.lovelist.LoveSongFragment;
import com.example.vimusic.ui.search.TimKiemFragment;
import com.example.vimusic.ui.thuvien.library.ThuVienFragment;
import com.example.vimusic.ui.thuvien.song.BaiHatFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    private ThuVienFragment thuVienFragment;
    private BottomSheetBehavior bottomSheetBehavior;
    private CardView bottom_nav_player;
    private MediaPlayerFragment mediaPlayerFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        bottom_nav_player = findViewById(R.id.bottom_nav_player);

        navView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        thuVienFragment = new ThuVienFragment();
        mediaPlayerFragment = new MediaPlayerFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, thuVienFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.host_frame_mediaplayer, mediaPlayerFragment).commit();
        bottomSheetBehavior = BottomSheetBehavior.from(bottom_nav_player);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null ;

                    switch (menuItem.getItemId()){
                        case R.id.navigation_home:
                            selectedFragment = new ThuVienFragment();
                            break;
                        case R.id.navigation_dashboard:
                            selectedFragment = new LoveSongFragment();
                            break;
                        case R.id.navigation_notifications:
                            selectedFragment = new KhamPhaFragment();
                            break;
                        case R.id.navigation_search:
                            selectedFragment = new TimKiemFragment();
                            break;

                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();

                    return true;
                }
            };


}
