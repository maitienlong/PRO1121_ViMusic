package com.example.vimusic.ui.thuvien.library;

public class LibraryPresenter {

    private LibraryView libraryView;

    public LibraryPresenter(LibraryView libraryView) {
        this.libraryView = libraryView;
    }

    void OpenPlaylist(){

        libraryView.OpenPlaylist();

    }
    void OpenNgheSi(){

        libraryView.OpenNgheSi();

    }
    void OpenAlbum(){

        libraryView.OpenAlbum();

    }
    void OpenBaihat(){

        libraryView.OpenBaihat();

    }

    void getMusics(){

        libraryView.getMusics();

    }

    void doStuffs(){

        libraryView.doStuffs();

    }

    void ScanSongTV(){

        libraryView.ScanSongTV();

    }



}
