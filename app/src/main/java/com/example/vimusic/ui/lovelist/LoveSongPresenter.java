package com.example.vimusic.ui.lovelist;

import com.example.vimusic.ui.mediaplayer.MediaPlayerView;

public class LoveSongPresenter {
    private LoveSongView loveSongView;

    public LoveSongPresenter(LoveSongView loveSongView) {
        this.loveSongView = loveSongView;
    }

    void ScanSongLove(){
        loveSongView.ScanLoveSong();
    }

    void SendMessage(int position){
        loveSongView.SendMessage(position);
    }


}
