package com.mygdx.game;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.math.MathUtils;

public class Block {
    int width,height;
    float x, y;
    float yTarget;
    float ySpeed;
    long timeWait;
    boolean destroyed = false;
    Color color = new Color();
    long timeCreation;

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

    public Block(float x, float y, float yTarget, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.yTarget = yTarget;
        this.timeCreation = TimeUtils.nanoTime();
        this.timeWait = (long) (1000000 * MathUtils.random(0, 1000));
        this.ySpeed = 0;
    }

    public void draw(ShapeRenderer shape){
        shape.setColor(color);
        shape.rect(x, y, width, height);

    }

    public void drawAnimatic(ShapeRenderer shape){
        if (TimeUtils.nanoTime() - timeCreation > timeWait){
            //y += (yTarget - y) * 0.1;
            ySpeed += (yTarget - (y+ySpeed*4)) * 0.1;
            y += ySpeed;
        }
        shape.setColor(color);
        shape.rect(x, y, width, height);

    }
}
