package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.GL20;
import java.util.ArrayList;
import java.util.Random;

public class MyGdxGame extends ApplicationAdapter {
	ShapeRenderer shape;
	Ball ball = new Ball(20, 20, 10,3, 3);
	Paddle paddle = new Paddle(100, 20, 100, 10);
	ArrayList<Block> blocks = new ArrayList<>();
	ArrayList<CircleEffect> circleEffects = new ArrayList<>();
	Random r = new Random();

	@Override
	public void create() {
		shape = new ShapeRenderer();
		ball.loadSound("pong.wav");
		int blockWidth = 63;
		int blockHeight = 20;
		for (int y = Gdx.graphics.getHeight()/2; y < Gdx.graphics.getHeight(); y += blockHeight + 10) {
			for (int x = 0; x < Gdx.graphics.getWidth(); x += blockWidth + 10) {
				blocks.add(new Block(x, y, blockWidth, blockHeight, new Color(1f, 1f, 1f, 0.5f)));
			}
		}
	}

	@Override
	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		shape.begin(ShapeRenderer.ShapeType.Filled);

		for (CircleEffect circleEffect : circleEffects) {
			circleEffect.update();
			circleEffect.draw(shape);
		}

		for (Block block : blocks) {
			ball.checkCollision(block);
			block.draw(shape);
		}
		for (int i = 0; i < blocks.size(); i++) {
			Block b = blocks.get(i);
			if (b.destroyed) {
				circleEffects.add(new CircleEffect(b.x+b.width/2, b.y+ b.height/2, 10));
				blocks.remove(b);
				i--;

			}
		}
		ball.checkCollision(paddle);
		ball.update();
		ball.draw(shape);

		for (Block block : blocks) {
			block.draw(shape);
		}
		paddle.update();
		paddle.draw(shape);

		shape.end();
	}
}

