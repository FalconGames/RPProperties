package net.swordsofvalor.rpproperties;

import org.bukkit.entity.Player;

public class Owner {

	private Player player;
	private int time;
	
	public Owner(Player player, int time){
		
		this.player = player;
		this.time = time * 60 * 60;
		
	}
	
	public int getSecondsRemaining(){
		return time;
	}
	
	public void updateTime(){
		time--;
	}
	
	public Player getPlayer(){
		return player;
	}
	
}
