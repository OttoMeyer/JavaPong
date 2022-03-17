/*
TO DO LIST
# Уничтожать эфекты кругов когда они становятся не нужны [X]
# Счётчмк очков [ ]
# Анимация старта игры [ ]
# Переработать колизию [ ]
# Переработать отскок мяча от рокетки [ ]
# Тряска соседних блоков при ударе [ ]
 */

/*
BUGS
@ Баг двойной колизии [ ]
	Если мечик сколкнётся одновременно с 2 обектами то дважды поменяет направление
	и таким образом не отскочит от объекта
@ Баг застревания в стенке [ ]
	Мячь может как-то странно залететь в стенку так что заходит слишком глубако.
	Каждый тик он менят своё направление отражаясь от стеки и так в ней застревает двигаясь
	по заг загу.
 */

package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.GL20;

import java.text.Format;
import java.util.ArrayList;
import java.util.Random;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input.Keys;

public class MyGdxGame extends ApplicationAdapter {
	ShapeRenderer shape;
	Ball ball;
	Paddle paddle = new Paddle(100, 20, 100, 10);
	ArrayList<Block> blocks = new ArrayList<>();
	ArrayList<CircleEffect> circleEffects = new ArrayList<>();
	Random r = new Random();
	public SpriteBatch batch;
	public BitmapFont font;
	public boolean flagOver;
	public StateMachine stateMachine;
	public BitmapFont.BitmapFontData data;
	public TextureRegion textureRegion;
	public Texture texture;
	public Pixmap pixmap;
	int score = 0;

	@Override
	public void create() { // Старт игры
		stateMachine = new StateMachine();
		ball = new Ball(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2, 10,3, 3);
		shape = new ShapeRenderer();
		ball.loadSound("pong.wav");
		int blockWidth = 63;
		int blockHeight = 20;
		for (int y = Gdx.graphics.getHeight()/2; y < Gdx.graphics.getHeight(); y += blockHeight + 10) {
			for (int x = 0; x < Gdx.graphics.getWidth(); x += blockWidth + 10) {
				blocks.add(new Block(x, y, blockWidth, blockHeight, new Color(1f, 1f, 1f, 0.5f)));
			}
		}
		pixmap = new Pixmap(100, 50, Pixmap.Format.Alpha);
		texture = new Texture(pixmap);
		data = new BitmapFont.BitmapFontData();
		batch = new SpriteBatch();
		textureRegion = new TextureRegion(texture);
		font = new BitmapFont(data, textureRegion, true);
		flagOver = false;
	}

	@Override
	public void render() { // Процесс игры
		if(stateMachine.goGame){ // Игра в процессе
			activeGame();
			isWin();
		}else if(stateMachine.overGame){ // Проигрыш
			gameOver();
		}else if(stateMachine.winGame){ // Выйгрыш
			gameOver();
		}

	}

	public void activeGame(){ // Вся Игровая логика с отображением элементов
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		shape.begin(ShapeRenderer.ShapeType.Filled);

		for (CircleEffect circleEffect : circleEffects) { // Красивый эффект кругов
			circleEffect.update();
			circleEffect.draw(shape);
		}
		for (int i = 0; i < circleEffects.size(); i++) { // Уничтожение блоков
			CircleEffect c = circleEffects.get(i);
			if (c.destroyed) {
				circleEffects.remove(c);
				i--;
			}
		}

		for (Block block : blocks) { // Колизия с блоками
			ball.checkCollision(block);
			block.draw(shape);
		}
		for (int i = 0; i < blocks.size(); i++) { // Уничтожение блоков
			Block b = blocks.get(i);
			if (b.destroyed) {
				score += 100;
				circleEffects.add(new CircleEffect(b.x+b.width/2, b.y+ b.height/2, 10));
				blocks.remove(b);
				i--;

			}
		}

		ball.checkCollision(paddle); // Логика и отображение мача
		ball.update(stateMachine);
		ball.draw(shape);

		for (Block block : blocks) { // Отображение блоков
			block.draw(shape);
		}
		paddle.update(); // Логика и отображение ракетки
		paddle.draw(shape);
		shape.end();

		batch.begin();
		font.setColor(Color.ROYAL);
		font.draw(batch, String.valueOf(score), 10, Gdx.graphics.getHeight()-10);
		batch.end();
	}

	public void gameOver(){ // Победа и проигрыш в игре
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		if(stateMachine.overGame){
			font.draw(batch, "Game Over",
					Gdx.graphics.getWidth()/2 + font.getCapHeight()/2,
					Gdx.graphics.getHeight()/2+font.getLineHeight()/2);
			score = 0;
		}
		else
			font.draw(batch, "You win",
					Gdx.graphics.getWidth()/2 + font.getCapHeight()/2,
					Gdx.graphics.getHeight()/2+font.getLineHeight()/2);
		batch.end();
		if (Gdx.input.isKeyPressed(Keys.ANY_KEY)){
			stateMachine.startGoGame();
			destroyAll();
			create();
		}
	}

	public void destroyAll(){ // Опустошить все массивы объектов
		blocks.clear();
		circleEffects.clear();
	}

	public void isWin(){ // Условие победы
		if (blocks.size() == 0){
			stateMachine.startWinGame();
		}
	}
}

