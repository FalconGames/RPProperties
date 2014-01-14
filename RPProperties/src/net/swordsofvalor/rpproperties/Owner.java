package net.swordsofvalor.rpproperties;

import java.io.Serializable;

import org.bukkit.entity.Player;

public class Owner implements Serializable{

	private static final long serialVersionUID = -8703521962096815231L;
	private Player player;
	private long time;
	private boolean owned;
	
	public Owner(Player player, int time){
		
		this.player = player;
		this.time = time * 60 * 60;
		if(time == -1) owned = true;
		
	}
	
	public long getSecondsRemaining(){
		if(!owned) return time;
		else return 9999L;
	}
	
	public void updateTime(){
		if(!owned)time--;
	}
	
	public Player getPlayer(){
		return player;
	}
	
}
