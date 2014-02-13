package net.swordsofvalor.rpproperties;

import java.io.Serializable;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Property implements Serializable{

	private static final long serialVersionUID = 2360353302086494078L;
	String name;
	int x, y, z, width, height, length;
	float price;
	boolean locked;
	
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
		RPProperties.getInstance().save();
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
	
	public Location getLocation(World world){
		return new Location(world, x + width / 2, y + 1, z + length / 2);
	}
	
}
