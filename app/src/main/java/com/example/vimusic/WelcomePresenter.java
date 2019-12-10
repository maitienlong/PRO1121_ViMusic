package com.example.vimusic;

public class WelcomePresenter {

    private WelcomeView welcomeView;

    public WelcomePresenter(WelcomeView welcomeView) {
        this.welcomeView = welcomeView;
    }

    void DoStuff(){
        welcomeView.DoStuff();
    }

    void getMusic(){
     welcomeView.getMusic();
    }


}
