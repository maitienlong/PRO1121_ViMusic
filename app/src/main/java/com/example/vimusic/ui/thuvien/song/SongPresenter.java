package com.example.vimusic.ui.thuvien.song;

import com.example.vimusic.model.BaiHat;

import java.util.ArrayList;

public class SongPresenter {
    private SongView songView;

    public SongPresenter(SongView songView) {
        this.songView = songView;
    }

    void ScanMusic() {
        songView.ScanAllSong();
    }

    void SendMessage(int position) {
        songView.SendMessage(position);
    }
}


