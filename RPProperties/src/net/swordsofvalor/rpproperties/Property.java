package net.swordsofvalor.rpproperties;

import org.bukkit.entity.Player;

public class Property {

	String name;
	int x, y, z, width, height, length;
	float price;
	
	public Property(String name, SelectionArea area, float price){
		this(area.getX(), area.getY(), area.getZ(), area.getX2(), area.getY2(), area.getZ2(), price);
		this.name = name;
	}
	
	private Property(int x, int y, int z, int width, int height, int length, float price){
		this.x = x < width ? x : width;
		this.y = y < height ? y : height;
		this.z = z < length ? z : length;
		this.width = x > width ? width : x;
		this.height = y > height ? height : y;
		this.length = z > length ? length : z;
		this.price = price;
	}
	
	public boolean checkBound(Player p){
		return
			(p.getLocation().getX() > x && p.getLocation().getX() < width) &&
			(p.getLocation().getY() > y && p.getLocation().getY() < height) &&
			(p.getLocation().getZ() > z && p.getLocation().getZ() < length);
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}
	
	public int getZ(){
		return y;
	}
	
	public int getWidth(){
		return width;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getLength(){
		return length;
	}
	
	
	public float getPrice(){
		return price;
	}
	
	public String getName(){
		return name;
	}
	
}
