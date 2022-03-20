package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Paddle {
    int x;
    int y;
    int xSize;
    int ySize;
    Sprite sprite;

    public Paddle(int x, int y, int xSize, int ySize){
        this.x = x;
        this.y = y;
        this.xSize = xSize;
        this.ySize = ySize;
        sprite = new Sprite(new Texture("Sprite-0004.png"));
    }

    public void update() {
        x = Gdx.input.getX() - xSize / 2;
        //y = Gdx.graphics.getHeight() - Gdx.input.getY();
    }

    public void draw(SpriteBatch batch) {
        sprite.setPosition(x,y);
        sprite.draw(batch);
    }

}
