package com.example.coingobbler;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;


import java.util.ArrayList;
import java.util.Random;

public class CoinGoblerClass extends ApplicationAdapter {
	SpriteBatch batch;

	// images
	Texture background; // this is the background image
	Texture background2;
	Texture[] character;
	Texture dizzy;
	Texture GO;

	// drawing vars
	int movementState = 0;
	int pause = 0; // makes the movement of character smooth
	int characterYpos =0;
	int gameState=1;
	int heartCount=0;
	boolean hit=false;
	BitmapFont font;
	BitmapFont healthFont;
	Rectangle characterRectnagle;


	// physics vars
	float gravity = 0.2f;
	float velocity = 0;

	// game items vars

	Texture coin;
	int coinCount;
	Texture bomb;
	int bombCount;
	Texture flower;
	int flowerCount;
	Texture heart;
	int healthPoints=3;

	int score=0;
	int endScore=0;
	int screenCounter = 0;

	ArrayList<Integer> bombXs = new ArrayList<Integer>();
	ArrayList<Integer> bombYs = new ArrayList<Integer>();
	ArrayList<Rectangle> bombRectangles = new ArrayList<Rectangle>();


	ArrayList<Rectangle> coinRectangles = new ArrayList<Rectangle>();
	ArrayList<Integer> coinXs = new ArrayList<Integer>();
	ArrayList<Integer> coinYs = new ArrayList<Integer>();

	ArrayList<Integer> flowerXs = new ArrayList<Integer>();
	ArrayList<Integer> flowerYs = new ArrayList<Integer>();
	ArrayList<Rectangle> flowerRectangles = new ArrayList<Rectangle>();

	ArrayList<Integer> heartXs = new ArrayList<Integer>();
	ArrayList<Integer> heartYs = new ArrayList<Integer>();
	ArrayList<Rectangle> heartRectangles = new ArrayList<Rectangle>();




	Random random;


	@Override
	public void create () { // first thing that runs befor the game starts
		batch = new SpriteBatch();

		background = new Texture("bg.png");
		background2 = new Texture("bg2.png");
		character = new Texture[4]; // 4 images of the running character
		character[0] = new Texture("frame-1.png");
		character[1] = new Texture("frame-2.png");
		character[2] = new Texture("frame-3.png");
		character[3] = new Texture("frame-4.png");
		dizzy = new Texture("dizzy-1.png");
		GO = new Texture("game-over.png");

		characterYpos = Gdx.graphics.getHeight()/2; // set character y position to mid screen

		coin = new Texture("coin.png");
		bomb = new Texture("bomb.png");
		flower = new Texture("flower.png");
		heart = new Texture("heart.png");
		Rectangle characterRectnagle = new Rectangle();
		random = new Random();
		font = new BitmapFont();
		font.setColor(Color.WHITE); // choose font color
		font.getData().setScale(10); // choose font size

		healthFont = new BitmapFont();
		healthFont.setColor(Color.RED); // choose font color
		healthFont.getData().setScale(10); // choose font size

	}

	public void makeBomb(){
		float height = random.nextFloat() * Gdx.graphics.getHeight();
		bombYs.add((int)height);
		bombXs.add(Gdx.graphics.getWidth());
	}

	public void makeCoin(){
		float height = random.nextFloat() * Gdx.graphics.getHeight();
		coinYs.add((int)height);
		coinXs.add(Gdx.graphics.getWidth());
	}

	public void makeFlower(){
		float height = random.nextFloat() * Gdx.graphics.getHeight();
		flowerYs.add((int)height);
		flowerXs.add(Gdx.graphics.getWidth());
	}

	public void makeHeart(){
		float height = random.nextFloat() * Gdx.graphics.getHeight();
		heartYs.add((int)height);
		heartXs.add(Gdx.graphics.getWidth());
	}


	@Override
	public void render () {
		batch.begin();

		if(screenCounter==0) {
			batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); // draw background first to have everything drawn on it
		}
		else
		{
			batch.draw(background2, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
		if(gameState==1) {
			// GAME IS LIVE


			/////////////////////////////// COINS BLOCK ///////////////////////////////
			if(coinCount<100){
				coinCount++;
			}
			else
			{
				coinCount=0;
				makeCoin();
			}

			coinRectangles.clear();
			for(int i=0;i < coinXs.size();i++)
			{
				batch.draw(coin, coinXs.get(i), coinYs.get(i));
				coinXs.set(i, coinXs.get(i)-4);
				coinRectangles.add(new Rectangle(coinXs.get(i),coinYs.get(i),coin.getWidth(),coin.getHeight()));
			}


			/////////////////////////////// BOMBS BLOCK ///////////////////////////////
			if(bombCount<300){
				bombCount++;
			}
			else
			{
				bombCount=0;
				makeBomb();
			}

			bombRectangles.clear();
			for(int i=0;i < bombXs.size();i++)
			{
				batch.draw(bomb, bombXs.get(i), bombYs.get(i));
				bombXs.set(i, bombXs.get(i)-6);
				bombRectangles.add(new Rectangle(bombXs.get(i),bombYs.get(i),bomb.getWidth(),bomb.getHeight()));
			}

			/////////////////////////////// FLOWER BLOCK ///////////////////////////////
			if(flowerCount< 2000){
				flowerCount++;
			}
			else
			{
				flowerCount=0;
				makeFlower();
			}

			flowerRectangles.clear();
			for(int i=0;i < flowerXs.size();i++)
			{
				batch.draw(flower, flowerXs.get(i), flowerYs.get(i));
				flowerXs.set(i, flowerXs.get(i)-4);
				flowerRectangles.add(new Rectangle(flowerXs.get(i),flowerYs.get(i),flower.getWidth(),flower.getHeight()));
			}

			/////////////////////////////// HEART BLOCK ///////////////////////////////
			if(heartCount< 2300){
				heartCount++;
			}
			else
			{
				heartCount=0;
				makeHeart();
			}

			heartRectangles.clear();
			for(int i=0;i < heartXs.size();i++)
			{
				batch.draw(heart, heartXs.get(i), heartYs.get(i));
				heartXs.set(i, heartXs.get(i)-4);
				heartRectangles.add(new Rectangle(heartXs.get(i),heartYs.get(i),heart.getWidth(),heart.getHeight()));
			}

			/////////////////////////////// JUMP BLOCK ///////////////////////////////
			if(Gdx.input.justTouched()) {
				velocity =-10; // jump if user touched the screen !
			}


			if(pause<8)
			{
				pause++; }
			else {
				pause = 0; // reset pause frame so next one could run
				movementState++; // in order to change frame of running
				movementState = movementState % 4; // in order to cycle between 4 images;
			}


			velocity += gravity; // increase velocity
			characterYpos -= velocity; // make character fall down the screen

			if(characterYpos <= 0) // prevents the character falling off screen
				characterYpos=0;


		} else if ( gameState == 0 )
		{
			// WAITING TO START
			if(Gdx.input.justTouched()) { gameState = 1;}
		} else if ( gameState == 2)
		{
			// GAME OVER
			if(Gdx.input.justTouched()) {
				gameState = 1;}
			characterYpos = Gdx.graphics.getHeight()/2;
			score = 0;
			velocity = 0;
			screenCounter =0;
			healthPoints=3;

			coinXs.clear();
			coinYs.clear();
			coinCount=0;
			coinRectangles.clear();

			bombXs.clear();
			bombYs.clear();
			bombCount=0;
			bombRectangles.clear();

			flowerXs.clear();
			flowerYs.clear();
			flowerCount=0;
			flowerRectangles.clear();


		}




		if(gameState==2)
		{
			batch.draw(GO,Gdx.graphics.getWidth()/2-GO.getWidth()/2,characterYpos+Gdx.graphics.getHeight()/4); // GAME OVER ICON
			font.draw(batch,"SCORE:   "+String.valueOf(endScore), Gdx.graphics.getWidth()/4,Gdx.graphics.getHeight()/7); // print score at end game

			batch.draw(dizzy,Gdx.graphics.getWidth()/2-character[movementState].getWidth()/2,characterYpos);
		} else {
			batch.draw(character[movementState], Gdx.graphics.getWidth() / 2 - character[movementState].getWidth() / 2, characterYpos);
		}
		characterRectnagle = new Rectangle(Gdx.graphics.getWidth()/2-character[movementState].getWidth()/2,characterYpos,character[movementState].getWidth(),character[movementState].getHeight());

		for(int i=0;i< coinRectangles.size();i++) // coin picked up
		{
			if(Intersector.overlaps(characterRectnagle,coinRectangles.get(i))){
				score++; // coin worth 1 score point
				endScore=score;
				coinRectangles.remove(i);
				coinXs.remove(i);
				coinYs.remove(i);
				break;
			}
		}

		for(int i=0;i< bombRectangles.size();i++) // bomb picked up
		{
			if(Intersector.overlaps(characterRectnagle,bombRectangles.get(i))){
				--healthPoints;



				bombRectangles.remove(i);
				bombXs.remove(i);
				bombYs.remove(i);


				if(healthPoints==0) {
					gameState = 2; // game over
				}
				break;
			}
		}

			for(int i=0;i< flowerRectangles.size();i++) // flower picked up
		{
			if(Intersector.overlaps(characterRectnagle,flowerRectangles.get(i))){
				score=score+10; // flower worth 10 score points
				screenCounter++; // switch screen
				screenCounter = screenCounter%2; // only two screens
				endScore=score;
				flowerRectangles.remove(i);
				flowerXs.remove(i);
				flowerYs.remove(i);
				break;
			}
		}

		for(int i=0;i< heartRectangles.size();i++) // heart picked up
		{
			if(Intersector.overlaps(characterRectnagle,heartRectangles.get(i))){
				healthPoints++; // increase health points
				heartRectangles.remove(i);
				heartXs.remove(i);
				heartYs.remove(i);
				break;
			}
		}




		font.draw(batch,String.valueOf(score), 100,200); // printing score to screen
		healthFont.draw(batch,String.valueOf(healthPoints),Gdx.graphics.getWidth()-200,200); // printing hp to screen
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
