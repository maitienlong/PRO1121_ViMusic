package com.example.vimusic.mainactivity;

public class MainPresenter {
    private MainView mainView;

    public MainPresenter(MainView mainView) {
        this.mainView = mainView;
    }


    void SelectedFragment(){

        mainView.SelectedFragment();
    }
    void dosulf(){
        mainView.DoSuff();
    }
    void getMusic(){
        mainView.getMusic();
    }
}
