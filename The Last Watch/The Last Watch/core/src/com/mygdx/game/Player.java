package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Entity;

public class Player extends Entity {
	
	boolean shootOld = false;
	boolean shootNow = false;
	boolean canShoot = true;
	public double life = 100.0;
	
	public Player(MyGdxGame game, float x, float y, int width, int height, float speed, Texture texture) {
		super(game, x, y, width, height, speed, texture);
	}
	
	Texture Up = new Texture("Grosh - Up.png");
	Texture Left = new Texture("Grosh - Left.png");
	Texture Right = new Texture("Grosh - Right.png");
	Texture Down = new Texture("Grosh - Down.png");
	
	

	@Override
	public void update(float delta) {
		
		dx = 0;
		dy = 0;

		// move
		if(Gdx.input.isKeyPressed(Keys.W)) {
			dy = speed * delta;
			//this.texture = Up;
		}
		if(Gdx.input.isKeyPressed(Keys.S)) {
			dy = -speed * delta;
			//this.texture = Down;
		}
		if(Gdx.input.isKeyPressed(Keys.A)) {
			dx = -speed * delta;
			//this.texture = Left;
		}
		if(Gdx.input.isKeyPressed(Keys.D)) {
			dx = speed * delta;
			//this.texture = Right;
		}
		
		if (Gdx.input.isKeyPressed(Keys.UP) || Gdx.input.isKeyPressed(Keys.RIGHT) || Gdx.input.isKeyPressed(Keys.DOWN) || Gdx.input.isKeyPressed(Keys.LEFT))
		{
			shootNow = true;
			if(Gdx.input.isKeyPressed(Keys.UP) && canShoot == true) {
				//shootNow = Gdx.input.isKeyPressed(Keys.UP);
				if (shootNow && !shootOld)
				{
					game.shoot(2);
					canShoot = false;
				}
				//shootOld = shootNow;
			}
			//shootOld = Gdx.input.isKeyPressed(Keys.UP);
			
			if(Gdx.input.isKeyPressed(Keys.RIGHT) && canShoot == true) {
				//shootNow = Gdx.input.isKeyPressed(Keys.RIGHT);
				if (shootNow && !shootOld)
				{
					game.shoot(0);
					canShoot = false;
				}
				//shootOld = shootNow;
			}
			//shootOld = Gdx.input.isKeyPressed(Keys.RIGHT);
			
			if(Gdx.input.isKeyPressed(Keys.LEFT) && canShoot == true) {
				//shootNow = Gdx.input.isKeyPressed(Keys.LEFT);
				if (shootNow && !shootOld)
				{
					game.shoot(1);
					canShoot = false;
				}
				//shootOld = shootNow;
			}
			//shootOld = Gdx.input.isKeyPressed(Keys.LEFT);
			
			if(Gdx.input.isKeyPressed(Keys.DOWN) && canShoot == true) {
				//shootNow = Gdx.input.isKeyPressed(Keys.DOWN);
				if (shootNow && !shootOld)
				{
					game.shoot(3);
					canShoot = false;
				}
				//shootOld = shootNow;
			}
			//shootOld = Gdx.input.isKeyPressed(Keys.DOWN);
			shootOld = shootNow;
		}
		if (Gdx.input.isKeyPressed(Keys.UP) == false && Gdx.input.isKeyPressed(Keys.RIGHT) == false && Gdx.input.isKeyPressed(Keys.DOWN) == false && Gdx.input.isKeyPressed(Keys.LEFT) == false)
		{
			shootOld = false;
			canShoot = true;
		}
	}
	
	
	
}
