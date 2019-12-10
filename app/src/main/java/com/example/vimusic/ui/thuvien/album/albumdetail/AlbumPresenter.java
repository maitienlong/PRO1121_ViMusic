package com.example.vimusic.ui.thuvien.album.albumdetail;

public class AlbumPresenter {

    private AlbumView albumView;

    public AlbumPresenter(AlbumView albumView) {
        this.albumView = albumView;
    }

    void SendMessage(int position, String namec) {
        albumView.SendMessage(position, namec);
    }
}
