package com.mygdx.game;

public class StateMachine {
    public boolean startGame;
    public boolean goGame;
    public boolean overGame;
    public boolean winGame;

    public StateMachine(){
        startGame = true;
        goGame = false;
        overGame = false;
        winGame = false;
    }

    public void startStartGame(){
        startGame = true;
        goGame = false;
        overGame = false;
        winGame = false;
    }

    public void startGoGame(){
        startGame = false;
        goGame = true;
        overGame = false;
        winGame = false;
    }

    public void startOverGame(){
        startGame = false;
        goGame = false;
        overGame = true;
        winGame = false;
    }

    public void startWinGame(){
        startGame = false;
        goGame = false;
        overGame = false;
        winGame = true;
    }
}
