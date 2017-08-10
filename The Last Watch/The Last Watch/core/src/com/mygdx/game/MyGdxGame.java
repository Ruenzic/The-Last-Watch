package com.mygdx.game;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyGdxGame extends ApplicationAdapter {
	
	SpriteBatch batch;
	int screenWidth;
	int screenHeight;
    
	// 1 = block
	// 0 = empty
	// the x and y coordinate system is not what it seems
	// visually x goes down and y across
	// this will make more sense when you compare it to what is drawn
	int[][] map = {
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}, 
		{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1}, 
		{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		{1,0,0,1,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,1},
		{1,0,0,1,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,1},
		{1,0,0,1,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,1},
		{1,0,0,1,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,1},
		{1,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		{1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,1},
		{1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1},
		{1,2,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1},
		{1,2,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,1,1,1,1},
		{1,2,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,1,1,1,1},
		{1,2,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1},
		{1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,1},
		{1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,1},
		{1,0,0,1,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		{1,0,0,1,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,1},
		{1,0,0,1,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,1},
		{1,0,0,1,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,1},
		{1,0,0,1,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,1},
		{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		{1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1},
		{1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1}, 
	};
	int mapWidth = 24;
	int mapHeight = 24;
	int tileSize = 20;
	Texture tileTexture;
	Texture grassTexture;
	Texture houseTexture;
	Texture healthTexture;
	Texture pathTexture;
	Texture Up;
	Texture Left;
	Texture Right;
	Texture Down;
	Random rand;
	int moveRand;
	float time;                    
	boolean canMove = false;       
	boolean entCollision;          // WINING CODITION - LOSE GAME, STOP SPAWNING ENEMIES (AFTER A CERTAIN AMOUNT AND HOME AND PLAYER LIFE 0)
	int sizeCheck;                 // MUSIC AND SOUNDS
	float randomSpeed;              
	int enemyOnScreen;              
	BitmapFont lifeText;           
	String bars = "";
	Music mp3Sound;
	Music menuMusic;
	Sound gunshot;
	float homeLife = 50;
	int newX;
	int newY;
	int placeHealth;
	boolean gameStatus = false;
	boolean gameMenu = true;
	int killCount = 0;
	
	Node[][] nodes = new Node[24][24]; // Array of nodes, node version of map[][] for pathfinding. 
	//ArrayList<Node> openList = new ArrayList<Node>();                   // OpenList
	//ArrayList<Node> closedList = new ArrayList<Node>();                 // ClosedList
	
	ArrayList<Entity> entities = new ArrayList<Entity>();
	
	enum Axis { X, Y };
	enum Direction { U, D, L, R };

  @Override
  public void create () {
	  
	  mp3Sound = Gdx.audio.newMusic(Gdx.files.internal("groshieMusic.wav"));
	  mp3Sound.setLooping(true);
	  		mp3Sound.setVolume((float) 0.75);
	  		//mp3Sound.play();
	  menuMusic = Gdx.audio.newMusic(Gdx.files.internal("MenuMusic.wav"));
	  menuMusic.setLooping(true);
	  menuMusic.setVolume((float) 0.75);
	  
	  if (gameMenu == true)
	  {
		  menuMusic.play();
	  }
	  
	  gunshot = Gdx.audio.newSound(Gdx.files.internal("Bow_Fire_Arrow-Stephan_Schutze-2133929391NEW.wav"));
	  
			  
			  
	  lifeText = new BitmapFont();
	  rand = new Random();
	  batch = new SpriteBatch();
	  houseTexture = new Texture("houseNew.png");
	  healthTexture = new Texture("health.png");
	  tileTexture = new Texture("groundTile.png");  
	  grassTexture = new Texture("wall.png");
	  pathTexture = new Texture("path.png");
	  
	  Up = new Texture("Grosh - Up.png");
	  Left = new Texture("Grosh - Left.png");
	  Right = new Texture("Grosh - Right.png");
	  Down = new Texture("Grosh - Down.png");
	  
	  screenWidth = Gdx.graphics.getWidth();
	  screenHeight = Gdx.graphics.getHeight();
	  Gdx.graphics.setDisplayMode(800, 600, false);
	 	  
	  // add some entities including a player
	  entities.add(new Player(this, 220, 60, 20, 20, 200.0f, new Texture("Grosh - UP.png")));
	  entities.add(new Entity(this, 260, 440, 20, 20, 120.0f, new Texture("zambie1 - UP.png")));
	  entities.add(new Entity(this, 200, 440, 20, 20, 120.0f, new Texture("zambie1 - UP.png")));
	  entities.add(new Entity(this, 260, 400, 20, 20, 120.0f, new Texture("zambie1 - UP.png")));
	  entities.add(new Entity(this, 200, 400, 20, 20, 120.0f, new Texture("zambie1 - UP.png")));
	  entities.add(new Entity(this, 150, 250, 20, 20, 120.0f, new Texture("zambie2 - UP.png")));
	  entities.get(entities.size() -1).type = 3;
	  entities.add(new Entity(this, 130, 230, 20, 20, 120.0f, new Texture("zambie3 - UP.png")));
	  entities.get(entities.size() -1).type = 4;
	  //entities.add(new Entity(this, 260, 400, 20, 20, 120.0f, new Texture("zambie1 - UP.png")));
	  //entities.get(entities.size() -1).type = 5; // type 5 is the entity made for path finding testing.
	  //entities.add(new Entity(this, 200, 400, 20, 20, 120.0f, new Texture("zambie1 - UP.png")));
	  //entities.get(entities.size() -1).type = 6; // type 6 is the entity made for path finding testing. Not diagonal
	  
	  enemyOnScreen = 4;
	  
	  for (int i = 1; i < entities.size() - 2; i++)
	  {
		  Entity e = entities.get(i);
		  e.direction = moveRand = rand.nextInt(4);
		  e.type = 0;
	  }
	  
	  entities.get(0).life = 100.0;
	  entities.get(0).direction = 2;
	  
	  //  Populate the nodes[][] array by looping through map and setting the x and y values and the status, true if map value 0 else false. 
	  for (int x = 0; x < 24; x++)
	  {
		  for (int y = 0; y < 24; y++)
		  {
			  if (map[x][y] == 0)
			  {
				  nodes[x][y] = new Node(x,y,true,0,0,0);
			  }
			  else
			  {
				  nodes[x][y] = new Node(x,y,false,0,0,0);
			  }
		  }
	  }
	  
	  
  }
  
  public void moveEntity(Entity e, float newX, float newY) {
	  // just check x collisions keep y the same
	  moveEntityInAxis(e, Axis.X, newX, e.y);
	  // just check y collisions keep x the same
	  moveEntityInAxis(e, Axis.Y, e.x, newY);
  }
  
  public void moveEntityInAxis(Entity e, Axis axis, float newX, float newY) {
	  Direction direction;
	  
	  // determine axis direction
	  if(axis == Axis.Y) {
		  if(newY - e.y < 0) direction = Direction.U;
		  else direction = Direction.D;
	  }
	  else {
		  if(newX - e.x < 0) direction = Direction.L;
		  else direction = Direction.R;
	  }

	  entCollision = entityCollision(e, direction, newX, newY);
	  if(!tileCollision(e, direction, newX, newY) && !entCollision) {
		  // full move with no collision
		  e.move(newX, newY);
	  }
	  else
	  {
		  if (e != entities.get(0) && e.type == 0)
		  {
			  e.direction = rand.nextInt(4);
		  }
		  else if (e.type == 3)
		  {
			  newX = 4 - rand.nextInt(10);
			  newY = 4 - rand.nextInt(10);
			  
			  if (newX > newY && newX > 0 && newY > 0)
			  {
				  e.direction = 2;
			  }
			  else if (newX < newY && newX < 0 && newY < 0)
			  {
				  e.direction = 3;
			  }
			  else if (newX < 0 && newY > 0)
			  {
				  e.direction = 1;
			  }
			  else if (newX > 0 && newY < 0)
			  {
				  e.direction = 0;
			  }
			  
			  
			  
			  e.dx = 4 - rand.nextInt(10);
			  e.dy = 4 - rand.nextInt(10);
		  }
		  
		  if (e.type == 4)
		  {
			  e.speed = (rand.nextInt((4 - 1) + 1) + 1)*1000/10f;
			  e.direction = rand.nextInt(4);
		  }
	  }
	  
	  //if (e == entities.get(0) && entCollision == true )
	  //{
		 //entities.remove(e);
	  //}
	  // else collision with wither tile or entity occurred 
  }
  
  public boolean tileCollision(Entity e, Direction direction, float newX, float newY) {
	  boolean collision = false;

	  // determine affected tiles
	  int x1 = (int) Math.floor(Math.min(e.x, newX) / tileSize);
	  int y1 = (int) Math.floor(Math.min(e.y, newY) / tileSize);
	  int x2 = (int) Math.floor((Math.max(e.x, newX) + e.width - 0.1f) / tileSize);
	  int y2 = (int) Math.floor((Math.max(e.y, newY) + e.height - 0.1f) / tileSize);
	  
	  // todo: add boundary checks...

	  // tile check
	  for(int x = x1; x <= x2; x++) {
		  for(int y = y1; y <= y2; y++) {
			  
			  if (x >= 24 || x < 0 || y >= 24 || y < 0)
			  {
				collision = true;  
			  }
			  
			  else if(map[x][y] == 1 || map[x][y] == 2 || map[x][y] == 3) {
				  collision = true;
				  if (map[x][y] == 2 && e.type == 0 && e != entities.get(0))
				  {
					  homeLife = (float) (homeLife - 3);
				  }
				  else if (map[x][y] == 2 && e.type == 3)
				  {
					  homeLife = (float) (homeLife - 0.09);
				  }
				  else if (map[x][y] == 2 && e.type == 4)
				  {
					  homeLife = (float) (homeLife - 0.3);
				  }
				  else if (map[x][y] == 3 && e == entities.get(0))
				  {
					  entities.get(0).life = 100;
					  map[x][y] = 0;
					  collision = false;
				  }
				  
				  if (e.type == 1 && map[x][y] != 3)
				  {
					  entities.remove(e);
				  }
				  
				  if (map[x][y] == 3)
				  {
					  collision = false;
				  }
				  
				  e.tileCollision(map[x][y], x, y, newX, newY, direction);
			  }
		  }
	  }
	  
	  return collision;
  }
  
  public boolean entityCollision(Entity e1, Direction direction, float newX, float newY) {
	  boolean collision = false;
	  
	  for(int i = 0; i < entities.size(); i++) {
		  Entity e2 = entities.get(i);
		  
		  // we don't want to check for collisions between the same entity
		  if(e1 != e2) {
			  // axis aligned rectangle rectangle collision detection
			  if(newX < e2.x + e2.width && e2.x < newX + e1.width &&
				  newY < e2.y + e2.height && e2.y < newY + e1.height) {
				  collision = false;
				  
				  if (e1.type == 0 && e2.type == 0)
				  {
					  collision = true;
				  }
				  else if (e1.type == 4 && e2.type == 0 || e2.type == 3)
				  {
					  collision = true;
				  }
				  else if (e1.type == 0 || e1.type == 3 && e2.type == 4)
				  {
					  collision = true;
				  }
				  else if (e1.type == 3 && e2.type == 0 || e2.type == 4)
				  {
					  collision = true;
				  }
				  else if (e1.type == 0 || e1.type == 4 && e2.type == 3)
				  {
					  collision = true;
				  }
				  
				  
				  
				  if (e1 == entities.get(0) || e2 == entities.get(0)) // Does damage to player
				  {
					  if (e1 == entities.get(0) && e2.type == 0)
						  {
						  	e1.life = e1.life - 1;
						  	System.out.println(e1.life);
						  	collision = true;
						  
						  	//if (e2.life > 0)
						  	//{
						  		//e2.life--;
						  	//}
						  	//else
						  	//{
						  		//entities.remove(e2);
						  		//enemyOnScreen--;
						  		//if(enemyOnScreen < 4)
						  		//{
						  			//newEnemy();
						  		//}
						  	//}
						  }
					  else if (e2 == entities.get(0) && e1.type != 1) // Doesnt do damage to enemy, only to player
					  {
						  e2.life = e2.life - 1;
						  System.out.println(e2.life);
						  collision = true;
						  
						 // e2.life = e2.life - 10;
						  //System.out.println(e2.life);
						  
						  
						  //if (e1.life > 0)
						  //{
							  //e1.life--;
						 // }
						  //else
						  //{
							 // entities.remove(e1);
							 // enemyOnScreen--;
						  		//if(enemyOnScreen < 4)
						  		//{
						  		//	newEnemy();
						  		//}
						 // }
					  }
				  }
				  else if(e1.type == 1 || e2.type == 1)
				  {
					  if (e1.type == 1 && e2.type == 0 || e2.type == 3 || e2.life == 4)
					  {
						  if (e2.life > 0)
						  {
							  e2.life = e2.life - 2;
							  entities.remove(e1);
							  if (e2.type != 4)
							  {
								  if (e1.direction == 3)
								  {
									  moveEntity(e2, e2.x, e2.y - 20);
								  }
								  if (e1.direction == 2)
								  {
									  moveEntity(e2, e2.x, e2.y + 20);
								  }
								  if (e1.direction == 1)
								  {
									  moveEntity(e2, e2.x - 20, e2.y);
								  }
								  if (e1.direction == 0)
								  {
									  moveEntity(e2, e2.x + 20, e2.y);
								  }
							  }
						  }
						  else
						  {
							  entities.remove(e1);
							  entities.remove(e2);
							  killCount++;
							  
							  placeHealth = rand.nextInt(10);
							  if (placeHealth == 0)
							  {
								  placeHealth = rand.nextInt(5);
								  if (placeHealth == 0)
								  {
									  map[10][12] = 3;
									  System.out.println("10, 13");
								  }
								  else if (placeHealth == 1)
								  {
									  map[17][5] = 3;
									  System.out.println("19, 6");
								  }
								  else if (placeHealth == 2)
								  {
									  map[14][10] = 3;
									  System.out.println("14, 9");
								  }
								  else if (placeHealth == 3)
								  {
									  map[20][21] = 3;
									  System.out.println("21, 22");
								  }
								  else if (placeHealth == 4)
								  {
									  map[3][5] = 3;
									  System.out.println("4, 4");
								  }
							  }
							  
							  enemyOnScreen--;
						  		if(enemyOnScreen < 4)
						  		{
						  			newEnemy();
						  		}
						  }
					  }
					  else if (e1.type == 0 || e1.type == 3 || e1.type == 4 && e2.type == 1)
					  {
						  if (e1.life > 0)
						  {
							  e1.life = e1.life - 2;
							  entities.remove(e2);
							  if (e1.type != 4)
							  {
								  if (e2.direction == 3)
								  {
									  moveEntity(e1, e2.x, e2.y - 20);
								  }
								  if (e2.direction == 2)
								  {
									  moveEntity(e1, e2.x, e2.y + 20);
								  }
								  if (e2.direction == 1)
								  {
									  moveEntity(e1, e2.x - 20, e2.y);
								  }
								  if (e2.direction == 0)
								  {
									  moveEntity(e1, e2.x + 20, e2.y);
								  }
							  }
						  }
						  else
						  {
							  entities.remove(e1);
							  entities.remove(e2);
							  killCount++;
							  enemyOnScreen--;
						  		if(enemyOnScreen < 4)
						  		{
						  			newEnemy();
						  		}
						  }
					  }
					  //entities.remove(e1);
					  //entities.remove(e2);
				  }
				  
				  e1.entityCollision(e2, newX, newY, direction);
			  }
		  }
		  
	  }
	  
	  return collision;
  }
  
  public void shoot(int direction)
  {
	  gunshot.play();
	  if (direction == 0)
	  {
		  entities.get(0).texture = Right;
	  }
	  else if (direction == 1)
	  {
		   entities.get(0).texture = Left;
	  }
	  else if (direction == 2)
	  {
		  entities.get(0).texture = Up;
	  }
	  else if (direction == 3)
	  {
		  entities.get(0).texture = Down;
	  }
	  
	  if (direction == 0)
	  {
		  entities.add(new Entity(this, entities.get(0).x, entities.get(0).y, 15, 15, 120.0f, new Texture("bulletNew1 - RIGHT.png")));
	  }
	  else if (direction == 1)
	  {
		  entities.add(new Entity(this, entities.get(0).x, entities.get(0).y, 15, 15, 120.0f, new Texture("bulletNew1 - LEFT.png")));
	  }
	  else if (direction == 2)
	  {
		  entities.add(new Entity(this, entities.get(0).x, entities.get(0).y, 15, 15, 120.0f, new Texture("bulletNew1 - UP.png")));
	  }
	  else if (direction == 3)
	  {
		  entities.add(new Entity(this, entities.get(0).x, entities.get(0).y, 15, 15, 120.0f, new Texture("bulletNew1 - DOWN.png")));
	  }
	  entities.get(entities.size()-1).type = 1;
	  entities.get(entities.size()-1).direction = direction;
  }
  
  public void chase()
  {
	  Entity player = entities.get(0);
	  
	  for (int i = 1; i < entities.size(); i++)
	  {
		  Entity e = entities.get(i);
		  if (e.type == 4)
		  {
			  if (e.x == player.x)
			  {
				  if (e.y < player.y)
				  {
					  e.direction = 2;
				  }
				  else if (e.y > player.y)
				  {
					  e.direction = 3;
				  }
				  e.chase = true;
				  e.speed = (float) 1000;
			  }
			  else if (e.y == player.y)
			  {
				  if (e.x < player.x)
				  {
					  e.direction = 0;
				  }
				  else if (e.x > player.x)
				  {
					  e.direction = 1;
				  }
				  e.chase = true;
				  e.speed = (float) 1000;
			  }
			  
		  }
	  }
	  
	  
	  
  }
  
  public void newEnemy()
  {
	  int position = rand.nextInt(6);
	  int enemyType = rand.nextInt(6);
	  if (enemyType == 1)enemyType = 3;
	  else if (enemyType == 2)enemyType = 4;
	  else if (enemyType == 3)enemyType = 0;
	  else if (enemyType == 4)enemyType = 3;
	  else if (enemyType == 5)enemyType = 0;
	  int max = 4;
	  int min = 1;
	  int previous = 0; // previous spawn, dont let them spawn in same place after eachother. 
	  boolean canPlace = false;
	  float randomSpeed = rand.nextInt((max - min) + 1) + min;
	  randomSpeed = randomSpeed * 1000;
	  randomSpeed = randomSpeed/10f;
	  
	  while (canPlace == false)
	  {
		  boolean test = true;
		  for (int i = 1; i < entities.size() -1; i++)
		  {
			  Entity e = entities.get(i);
			  if (position == 0 && e.x < 260 || e.x > 280 && e.y < 440 || e.y > 460)
			  {
				  if (previous != position)
				  {
					  canPlace = true;
					  previous = position;
					  break;
				  }
			  }
			  else if (position == 5 && e.x < 280 || e.x > 300 && e.y < 360 || e.y > 380)
			  {
				  if (previous != position)
				  {
					  canPlace = true;
					  previous = position;
					  break;
				  }
			  }
			  else if (position == 1 && e.x < 200 || e.x > 220 && e.y < 440 || e.y > 460)
			  {
				  if (previous != position)
				  {
					  canPlace = true;
					  previous = position;
					  break;
				  }
			  }
			  else if (position == 2 && e.x < 260 || e.x > 280 && e.y < 400 || e.y > 420)
			  {
				  if (previous != position)
				  {
					  canPlace = true;
					  previous = position;
					  break;
				  }
			  }
			  else if (position == 3 && e.x < 200 || e.x > 220 && e.y < 400 || e.y > 420)
			  {
				  if (previous != position)
				  {
					  canPlace = true;
					  previous = position;
					  break;
				  }
			  }
			  else if (position == 4 && e.x < 180 || e.x > 200 && e.y < 360 || e.y > 380)
			  {
				  if (previous != position)
				  {
					  canPlace = true;
					  previous = position;
					  break;
				  }
			  }
			  else
			  {
				  test = false;
			  }
		  }
		  if (test = false)
		  {
			  canPlace = false;
		  }
		  position = rand.nextInt(6);
	  }
	  
	  Texture zambie1 = new Texture("zambie1 - UP.png");
	  Texture zambie2 = new Texture("zambie2 - UP.png");
	  Texture zambie3 = new Texture("zambie3 - UP.png");
	  Texture eType = new Texture("zambie1 - UP.png");
	  if (enemyType == 0)eType = zambie1;
	  else if (enemyType == 2)eType = zambie2;
	  else if (enemyType == 3)eType = zambie3;
	  
	  if (position == 0)
	  {
		  entities.add(new Entity(this, 260, 440, 20, 20, randomSpeed, eType));
	  }
	  else if (position == 1)
	  {
		  entities.add(new Entity(this, 200, 440, 20, 20, randomSpeed, eType));
	  }
	  else if (position == 2)
	  {
		  entities.add(new Entity(this, 260, 400, 20, 20, randomSpeed, eType));
	  }
	  else if (position == 3)
	  {
		  entities.add(new Entity(this, 200, 400, 20, 20, randomSpeed, eType));
	  }
	  else if (position == 4)
	  {
		  entities.add(new Entity(this, 180, 360, 20, 20, randomSpeed, eType));
	  }
	  else if (position == 5)
	  {
		  entities.add(new Entity(this, 280, 360, 20, 20, randomSpeed, eType));
	  }
	  
	  
	  entities.get(entities.size() -1).type = enemyType;
  }

  @Override
  public void render () {
	  
	  // update
	  // ---
	  
	  // loop through all the enemies and if enemy on same x-axis or y-axis as player change direction to go towards player. 
	  // make entity increase speed. 
	  
	  if (killCount >= 100)
	  {
		  gameStatus = false;
		  mp3Sound.stop();
	  }
	  else if (entities.get(0).life <= 0)
	  {
		  gameStatus = false;
		  mp3Sound.stop();
	  }
	  else if (homeLife <= 0)
	  {
		  gameStatus = false;
		  mp3Sound.stop();
	  }
	  
	  
	  System.out.println(entities.get(0).direction);
	  
	  float delta = Gdx.graphics.getDeltaTime();
	  
	  // update all entities
	  //chase();
	  
	  for(int i = entities.size() - 1; i >= 0; i--) {
		  Entity e = entities.get(i);
		  if (e == entities.get(0))
		  {
			// update entity based on input/ai/physics etc
			  // this is where we determine the change in position
			  e.update(delta);
			  // now we try move the entity on the map and check for collisions
			  moveEntity(e, e.x + e.dx, e.y + e.dy);
		  }
		  else if (e.type == 3)
		  {
			  moveEntity(e, e.x + e.dx, e.y + e.dy); // uncomment
		  }
		  if (e != entities.get(0))
		  {
			  e.update(delta);
		  }
		  chase();
		  
		  // update entity based on input/ai/physics etc
		  // this is where we determine the change in position
		  //e.update(delta);
		  // now we try move the entity on the map and check for collisions
		  //moveEntity(e, e.x + e.dx, e.y + e.dy);
	  }	  
	  chase();
	  
	  for (int i = 0; i < entities.size(); i++) // Loop through entities and path find for all entities of type 5 and type 6.
	  {
		  ArrayList<Node> openList = new ArrayList<Node>();                   // OpenList
		  ArrayList<Node> closedList = new ArrayList<Node>();                 // ClosedList
		  Entity e = entities.get(i);
		  Entity player = entities.get(0);
		  int Px = (int) Math.floor(player.x / tileSize); // player x value
		  int Py = (int) Math.floor(player.y / tileSize); // player y value
		  
		  Node current; // Current node.
		  Node lowestCost;
		  Node goal = nodes[Px][Py]; // set the goal node to the players position. 
		  e.state = "chase";
		  Node check;
		  e.finalPath = new ArrayList<Node>();
		  if (e.type == 5 || e.type == 6) 
		  {
			  
			  int x = (int) Math.floor(e.x / tileSize);
			  int y = (int) Math.floor(e.y / tileSize);
			  
			  //if (Px >= 8 || Px <= 15 && Py <= 3)
			  //{
				  //e.state = "attack";
				  //goal = nodes[11][2];
			  //}
			  
			  nodes[x][y].g = 0;
			  nodes[x][y].h = (Math.abs(x- goal.x) + Math.abs(y - goal.y));
			  nodes[x][y].f = nodes[x][y].g + nodes[x][y].h;
			  if (nodes[x][y].f < 22)
			  {
				  openList.add(nodes[x][y]); // add starting node to open list
				  e.state = "Chase";
			  }
			  else
			  {
				  e.state = "Ignore";
			  }
			  //openList.add(nodes[x][y]); // add starting node to open list
			  
			  //check to see if the entity is within 2 or 3 tiles of the home base and if so, set the entities state to attack and not chase. x 10,11,12,13 , y 1
			  while(openList.size() != 0) // While open list is not empty
			  {
				  lowestCost = openList.get(0);
				  for (int j = 0; j < openList.size(); j ++)
				  {
					  if (openList.get(j).f < lowestCost.f)
					  {
						  lowestCost = openList.get(j); // find the node in openLsit with the lowest cost. 
					  }
				  }
				  current = lowestCost; // set the current node to the lowest cost node from the open list.
				  if (current == goal)
				  {
					  while(current.parent != null) // loop through back from current following parents and add them to finalPAth. Stop when begining is reached (no parent)
					  {
						  e.finalPath.add(current.parent);
						  current = current.parent;
					  }
					  break; // path complete (follow closed nodes back to get final path.) - parents in each node from current. 
				  }
				  else
				  {
					  closedList.add(current); // move the current node to the closed list.
					  openList.remove(current); // remove from open list
					  
					  for (int k = 0; k < 8; k++) // for each adjacent node, add to open list and calculate a cost for g,h,f and set the parent.
					  {
						  if (k == 1 && nodes[current.x][current.y + 1].status == true && openList.contains(nodes[current.x][current.y + 1]) == false && closedList.contains(nodes[current.x][current.y + 1]) == false)
						  {
							  check = nodes[current.x][current.y + 1];
							  check.parent = nodes[current.x][current.y];
							  check.g = check.parent.g + 1;
							  check.h = (Math.abs(check.x- Px) + Math.abs(check.y - Py));
							  check.f = check.g + check.h;
							  
							  openList.add(check);
						  }
						  else if (k == 3 && nodes[current.x - 1][current.y].status == true && openList.contains(nodes[current.x - 1][current.y]) == false && closedList.contains(nodes[current.x - 1][current.y]) == false)
						  {
							  check = nodes[current.x - 1][current.y];
							  check.parent = nodes[current.x][current.y];
							  check.g = check.parent.g + 1;
							  check.h = (Math.abs(check.x- Px) + Math.abs(check.y - Py));
							  check.f = check.g + check.h;
							  
							  openList.add(check);
						  }
						  else if (k == 4 && nodes[current.x + 1][current.y].status == true && openList.contains(nodes[current.x + 1][current.y]) == false && closedList.contains(nodes[current.x + 1][current.y]) == false)
						  {
							  check = nodes[current.x + 1][current.y];
							  check.parent = nodes[current.x][current.y];
							  check.g = check.parent.g + 1;
							  check.h = (Math.abs(check.x- Px) + Math.abs(check.y - Py));
							  check.f = check.g + check.h;
							 
							  openList.add(check);
						  }
						  else if (k == 6 && nodes[current.x][current.y - 1].status == true && openList.contains(nodes[current.x][current.y - 1]) == false && closedList.contains(nodes[current.x][current.y - 1]) == false)
						  {
							  check = nodes[current.x][current.y - 1];
							  check.parent = nodes[current.x][current.y];
							  check.g = check.parent.g + 1;
							  check.h = (Math.abs(check.x- Px) + Math.abs(check.y - Py));
							  check.f = check.g + check.h;
							 
							  openList.add(check);
						  }
						  else if (k == 0 && e.type == 5 && nodes[current.x - 1][current.y + 1].status == true && openList.contains(nodes[current.x - 1][current.y + 1]) == false && closedList.contains(nodes[current.x - 1][current.y + 1]) == false)
						  {
							  check = nodes[current.x - 1][current.y + 1];
							  check.parent = nodes[current.x][current.y];
							  check.g = check.parent.g + 1;
							  check.h = (Math.abs(check.x- Px) + Math.abs(check.y - Py));
							  check.f = check.g + check.h;
							
							  openList.add(check);
						  }
						  else if (k == 2 && e.type == 5 && nodes[current.x + 1][current.y + 1].status == true && openList.contains(nodes[current.x + 1][current.y + 1]) == false && closedList.contains(nodes[current.x + 1][current.y + 1]) == false)
						  {
							  check = nodes[current.x + 1][current.y + 1];
							  check.parent = nodes[current.x][current.y];
							  check.g = check.parent.g + 1;
							  check.h = (Math.abs(check.x- Px) + Math.abs(check.y - Py));
							  check.f = check.g + check.h;
							 
							  openList.add(check);
						  }
						  else if (k == 5 && e.type == 5 && nodes[current.x - 1][current.y - 1].status == true && openList.contains(nodes[current.x - 1][current.y - 1]) == false && closedList.contains(nodes[current.x - 1][current.y - 1]) == false)
						  {
							  check = nodes[current.x - 1][current.y - 1];
							  check.parent = nodes[current.x][current.y];
							  check.g = check.parent.g + 1;
							  check.h = (Math.abs(check.x- Px) + Math.abs(check.y - Py));
							  check.f = check.g + check.h;
							 
							  openList.add(check);
						  }
						  else if (k == 7 && e.type == 5 && nodes[current.x + 1][current.y - 1].status == true && openList.contains(nodes[current.x + 1][current.y - 1]) == false && closedList.contains(nodes[current.x + 1][current.y - 1]) == false)
						  {
							  check = nodes[current.x + 1][current.y - 1];
							  check.parent = nodes[current.x][current.y];
							  check.g = check.parent.g + 1;
							  check.h = (Math.abs(check.x- Px) + Math.abs(check.y - Py));
							  check.f = check.g + check.h;
							 
							  openList.add(check);
						  }
					  }
					  
				  }
			  }
			  // Loop through finalPath from back to front and draw a block on those tiles to indicate the path from the entity to the player.
			  // loop through map as well and change all 4 type tiles to 0, so it can be refreshed in case player moves. 
		  }
	  }
	  
	//for(int b = 0; b < mapHeight; b++) {
	   //go over each column left to right		
	  //for(int a = 0; a < mapWidth; a++) {
		   //tile
		  //if(map[a][b] == 4) {
			  //map[a][b] = 0;
		  //} 
	  //}
  //}
	  //for (int p = 1; p < entities.size(); p++)
	  //{
		  //Entity e = entities.get(p);
		  //Node check;
		//for (int n = e.finalPath.size() - 1; n >= 0; n--)
		  //{
			 // check = e.finalPath.get(n);
			  //System.out.println("test");
			 // map[check.x][check.y] = 4;
		  //}
	  //}
	  
	  
	  
	  // draw
	  // ---

	  
	  // to offset where your map and entities are drawn change the viewport
	  // see libgdx documentation
	  
	  Gdx.gl.glClearColor(0, 0, 0, 1);
	  Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	  batch.begin();
	  
      if (gameStatus == true)
      {
    	  //for (int i = 1; i < entities.size(); i++)
    	  //{
    		  //Entity e = entities.get(i);
    		  //if (e.type == 5 || e.type == 6 && e.finalPath.size() != 0)
    		  //{	
    			 //int size = e.finalPath.size();
	    		  //e.x = e.finalPath.get(size -1).x*20;
	    		  //e.y = e.finalPath.get(size - 1).y*20;
	    		  //e.finalPath.remove(size - 1);
    			 //map[(int) Math.floor(e.x/20)][(int) Math.floor(e.y/20)] = 0;
    			 // moveEntity(e, e.finalPath.get(e.finalPath.size()-1).x*20, e.finalPath.get(e.finalPath.size()-1).y*20);
	    		  
    		  //}
    	  //}
    	  
    	  
		  lifeText.setColor(5.0f, 5.0f, 1.0f, 1.0f);
		  lifeText.draw(batch, "THE LAST WATCH", 490, 465);
		  lifeText.draw(batch, "PLAYER LIFE: " + Math.round(entities.get(0).life * 100.0)/100.0, 490, 400);
		  bars = "";
		  for (int b = (int) (entities.get(0).life/3); b > 0; b--)
		  {
			  bars += "|";
		  }
		  
		  lifeText.draw(batch, bars , 490, 350);
		  
		  
		  lifeText.draw(batch, "HOME LIFE: " + Math.round(homeLife * 100.0)/100.0, 490, 300);
		  bars = "";
		  for (int b = (int) (homeLife/2); b > 0; b--)
		  {
			  bars += "|";
		  }
		  
		  lifeText.draw(batch, bars , 490, 250);
		  
		  lifeText.draw(batch, "Enemies Killed: " + killCount + "/100", 490, 200);
		  
		  // draw tile map
		  // go over each row bottom to top
		  for(int y = 0; y < mapHeight; y++) {
			  // go over each column left to right		
			  for(int x = 0; x < mapWidth; x++) {
				  // tile
				  if(map[x][y] == 1) {
					  batch.draw(tileTexture, x * tileSize, y * tileSize);
				  }
				  else if (map[x][y] == 0)
				  {
					  batch.draw(grassTexture, x * tileSize, y * tileSize);
				  }
				  else if (map[x][y] == 2)
				  {
					  batch.draw(houseTexture, x * tileSize, y * tileSize);
				  }
				  else if (map[x][y] == 3)
				  {
					  batch.draw(healthTexture, x * tileSize, y * tileSize);
				  }
				  else if (map[x][y] == 4)
				  {
					  batch.draw(pathTexture, x * tileSize, y * tileSize);
				  }
				  // draw other types here...
			  }
		  }
	    
		  // draw all entities
		  time += Gdx.graphics.getDeltaTime();
		  if (time > 0.04)
		  {
			  canMove = true;
			  time = (float) (time - 0.04);
		  }
		  sizeCheck = entities.size();
		  for(int i = entities.size() - 1; i >= 0; i--) {
			  if (i < entities.size())
			  {
				  Entity e = entities.get(i);
			  }
			  else
			  {
				  i--;
			  }
			  Entity e = entities.get(i);
			  if (i != 0 && e.type != 1 && e.type != 5 && e.type != 6) // Not player or bullet
			  {
				  if (e.direction== 0) // Right
				  {
					  if (canMove)
					  {
						  moveEntity(e, e.x + delta * e.speed, e.y);
					  }
					  batch.draw(e.texture, e.x, e.y);
				  }
				  else if (e.direction== 1) // Left
				  {
					  if (canMove)
					  {
						  moveEntity(e, e.x - delta * e.speed, e.y);
					  }
					  batch.draw(e.texture, e.x, e.y);
				  }
				  else if (e.direction== 2) // Up
				  {
					  if (canMove)
					  {
						  moveEntity(e, e.x, e.y + delta * e.speed);
					  }
					  batch.draw(e.texture, e.x, e.y);
				  }
				  else if (e.direction== 3) // Down
				  {
					  if (canMove)
					  {
						  moveEntity(e, e.x, e.y - delta * e.speed);
					  }
					  batch.draw(e.texture, e.x, e.y);
				  }
			  }
			  else if (e.type == 1) // Bullet
			  {
				  if (e.direction == 2) // Up
				  {
					  moveEntity(e, e.x, e.y + 20);
					  batch.draw(e.texture, e.x, e.y);
				  }
				  else if (e.direction == 3) // Down
				  {
					  moveEntity(e, e.x, e.y - 20);
					  batch.draw(e.texture, e.x, e.y);
				  }
				  else if (e.direction == 1) // Left
				  {
					  moveEntity(e, e.x - 20, e.y);
					  batch.draw(e.texture, e.x, e.y);
				  }
				  else if (e.direction == 0) // Right
				  {
					  moveEntity(e, e.x + 20, e.y);
					  batch.draw(e.texture, e.x, e.y);
				  }
			  }
			  else
			  {
				  batch.draw(e.texture, e.x, e.y);
			  }
			  //batch.draw(e.texture, e.x, e.y);
			  //if (sizeCheck != entities.size())
			  //{
				  //break;
			  //}
		  }
		  canMove = false;
      }
      else if (gameStatus == false && gameMenu == false)
      {
    	  if (!menuMusic.isPlaying())
    	  {
    		  menuMusic.play();
    	  }
    	  
    	  if (killCount == 100)
    	  {
    		  lifeText.setColor(5.0f, 5.0f, 1.0f, 1.0f);
    		  lifeText.draw(batch, "YOU WIN!", 290, 340);
    		  lifeText.draw(batch, "PRESS 'R' TO RESTART THE GAME", 200, 300);
    	  }
    	  else if (entities.get(0).life <= 0 || homeLife <= 0)
    	  {
    		  lifeText.setColor(5.0f, 5.0f, 1.0f, 1.0f);
    		  lifeText.draw(batch, "YOU LOSE!", 290, 340);
    		  lifeText.draw(batch, "PRESS 'R' TO RESTART THE GAME", 200, 300);
    	  }
    	  
    	  if (Gdx.input.isKeyPressed(Keys.R))
    	  {
    		  menuMusic.stop();
    		  mp3Sound.play();
    		  homeLife = 50;
    		  killCount = 0;
    		  gameStatus = true;
    		  entities = new ArrayList<Entity>();
    		  entities.add(new Player(this, 220, 60, 20, 20, 200.0f, new Texture("Grosh - UP.png")));
    		  entities.add(new Entity(this, 260, 440, 20, 20, 120.0f, new Texture("zambie1 - UP.png")));
    		  entities.add(new Entity(this, 200, 440, 20, 20, 120.0f, new Texture("zambie1 - UP.png")));
    		  entities.add(new Entity(this, 260, 400, 20, 20, 120.0f, new Texture("zambie1 - UP.png")));
    		  entities.add(new Entity(this, 200, 400, 20, 20, 120.0f, new Texture("zambie1 - UP.png")));
    		  entities.add(new Entity(this, 150, 250, 20, 20, 120.0f, new Texture("zambie2 - UP.png")));
    		  entities.get(entities.size() -1).type = 3;
    		  entities.add(new Entity(this, 130, 230, 20, 20, 120.0f, new Texture("zambie3 - UP.png")));
    		  entities.get(entities.size() -1).type = 4;
    		  
    		  enemyOnScreen = 4;
    		  
    		  for (int i = 1; i < entities.size() - 2; i++)
    		  {
    			  Entity e = entities.get(i);
    			  e.direction = moveRand = rand.nextInt(4);
    			  e.type = 0;
    		  }
    		  
    		  entities.get(0).life = 100.0;
    		  entities.get(0).direction = 2;
    	  }
      }
      else if (gameStatus == false && gameMenu == true)
      {
    	  if (!menuMusic.isPlaying())
    	  {
    		  menuMusic.play();
    	  }
    	  lifeText.setColor(5.0f, 5.0f, 1.0f, 1.0f);
    	  lifeText.draw(batch, "THE LAST WATCH", 250, 400);
    	  lifeText.draw(batch, "PRESS 'SPACE' TO START THE GAME", 180, 350);
    	  lifeText.draw(batch, "CONTROLS:", 145, 280);
    	  lifeText.draw(batch, "- WASD TO MOVE UP LEFT, DOWN AND RIGHT", 145, 250);
    	  lifeText.draw(batch, "- ARROW KEYS TO SHOOT IN THE 4 DIFFERENT DIRECTIONS", 145, 220);
    	  lifeText.draw(batch, "Its 2035 and the world has been facing a zombie  apocalypse, you are among the few", 30, 160);
    	  lifeText.draw(batch, "that have survived. A  cure has been discovered and is on its way to you, and it's your", 30, 140);
    	  lifeText.draw(batch, "job to take the last watch, protect your family and survive the night.", 30, 120);
    	  
    	  
    	  if (Gdx.input.isKeyPressed(Keys.SPACE))
    	  {
    		  menuMusic.stop();
    		  mp3Sound.play();
    		  homeLife = 50;
    		  killCount = 0;
    		  gameStatus = true;
    		  gameMenu = false;
    		  entities = new ArrayList<Entity>();
    		  entities.add(new Player(this, 220, 60, 20, 20, 200.0f, new Texture("Grosh - UP.png")));
    		  entities.add(new Entity(this, 260, 440, 20, 20, 120.0f, new Texture("zambie1 - UP.png")));
    		  entities.add(new Entity(this, 200, 440, 20, 20, 120.0f, new Texture("zambie1 - UP.png")));
    		  entities.add(new Entity(this, 260, 400, 20, 20, 120.0f, new Texture("zambie1 - UP.png")));
    		  entities.add(new Entity(this, 200, 400, 20, 20, 120.0f, new Texture("zambie1 - UP.png")));
    		  entities.add(new Entity(this, 150, 250, 20, 20, 120.0f, new Texture("zambie2 - UP.png")));
    		  entities.get(entities.size() -1).type = 3;
    		  entities.add(new Entity(this, 130, 230, 20, 20, 120.0f, new Texture("zambie3 - UP.png")));
    		  entities.get(entities.size() -1).type = 4;
    		  
    		  enemyOnScreen = 4;
    		  
    		  for (int i = 1; i < entities.size() - 2; i++)
    		  {
    			  Entity e = entities.get(i);
    			  e.direction = moveRand = rand.nextInt(4);
    			  e.type = 0;
    		  }
    		  
    		  entities.get(0).life = 100.0;
    		  entities.get(0).direction = 2;
    	  }
      }
	  
	  batch.end();
  }
}
