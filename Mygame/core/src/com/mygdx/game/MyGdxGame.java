/*
TO DO LIST
# Уничтожать эфекты кругов когда они становятся не нужны [X]
# Счётчмк очков [V]
	# Сделать лучше [X]
		Чувствую нужно будет разобраться как работают текстуры перед этим.
# Анимация старта игры [X]
# Переработать колизию [V]
# Переработать отскок мяча от рокетки [V]
# Тряска соседних блоков при ударе [ ]
 */

/*
BUGS
@ Баг двойной колизии [ ]
	Если мечик сколкнётся одновременно с 2 обектами то дважды поменяет направление
	и таким образом не отскочит от объекта
@ Баг застревания в стенке [Возможно]
	Мячь может как-то странно залететь в стенку так что заходит слишком глубако.
	Каждый тик он менят своё направление отражаясь от стеки и так в ней застревает двигаясь
	по заг загу.
 */

package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.MathUtils;

import java.util.ArrayList;
import java.util.Random;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.Input.Keys;

import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class MyGdxGame extends ApplicationAdapter {
	ShapeRenderer shape;
	Ball ball;
	Paddle paddle;
	ArrayList<Block> blocks = new ArrayList<>();
	ArrayList<CircleEffect> circleEffects = new ArrayList<>();
	Random r = new Random();
	public SpriteBatch batch;
	public BitmapFont font;
	public boolean flagOver;
	public StateMachine stateMachine;
	int score = 0;
	GlyphLayout glyphLayout;

	@Override
	public void create() { // Старт игры
		stateMachine = new StateMachine();
		paddle = new Paddle(100, 20, 100, 10);
		shape = new ShapeRenderer();
		int blockWidth = 63;
		int blockHeight = 20;
		for (int y = Gdx.graphics.getHeight()/2; y < Gdx.graphics.getHeight(); y += blockHeight + 10) {
			for (int x = 0; x < Gdx.graphics.getWidth(); x += blockWidth + 10) {
				blocks.add(new Block(x, y+1000, y, blockWidth, blockHeight, new Color(1f, 1f, 1f, 1f)));
			}
		}
		batch = new SpriteBatch();
		font = new BitmapFont(Gdx.files.internal("6.fnt"));
		//font = new BitmapFont();
		glyphLayout = new GlyphLayout();
	}

	@Override
	public void render() { // Процесс игры
		if(stateMachine.startGame){
			startGame();
		}else if(stateMachine.goGame){ // Игра в процессе
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
			ball.newCheckCollision(block);
		}
		for (int i = 0; i < blocks.size(); i++) { // Уничтожение блоков
			Block b = blocks.get(i);
			if (b.destroyed) {
				score += 100;
				ball.modSpeed(0.1f);
				findBlock(b);
				circleEffects.add(new CircleEffect((int)b.x+b.width/2, (int)b.y+ b.height/2, 10));
				blocks.remove(b);
				i--;

			}
		}


		ball.newCheckCollision(paddle); // Логика и отображение мача
		ball.update(stateMachine);
		shape.end();



		batch.begin();
		ball.draw(batch);
		paddle.update(); // Логика и отображение ракетки
		paddle.draw(batch);
		for (Block block : blocks) { // Колизия с блоками
			block.drawAnimatic(batch);
		}
		font.setColor(Color.WHITE);
		font.draw(batch, String.valueOf(score), 10, Gdx.graphics.getHeight()-10);
		batch.end();
	}

	public void gameOver(){ // Победа и проигрыш в игре
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		if(stateMachine.overGame){
			font.setColor(Color.RED);
			glyphLayout.setText(font, "Game Over");
			font.draw(batch, glyphLayout,
					Gdx.graphics.getWidth()/2 - glyphLayout.width/2,
					Gdx.graphics.getHeight()/2 + glyphLayout.height/2);
			score = 0;
		}
		else {
			font.setColor(Color.GREEN);
			glyphLayout.setText(font, "You win");
			font.draw(batch, glyphLayout,
					Gdx.graphics.getWidth()/2 - glyphLayout.width/2,
					Gdx.graphics.getHeight()/2 + glyphLayout.height/2);
		}
		batch.end();
		if (Gdx.input.isKeyPressed(Keys.ANY_KEY)){
			stateMachine.startStartGame();
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

	public void startGame(){
		boolean flag = true;
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		shape.begin(ShapeRenderer.ShapeType.Filled);
		batch.begin();
		for (Block block : blocks) { // Отображение блоков
			block.drawAnimatic(batch);
			if(Math.abs(block.yTarget - block.y) > 5){
				flag = false;
			}
		}
		batch.end();


		shape.end();

		batch.begin();
		paddle.update(); // Логика и отображение ракетки
		paddle.draw(batch);
		font.setColor(Color.WHITE);
		font.draw(batch, String.valueOf(score), 10, Gdx.graphics.getHeight()-10);
		batch.end();

		if(flag)
		{
			stateMachine.startGoGame();
			float piRad = MathUtils.random((float)-Math.PI*3/4, (float)-Math.PI/4);
			ball = new Ball(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/3,
					10,MathUtils.cos(piRad)*2, MathUtils.sin(piRad)*2);

			//ball = new Ball(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/3,
			//		20,0, 0);

			ball.loadSound("pong.wav");
		}


	}
	private void findBlock(Block b){
		float bWidth = b.x + b.sprite.getWidth() / 2;
		float bHeight = b.y + b.sprite.getHeight() / 2;
		for (Block block : blocks) {
			float blockWidth = block.x + block.sprite.getWidth() / 2;
			float blockHeight = block.y + block.sprite.getHeight() / 2;
			double r = Math.sqrt(Math.pow(bWidth - blockWidth, 2) + Math.pow(bHeight - blockHeight, 2));
			if(r < 100){
				block.rectSpeed += (100 - r) / 10;
			}
		}
	}
}

