package com.example.vimusic.ui.mediaplayer;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.example.vimusic.R;
import com.example.vimusic.ui.thuvien.song.SongPresenter;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class MediaPlayerFragment extends Fragment implements MediaPlayerView {

    private MediaPlayer mediaPlayer;

    private TextView tvPlayerTenBaiHat;
    private TextView tvPlayerTenCaSi;

    private ImageView btnPlay;
    private ImageView btnReBack;
    private ImageView btnForward;

    private SongPresenter songPresenter;

    private String location;
    private String name;
    private String artist;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            location = getArguments().getString("location");
            name = getArguments().getString("title");
            artist = getArguments().getString("artist");
        }

    }

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_mediaplayer, container, false);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvPlayerTenBaiHat = view.findViewById(R.id.tvPlayerTenBaiHat);
        tvPlayerTenCaSi = view.findViewById(R.id.tvPlayerTenCaSi);

        btnPlay = view.findViewById(R.id.btnPlay);
        btnReBack = view.findViewById(R.id.btnReBack);
        btnForward = view.findViewById(R.id.btnForward);

        if (location != null) {
            mediaPlayer = MediaPlayer.create(getActivity(), Uri.parse(location));

            mediaPlayer.start();
        }

        tvPlayerTenBaiHat.setText(name);
        tvPlayerTenCaSi.setText(artist);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("MediaPlayerFragment", "onResume");

    }

    @Override
    public void onPause() {
        super.onPause();
        Log.e("MediaPlayerFragment", "onPause");
    }

    public void ReceivedData(String location) {
        mediaPlayer = MediaPlayer.create(getActivity(), Uri.parse(location));
        mediaPlayer.start();
    }
}
