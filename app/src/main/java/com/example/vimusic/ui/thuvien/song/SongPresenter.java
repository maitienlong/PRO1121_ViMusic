package com.example.vimusic.ui.thuvien.song;

public class SongPresenter {
    private SongView songView;

    public SongPresenter(SongView songView) {
        this.songView = songView;
    }

    void ScanMusic() {
        songView.ScanAllSong();
    }

    void SendMessage(String location , String title, String artist) {
        songView.SendMessage(location, title, artist);
    }
}


