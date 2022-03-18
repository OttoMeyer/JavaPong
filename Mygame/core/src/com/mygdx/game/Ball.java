package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.audio.Sound;



public class Ball {
    int x;
    int y;
    int size;
    int xSpeed;
    int ySpeed;
    Color color = Color.WHITE;
    private Sound pong;
    float soundMod = 0.1f;

    public Ball(int x, int y, int size, int xSpeed, int ySpeed) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    public void loadSound(String path){
        pong = Gdx.audio.newSound(Gdx.files.internal(path));
    }

    public void update(StateMachine stateMachine) {
        x += xSpeed;
        y += ySpeed;
        if (x < size || x > Gdx.graphics.getWidth() - size) {
            xSpeed = -xSpeed;
        }
        if (y > Gdx.graphics.getHeight()- size ) {
            ySpeed = -ySpeed;
        }
        if (y < size){
            stateMachine.startOverGame();
        }
    }
    public void draw(ShapeRenderer shape){
        shape.setColor(color);
        shape.circle(x, y, size);
    }
    public void checkCollision(Paddle paddle) {
        if(collidesWith(paddle)){
            ySpeed = -ySpeed;
            xSpeed = xSpeed + ((x + size) - (paddle.x + paddle.xSize/2))/20;
        }
    }

    public void checkCollision(Block block) {
        if(collidesWith(block)){
            pong.play(soundMod);
            int xMidle = x - size;
            int yMidle = y - size;
            int xCube = (int)(block.x - block.height/2);
            int yCube = (int)(block.y - block.width/2);
            if (Math.abs((yMidle - yCube)/(block.width/2)) < Math.abs((xMidle - xCube)/(block.height/2))){
                ySpeed = - ySpeed;
            }
            else{
                xSpeed = - xSpeed;
            }
            block.destroyed = true;

        }
    }

    private boolean collidesWith(Paddle paddle) {
        if ((paddle.x < x + size) & (paddle.x + paddle.xSize> x - size)
                & (paddle.y < y + size) & (paddle.y + paddle.ySize> y - size)){
            return true;
        }
        else{
            return false;
        }

    }

    private boolean collidesWith(Block block) {
        if ((block.x < x + size) & (block.x + block.width> x - size)
                & (block.y < y + size) & (block.y + block.height> y - size)){
            return true;
        }
        else{
            return false;
        }

    }
}
