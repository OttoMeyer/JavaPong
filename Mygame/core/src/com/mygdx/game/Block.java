package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Block {
    int width,height;
    float x, y;
    float yTarget;
    float ySpeed;
    float rectSpeed;
    long timeWait;
    boolean destroyed = false;
    Color color = new Color();
    long timeCreation;
    Sprite sprite;

    public Block(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        color = new Color(1f,1f,1f,1f);
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
        float rectSpeed = 0;
        sprite = new Sprite(new Texture("Sprite-0003.png"));
    }

    public void drawAnimatic(SpriteBatch batch){
        if (TimeUtils.nanoTime() - timeCreation > timeWait){
            //y += (yTarget - y) * 0.1;
            ySpeed += (yTarget - (y+ySpeed*4)) * 0.1;
            y += ySpeed;
        }
        sprite.rotate(rectSpeed);
        rectSpeed += (0 - (sprite.getRotation() + rectSpeed)) * 0.1;
        sprite.setColor(color);
        sprite.setSize(width, height);
        sprite.setPosition(x,y);
        sprite.draw(batch);

    }
}
