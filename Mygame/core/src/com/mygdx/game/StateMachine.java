package com.mygdx.game;

public class StateMachine {
    public boolean goGame;
    public boolean overGame;
    public boolean winGame;

    public StateMachine(){
        goGame = true;
        overGame = false;
        winGame = false;
    }

    public void startGoGame(){
        goGame = true;
        overGame = false;
        winGame = false;
    }

    public void startOverGame(){
        goGame = false;
        overGame = true;
        winGame = false;
    }

    public void startWinGame(){
        goGame = false;
        overGame = false;
        winGame = true;
    }
}
