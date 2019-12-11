package com.example.vimusic.ui.mediaplayer;

import android.content.Context;

public interface MediaPlayerView {
    void btnNext() ;
    void btnPreview();
    void BtnPlay();
    void btnLoop();
    void playAudio(Context context, String location, String artist, String name, String album, String keylist, int index) throws Exception;
    void PlayMedia();
    void PauseMedia();
    void killPlayer();
    void SetTextBinding(String title, String artist, String album, String totaltime);

}
