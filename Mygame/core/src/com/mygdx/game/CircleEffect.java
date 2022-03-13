package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.Color;
public class CircleEffect {
    int x, y, size;
    float liveTime, burnTime;
    Color color = new Color(1f, 0f, 0f, 1f);
    float rColor;

    public CircleEffect(int x, int y, int liveTime){
        this.x = x;
        this.y = y;
        this.size = 5;
        this.liveTime = liveTime;
        burnTime = Gdx.graphics.getDeltaTime();
        rColor = 0.5f;
    }

    public void update() {
        rColor = rColor - 0.001f;
        size = size + 1;
    }

    public void draw(ShapeRenderer shape){
        shape.setColor(new Color(0f, 0f, rColor, 1f));
        shape.circle(x,y,size);
    }

}
