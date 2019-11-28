package com.example.vimusic.ui.thuvien.playlist.PlaylistFragment;

public class PlaylistPresenter {

    private PlaylistView playlistView;

    public PlaylistPresenter(PlaylistView playlistView) {
        this.playlistView = playlistView;
    }

    void BackPlaylistToThuVien() {

        playlistView.BackPlaylistToThuVien();

    }

    void CheckShow() {
        playlistView.CheckShow();

    }

    void ShowPlayList() {
        playlistView.ShowPlayList();

    }

    void AddPlayList() {
        playlistView.AddPlayList();

    }
}
