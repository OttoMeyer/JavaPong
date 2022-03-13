package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Paddle {
    int x;
    int y;
    int xSize;
    int ySize;

    public Paddle(int x, int y, int xSize, int ySize){
        this.x = x;
        this.y = y;
        this.xSize = xSize;
        this.ySize = ySize;
    }

    public void update() {
        x = Gdx.input.getX() - xSize / 2;
    }

    public void draw(ShapeRenderer shape) {
        shape.setColor(Color.WHITE);
        shape.rect(x, y, xSize, ySize);
    }

}
