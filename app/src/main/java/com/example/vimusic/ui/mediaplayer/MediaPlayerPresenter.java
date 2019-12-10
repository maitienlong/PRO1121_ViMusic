package com.example.vimusic.ui.mediaplayer;

import android.content.Context;

import com.example.vimusic.ui.thuvien.song.SongView;

public class MediaPlayerPresenter {
    private MediaPlayerView mediaPlayerView;

    public MediaPlayerPresenter(MediaPlayerView mediaPlayerView) {
        this.mediaPlayerView = mediaPlayerView;
    }

    void playAudio(Context context, String location, String artist, String name, String album, String keylist, int index) {
        try {
            mediaPlayerView.playAudio(context, location, artist, name, album, keylist, index);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void killPlayer() {
        mediaPlayerView.killPlayer();
    }

    void SetTextBinding(String name, String artist, String album, String totaltime) {
        mediaPlayerView.SetTextBinding(name, artist, album, totaltime);
    }

    void ClickPlay(){
        mediaPlayerView.BtnPlay();
    }

    void ClickPreview(){
        mediaPlayerView.btnPreview();
    }

    void ClickNext(){
        mediaPlayerView.btnNext();
    }

}
