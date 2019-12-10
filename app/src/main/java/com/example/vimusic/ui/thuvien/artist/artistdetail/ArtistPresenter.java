package com.example.vimusic.ui.thuvien.artist.artistdetail;

public class ArtistPresenter {

    public ArtistView artistView;

    public ArtistPresenter(ArtistView artistView) {
        this.artistView = artistView;
    }

    void SendMessage(int position, String namec){
        artistView.SendMessage(position, namec);
    }
}
