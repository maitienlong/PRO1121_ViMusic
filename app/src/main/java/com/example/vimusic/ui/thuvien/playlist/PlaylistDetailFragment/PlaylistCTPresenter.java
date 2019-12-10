package com.example.vimusic.ui.thuvien.playlist.PlaylistDetailFragment;

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

    void sentdate(int position, String namec){
        playlistCTView.SendMessage(position, namec);
    }
}
