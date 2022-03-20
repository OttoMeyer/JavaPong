package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
public class CircleEffect {
    int x, y, size;
    float liveTime, burnTime;
    Color color = new Color(0.8f,0.8f,1f,0.1f);
    float rColor, gColor, bColor;
    float rSpeedColor, gSpeedColor, bSpeedColor;
    boolean destroyed;
    int speedDark = 200;

    public CircleEffect(int x, int y, int liveTime){
        this.x = x;
        this.y = y;
        this.size = 5;
        this.liveTime = liveTime;
        burnTime = Gdx.graphics.getDeltaTime();
        rColor = 0.8f;
        gColor = 0.8f;
        bColor = 1f;
        rSpeedColor = rColor / speedDark;
        gSpeedColor = gColor / speedDark;
        bSpeedColor = bColor / speedDark;
        destroyed = false;
    }

    public void update() {
        rColor -= rSpeedColor;
        gColor -= gSpeedColor;
        bColor -= bSpeedColor;
        size = size + 1;
        if (rColor < 0)
            destroyed = true;
    }

    public void draw(ShapeRenderer shape){
        shape.setColor(new Color(rColor,gColor,bColor,0.1f));
        shape.circle(x,y,size);
    }

}
