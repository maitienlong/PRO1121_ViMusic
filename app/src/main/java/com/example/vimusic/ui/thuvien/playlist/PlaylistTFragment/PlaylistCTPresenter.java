package com.example.vimusic.ui.thuvien.playlist.PlaylistTFragment;

import com.example.vimusic.ui.thuvien.playlist.PlaylistFragment.PlaylistView;

public class PlaylistCTPresenter {

    private PlaylistCTView playlistCTView;

    public PlaylistCTPresenter(PlaylistCTView playlistCTView) {
        this.playlistCTView = playlistCTView;
    }

    void ShowPlaylistCT() {
        playlistCTView.ShowPlaylistCT();
    }

    void InputBundle() {
        playlistCTView.InputBundle();
    }

    void FindDatabase() {
        playlistCTView.FindDatabase();
    }

    void SetNamePlaylist() {
        playlistCTView.SetNamePlaylist();
    }

    void sentdate(String location, String title, String artist){
        playlistCTView.SendMessage(location, title, artist);
    }
}
