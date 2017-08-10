package com.mygdx.game;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.MyGdxGame.Direction;

public class Entity {
	public MyGdxGame game;
	public float x;
	public float y;
	public float dx;
	public float dy;
	public int width;
	public int height;  
	public float speed;
	public Texture texture;
	public int direction;
	public int type;
	public double life;
	public boolean chase;
	ArrayList<Node> finalPath = new ArrayList<Node>();
	public String state;
	
	public Entity(MyGdxGame game, float x, float y, int width, int height, float speed, Texture texture) {
		this.game = game;
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.speed = speed;
		this.texture = texture;
		this.life = 3;
		//this.dx = 1;
		//this.dy = 2;
	}
	
	Texture zambie1Up = new Texture("zambie1 - UP.png");
	Texture zambie1Left = new Texture("zambie1 - LEFT.png");
	Texture zambie1Right = new Texture("zambie1 - RIGHT.png");
	Texture zambie1Down = new Texture("zambie1 - DOWN.png");
	
	Texture zambie2Up = new Texture("zambie2 - UP.png");
	Texture zambie2Left = new Texture("zambie2 - LEFT.png");
	Texture zambie2Right = new Texture("zambie2 - RIGHT.png");
	Texture zambie2Down = new Texture("zambie2 - DOWN.png");
	
	Texture zambie3Up = new Texture("zambie3 - UP.png");
	Texture zambie3Left = new Texture("zambie3 - LEFT.png");
	Texture zambie3Right = new Texture("zambie3 - RIGHT.png");
	Texture zambie3Down = new Texture("zambie3 - DOWN.png");

	public void update(float delta) {
		if (type == 0)
		{
			if (direction == 0)
			{
				this.texture = zambie1Right;
			}
			else if (direction == 1)
			{
				this.texture = zambie1Left;
			}
			else if (direction == 2)
			{
				this.texture = zambie1Up;
			}
			else if (direction == 3)
			{
				this.texture = zambie1Down;
			}
		}
		else if (type == 4)
		{
			if (direction == 0)
			{
				this.texture = zambie2Right;
			}
			else if (direction == 1)
			{
				this.texture = zambie2Left;
			}
			else if (direction == 2)
			{
				this.texture = zambie2Up;
			}
			else if (direction == 3)
			{
				this.texture = zambie2Down;
			}
		}
		else if (type == 3)
		{
			if (direction == 0)
			{
				this.texture = zambie3Right;
			}
			else if (direction == 1)
			{
				this.texture = zambie3Left;
			}
			else if (direction == 2)
			{
				this.texture = zambie3Up;
			}
			else if (direction == 3)
			{
				this.texture = zambie3Down;
			}
		}
	}
	
	public void move(float newX, float newY) {
		x = newX;
		y = newY;		
	}
	
	public void render() {
		
	}

	public void tileCollision(int tile, int tileX, int tileY, float newX, float newY, Direction direction) {
		//System.out.println("tile collision at: " + tileX + " " + tileY);
		
		if(direction == Direction.U) {
			y = tileY * game.tileSize + game.tileSize;
		}
		else if(direction == Direction.D) {
			y = tileY * game.tileSize - height;
		}
		else if(direction == Direction.L) {
			x = tileX * game.tileSize + game.tileSize;
		}
		else if(direction == Direction.R) {
			x = tileX * game.tileSize - width;
		}		
	}

	public void entityCollision(Entity e2, float newX, float newY, Direction direction) {
		//System.out.println("entity collision around: " + newX + " " + newY);
		
		//move(newX, newY);
		// could also resolve entity collisions in the same we do tile collision resolution
		// as shown in class
	}
}
