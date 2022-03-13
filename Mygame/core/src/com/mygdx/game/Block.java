package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;

public class Block {
    int x,y,width,height;
    boolean destroyed = false;
    Color color = new Color();

    public Block(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        color = new Color(1f,0f,0f,1f);
    }

    public Block(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    public void draw(ShapeRenderer shape){
        shape.setColor(color);
        shape.rect(x, y, width, height);

    }
}
