package com.example.vimusic.mainactivity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.vimusic.R;
import com.example.vimusic.dao.BaiHatDAO;
import com.example.vimusic.databinding.ActivityMainBinding;
import com.example.vimusic.model.BaiHat;
import com.example.vimusic.model.BindingModel;
import com.example.vimusic.ui.mediaplayer.BottomMediaPlayerFragment;
import com.example.vimusic.ui.mediaplayer.MediaPlayerFragment;
import com.example.vimusic.ui.khampha.KhamPhaFragment;
import com.example.vimusic.ui.lovelist.LoveSongFragment;
import com.example.vimusic.ui.search.TimKiemFragment;
import com.example.vimusic.ui.thuvien.library.ThuVienFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements MainView {

    private ThuVienFragment thuVienFragment;
    private BottomSheetBehavior bottomSheetBehavior;
    private CardView bottom_nav_player;
    private MediaPlayerFragment mediaPlayerFragment;
    private FrameLayout fragment_container;
    private BottomMediaPlayerFragment bottomMediaPlayerFragment;

    private MainPresenter mainPresenter;

    private FrameLayout MediaPlayTAB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        MediaPlayTAB = findViewById(R.id.MediaPlayTAB);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        BottomNavigationView navView = findViewById(R.id.nav_view);
        bottom_nav_player = findViewById(R.id.bottom_nav_player);
        bottomMediaPlayerFragment = new BottomMediaPlayerFragment();

        navView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);

        thuVienFragment = new ThuVienFragment();
        mediaPlayerFragment = new MediaPlayerFragment();

        mainPresenter = new MainPresenter(this);

        fragment_container = findViewById(R.id.fragment_container);

        //-------------------------------------------------------------------------------------------

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, thuVienFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.host_frame_mediaplayer, mediaPlayerFragment).commit();
        getSupportFragmentManager().beginTransaction().add(R.id.MediaPlayTAB, bottomMediaPlayerFragment).commit();
        bottomSheetBehavior = BottomSheetBehavior.from(bottom_nav_player);

        BindingModel bindingModel = new BindingModel();

        activityMainBinding.setMainactivity(bindingModel);

        bottomSheetBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View view, int i) {
                switch (i) {
                    case BottomSheetBehavior.STATE_COLLAPSED:
                        MediaPlayTAB.setVisibility(View.VISIBLE);
                        fragment_container.setVisibility(View.VISIBLE);
                        overridePendingTransition(R.anim.design_bottom_sheet_fade_out, R.anim.design_bottom_sheet_fade_in);
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED:
                        fragment_container.setVisibility(View.GONE);
                        break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        MediaPlayTAB.setVisibility(View.GONE);
                        overridePendingTransition(R.anim.design_bottom_sheet_fade_in, R.anim.design_bottom_sheet_fade_out);
                        break;
                    case BottomSheetBehavior.STATE_HIDDEN:
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        break;
                        
                }
            }

            @Override
            public void onSlide(@NonNull View view, float v) {

            }
        });

    }

    private BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    Fragment selectedFragment = null;

                    switch (menuItem.getItemId()) {
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


    @Override
    public void SelectedFragment() {
    }

    @Override
    public void DoSuff() {
    }

    @Override
    public void getMusic() {
    }

}
