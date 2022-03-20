package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.math.MathUtils;


public class Ball {
    float x;
    float y;
    int size;
    float xSpeed;
    float ySpeed;
    Color color = new Color(0.36f, 0.39f, 0.72f, 1f);
    private Sound pong;
    float soundMod = 0.1f;
    float speedMod = 2f;
    Sprite sprite;


    public Ball(int x, int y, int size, float xSpeed, float ySpeed) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        sprite = new Sprite(new Texture("Sprite-0005.png"));
        size = (int)sprite.getHeight();
    }

    public void loadSound(String path){
        pong = Gdx.audio.newSound(Gdx.files.internal(path));
    }

    public void update(StateMachine stateMachine) {
        x += xSpeed;
        y += ySpeed;
        if (x < 0) {
            xSpeed = Math.abs(xSpeed);
        }
        if(x > Gdx.graphics.getWidth() - size){
            xSpeed = -Math.abs(xSpeed);
        }
        if (y > Gdx.graphics.getHeight()- size ) {
            ySpeed = -Math.abs(ySpeed);
        }
        if (y < 0){
            stateMachine.startOverGame();
        }
    }
    public void draw(SpriteBatch batch){
        sprite.setPosition(x,y);
        sprite.draw(batch);
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
            float xMidle = x - size;
            float yMidle = y - size;
            float xCube = (block.x - block.height/2);
            float yCube = (block.y - block.width/2);
            if (Math.abs((yMidle - yCube)/(block.width/2)) < Math.abs((xMidle - xCube)/(block.height/2))){
                ySpeed = - ySpeed;
            }
            else{
                xSpeed = - xSpeed;
            }
            block.destroyed = true;

        }
    }

    public void newCheckCollision(Block block) {
        if(collidesWith(block)){
            pong.play(soundMod);
            float xMidle = x + size/2;
            float yMidle = y + size/2;
            float xCube = (block.x - block.width/2);
            float yCube = (block.y - block.height/2);
            if (Math.abs((yMidle - yCube)/(block.height/2)) < Math.abs((xMidle - xCube)/(block.width/2))){
                ySpeed = - ySpeed;
            }
            else{
                xSpeed = - xSpeed;
            }
            block.destroyed = true;

        }
    }

    public void newCheckCollision(Paddle paddle) {
        if(collidesWith(paddle)){
            ySpeed = -ySpeed;
            xSpeed = xSpeed + ((x + size/2) - (paddle.x + paddle.xSize/2))/50;
            float norm = (float) Math.sqrt(Math.pow(ySpeed, 2) + Math.pow(xSpeed, 2));
            ySpeed = ySpeed / norm * speedMod;
            xSpeed = xSpeed / norm * speedMod;


        }
    }

    public void modSpeed(float mod){
        speedMod += mod;
    }

    private boolean collidesWith(Paddle paddle) {
        if ((paddle.x < x + size) & (paddle.x + paddle.xSize> x)
                & (paddle.y < y + size) & (paddle.y + paddle.ySize> y)){
            return true;
        }
        else{
            return false;
        }

    }

    private boolean collidesWith(Block block) {
        if ((block.x < x+size) & (block.x + block.width> x)
                & (block.y < y+size) & (block.y + block.height> y)){
            return true;
        }
        else{
            return false;
        }

    }

}
