package com.mygdx.game;

public class Node {
	
	public int x;
	public int y;
	public boolean status;
	public int g;
	public int h;
	public int f;
	public Node parent;
	
	public Node(int x, int y, boolean status, int g, int h, int f){
		this.x = x;
		this.y = y;
		this.status = status;
		this.g = g;
		this.h = h;
		this.f = f;
		
	}
	
	
	
	
	
	
	
	
	
	

}
